var telCheckValidPayment = true;

$(function () {

    function formatNumberToCode(number) {
        return 'LUNASTORE' + number.toString().padStart(8, '0');
    }

    var IMP = window.IMP;
    if (!IMP) {
        console.error("아임포트 SDK가 로드되지 않았습니다.");
        return;
    }
    IMP.init("imp78130364");

    var b_idx = $("#b_idx").val();
    var bo_idx = null;
    var bo_totalprice = 0;
    var bo_item_name = $("#itemsName").val();
    var b_name = $("#buyerName").val();
    var b_tel = $("#telInput").val();
    var ba_address = $("#baAddress").text();
    var ba_zipcode = $("#zonecodeInput").val();

    var i_idxArray = [];
    var optionArray = [];
    var priceArray = [];
    var cartItemsCountValue = $('#cartItemsCount').val();

    var countArray;
    try {
        countArray = cartItemsCountValue ? JSON.parse(cartItemsCountValue) : [];
    } catch (e) {
        console.error("JSON.parse 에러:", e);
        countArray = [];
    }

    $('.cartItems').each(function () {
        i_idxArray.push(parseInt($(this).val()));
    });
    $('.cartOptions').each(function () {
        optionArray.push($(this).val());
    });
    $('.cartPrice').each(function () {
        priceArray.push(parseInt($(this).val()));
    });

    // 총 가격 계산
    for (var i = 0; i < priceArray.length; i++) {
        bo_totalprice += priceArray[i] * (countArray[i] || 0);
    }

    // 서버에서 전달된 formattedTotalWithShipping 사용
    var formattedTotalWithShippingText = $("#formattedTotalWithShipping").text().replace('₩', '').replace(/,/g, '');
    var formattedTotalWithShipping = parseInt(formattedTotalWithShippingText);

    // Submit 버튼 활성화
    if (b_idx && bo_item_name && b_name && b_tel && ba_address && ba_zipcode) {
        $('#submitButton').prop('disabled', false);
    }

    $('#submitButton').click(function (event) {
        event.preventDefault();
        var orderArray = i_idxArray.map(function (i_idx, index) {

            return {
                i_idx: i_idx,
                bos_option: optionArray[index],
                bos_price: priceArray[index],
                bos_count: countArray[index] || 0
            };
        });
        var order = {
            b_idx: parseInt(b_idx),
            bo_itemname: bo_item_name,
            bo_name: b_name,
            bo_zipcode: ba_zipcode,
            bo_address: ba_address,
            bo_contact: b_tel,
            orderStateVO: orderArray
        };
        console.log("주문 데이터:", order);

        $.ajax({
            type: "POST",
            url: "/order/insertOrder",
            data: JSON.stringify(order),
            contentType: "application/json;charset=utf-8;",
            success: function (data) {
                bo_idx = data.bo_idx;
                console.log("bo_idx:", bo_idx);
                // 결제 요청
                var merchant_uid = 'merchant_' + new Date().getTime();
                console.log("생성된 merchant_uid:", merchant_uid);

                if (!bo_idx) {
                    console.error("bo_idx가 정의되지 않았습니다.");
                    alert("주문 처리가 실패했습니다.");
                    $('#submitButton').prop('disabled', false); // 버튼 다시 활성화
                    return;
                }
                IMP.request_pay({
                    pg: "kcp",
                    pay_method: "card",
                    merchant_uid: merchant_uid,
                    name: bo_item_name,
                    amount: formattedTotalWithShipping, // 숫자형
                    buyer_name: b_name,
                    buyer_tel: b_tel,
                    buyer_addr: ba_address,
                    buyer_postcode: ba_zipcode,
                }, function (rsp) {
                    try {
                        console.log("결제 응답:", rsp);
                        if (rsp.success) {
                            // 결제 성공 처리
                            $.ajax({
                                url: "/order/payment",
                                type: "POST",
                                contentType: "application/json",
                                data: JSON.stringify({
                                    merchantUid: rsp.merchant_uid,
                                    amount: rsp.paid_amount,
                                    bo_idx: bo_idx
                                }),
                                success: function (response) {
                                    console.log("결제 정보가 저장되었습니다:", response);
                                    // 주문을 성공적으로 처리
                                    $.ajax({
                                        type: "POST",
                                        url: "/order/successOrder",
                                        data: JSON.stringify(order),
                                        contentType: "application/json;charset=utf-8;",
                                        success: function (data) {
                                            alert("결제가 성공적으로 완료되었습니다.");
                                            window.location.href = "/";
                                        },
                                        error: function (xhr, status, error) {
                                            console.error("주문 처리 실패:", error);
                                        }
                                    });
                                },
                                error: function (xhr, status, error) {
                                    alert("결제 정보 전송에 실패했습니다.");
                                    console.error("결제 정보 전송 실패:", error);
                                }
                            });
                        } else {
                            // 결제 실패 처리
                            alert("결제에 실패했습니다: " + rsp.error_msg);
                            $.ajax({
                                type: "POST",
                                url: "/order/deleteOrder",
                                data: JSON.stringify({ bo_idx: bo_idx }),
                                contentType: "application/json;charset=utf-8;",
                                success: function (data) {
                                    alert("결제가 취소되었습니다.");
                                },
                                error: function (xhr, status, error) {
                                    console.error("주문 삭제 실패:", error);
                                }
                            });
                        }
                    } catch (e) {
                        console.error("결제 응답 처리 중 오류 발생:", e);
                    }
                });
            },
            error: function (xhr, status, error) {
                console.error("주문 삽입 실패:", error);
                alert("주문을 처리하는 중 오류가 발생했습니다.");
            }
        });
    });
});
