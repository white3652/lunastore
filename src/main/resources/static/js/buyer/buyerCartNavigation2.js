$(function () {
    const b_idx = $("#b_idx").val();

    $("#cartBtn").click(function () {
        $(".global_nav_cartList").html("");
        $(".global_nav_cartView").html("");

        if (b_idx !== "") {
            $.ajax({
                type: 'post',
                url: urls.cartList,
                data: {
                    b_idx: b_idx
                },
                success: function (data) {
                    if (data[0]) {
                        $('.global_nav_cartList').append('<h1>장바구니</h1>');
                    } else {
                        $('.global_nav_cartList').append('<h1>장바구니가 비어 있습니다.</h1>');
                    }
                    for (i = 0; i < 3; i++) {
                        if (data[i]) {
                            var price = parseInt(data[i].i_price);

                            var cartItem =
                                $('<div class="global_nav_cartItem">' +
                                    '<a href="/item/view?i_idx=' + data[i].i_idx + '">' +
                                    '<img src="/uploads/' + data[i].i_img + '" alt="상품이미지">' +
                                    '<div>' +
                                    '<span class="global_nav_itemName">' + data[i].i_name + '</span>' +
                                    '<span class="global_nav_itemOption">' + data[i].i_option + '</span>' +
                                    '<span class="global_nav_itemPrice">₩ ' + price.toLocaleString() + '</span>' +
                                    '</div>' +
                                    '</a>' +
                                    '</div>');

                            $('.global_nav_cartList').append(cartItem);
                        }
                    }

                    let cartNum = data.length;
                    if (data.length > 3) {
                        cartNum -= 3;
                    } else {
                        cartNum = 0;
                    }
                    var cartOtherNum =
                        $('<a href="/cart/cart?b_idx=' + b_idx + '"><span>장바구니 확인</span></a>' +
                            '<span class="global_nav_itemCount">' + cartNum + '개의 상품이 더 있습니다.</span>');
                    $('.global_nav_cartView').append(cartOtherNum);
                },
                error: function (error) {}
            }); //end of ajax
        } else {
            var cartItem = $('<div class="global_nav_cartItem">' +
                '<span class="global_nav_notLogin">저장해둔 항목이 있는지 확인하려면 ' +
                '<a class="global_nav_login" href="/buyer/login">로그인</a>' +
                '하세요.</span></div>');
            $('.global_nav_cartList').append('<h1>장바구니가 비어 있습니다.</h1>');
            $('.global_nav_cartList').append(cartItem);
        }
    });
});