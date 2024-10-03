$(function () {
    var m_l_view_option_count = $('.m_l_view_option').length;
    var i_idx = $("#i_idx").val();
    var i_name = $("#i_name").text();
    var i_img = $("#i_img").val();
    var i_price;
    var i_option;
    var b_idx = $("#b_idx").val();
    var s_idx = $("#s_idx").val(); // 판매자의 s_idx

    $("#getCart").click(function (e) {
        e.preventDefault();

        i_option = $('.m_l_view_getitem .m_l_view_checkedoption').text();
        i_price = $("#i_price").val();
        var i_count = $("#i_count").val();

        // Validate quantity
        if (i_count === undefined || i_count === null || i_count.trim() === "") {
            alert(messages.invalidQuantity);
            return false;
        }

        i_count = parseInt(i_count, 10);
        if (isNaN(i_count) || i_count < 1) {
            alert(messages.quantityGreaterThanZero);
            return false;
        }

        if (b_idx != "") {
            // 구매자일 경우
        } else {
            // 구매자가 아니면 (판매자 등)
            if (confirm(messages.loginRequired)) {
                window.location.href = urls.buyerLogin;
            }
            return false;
        }

        // if (m_l_view_option_count > 0) {
        //     if (m_l_view_option_count !== i_option.split(',').length || i_option == "") {
        //         alert('모든 옵션을 선택해 주세요.');
        //         return false;
        //     }
        // }

        var cartData = {
            b_idx: b_idx,
            i_idx: i_idx,
            i_name: i_name,
            i_img: i_img,
            i_option: i_option || "",
            i_price: i_price,
            i_count: i_count
        };

        $.ajax({
            type: "POST",
            url: urls.insertCart,
            data: cartData,
            success: function (data) {
                if (data.success) {
                    if (confirm(messages.cartAddSuccess)) {
                        window.location.href = urls.cart + "?b_idx=" + b_idx;
                    } else {
                        alert("장바구니에 추가되었습니다.");
                        // Optionally, update cart count/display here
                    }
                } else {
                    alert(data.message || "장바구니에 동일한 상품이 존재합니다.");
                }
            },
            error: function (xhr, status, error) {
                alert(messages.cartAddError);
            }
        });
    });
    // --- 수량 증가/감소 버튼 이벤트 추가 ---
    // 수량 증가 버튼 클릭 이벤트
    $('.quantity-controls .increment').click(function () {
        var $input = $(this).siblings('.quantity-input');
        var currentVal = parseInt($input.val());
        if (!isNaN(currentVal)) {
            $input.val(currentVal + 1).trigger('change');
        }
    });

    // 수량 감소 버튼 클릭 이벤트
    $('.quantity-controls .decrement').click(function () {
        var $input = $(this).siblings('.quantity-input');
        var currentVal = parseInt($input.val());
        if (!isNaN(currentVal) && currentVal > 1) {
            $input.val(currentVal - 1).trigger('change');
        }
    });
    // --- 수량 증가/감소 버튼 이벤트 추가 종료 ---
    if (b_idx != "") {
        $.ajax({
            type: "POST",
            url: urls.findOrder,
            data: {
                b_idx: b_idx,
                i_idx: i_idx
            },
            success: function (data) {
                if (data.length !== 0) {
                    $(".m_l_view_buyerReviewBox").append('<span class="m_l_view_buyerOrders">'+ messages.purchaseOption +'</span>');
                    $(".m_l_view_buyerReviewBox").append('<select class="m_l_view_buyerOrder"></select>');
                    for (let i = 0; i < data.length; i++) {
                        $('.m_l_view_buyerOrder').append($('<option>', {
                            value: data[i].bos_idx,
                            text: data[i].bos_option
                        }));
                    }
                    var selectStar = $('<select id="m_l_view_buyerStar">' +
                        '<option value="5">' + messages.fiveStar + '</option>' +
                        '<option value="4">' + messages.fourStar + '</option>' +
                        '<option value="3">' + messages.threeStar + '</option>' +
                        '<option value="2">' + messages.twoStar + '</option>' +
                        '<option value="1">' + messages.oneStar + '</option>' +
                        '</select>');
                    $('.m_l_view_buyerReviewBox').append(selectStar);

                    var textarea = $('<textarea>').attr({
                        'rows': '4',
                        'cols': '50',
                        'placeholder': messages.reviewPlaceholder
                    });

                    var button = $('<button>').attr({
                        'type': 'button',
                        'class': 'm_l_view_reviewBtn'
                    }).text(messages.submitReview);
                    var div = $('<div>').append(button);
                    $('.m_l_view_buyerReviewBox').after(div).after(textarea);
                } else {
                    $(".m_l_view_buyerReviewBox").append('<div><span class="m_l_view_notFoundBuyer">' + messages.noOrdersForReview + '</span></div>');
                }
            },
            error: function (xhr, status, error) {
                // 에러 처리 로직 추가 가능
                console.error("Error fetching orders:", error);
            }
        });

        $(document).on('click', '.m_l_view_reviewBtn', function () {
            var bos_idx = $('.m_l_view_buyerOrder').val();
            var br_star = $('#m_l_view_buyerStar').val();
            var br_content = $('.m_l_view_review textarea').val();

            // Validate review content
            if (br_content.trim() === "") {
                alert(messages.enterReviewContent);
                return false;
            }

            // bos_idx 검증 (필수 항목)
            if (!bos_idx || bos_idx === "0") { // bos_idx가 0인 경우도 유효하지 않음
                alert(messages.invalidOrderStatus);
                return false;
            }

            $.ajax({
                type: "POST",
                url: urls.insertReview,
                contentType: "application/json",
                data: JSON.stringify({
                    bos_idx: parseInt(bos_idx, 10),
                    br_star: parseInt(br_star, 10),
                    br_content: br_content
                }),
                success: function (data) {
                    console.log("서버 응답:", data); // 서버에서 반환된 데이터 확인

                    if (data.success) { // data.success를 통해 성공 여부 확인
                        alert(messages.reviewSubmissionSuccess);
                        location.reload(); // 페이지 새로고침
                    } else {
                        alert(data.message || "리뷰 등록에 실패했습니다.");
                    }
                },
                error: function (xhr, status, error) {
                    // 에러 처리 로직 추가 가능
                    console.error("Error submitting review:", error);
                    alert(messages.reviewSubmissionError);
                }
            });
        });
    }

    // 리뷰 검색 폼 제출 이벤트
    $('.m_l_view_review_search_form form').on('submit', function (e) {
        e.preventDefault(); // 기본 폼 제출 방지

        // 폼 데이터 수집
        var i_idx = $('input[name="i_idx"]').val();
        var b_nickname = $('input[name="b_nickname"]').val();
        var br_star = $('input[name="br_star"]:checked').val() || 0;
        var pageNum = 1; // 초기 페이지 번호

        // AJAX 요청
        $.ajax({
            type: "GET",
            url: "/item/view",
            data: {
                i_idx: i_idx,
                b_nickname: b_nickname,
                br_star: br_star,
                pageNum: pageNum
            },
            headers: {
                'X-Requested-With': 'XMLHttpRequest' // AJAX 요청임을 서버에 알림
            },
            success: function (data) {
                // 서버로부터 받은 부분 HTML을 DOM에 삽입
                $('#reviewList').html($(data).find('#reviewList').html());
                $('#pagination').html($(data).find('#pagination').html());
            },
            error: function (xhr, status, error) {
                alert("리뷰 검색에 실패했습니다.");
                console.error("Error fetching reviews:", error);
            }
        });
    });

    // 페이징 링크 클릭 이벤트 (동적으로 생성되므로 이벤트 위임 사용)
    $(document).on('click', '#pagination a', function (e) {
        e.preventDefault();

        var url = $(this).attr('href');

        // AJAX 요청
        $.ajax({
            type: "GET",
            url: url,
            headers: {
                'X-Requested-With': 'XMLHttpRequest' // AJAX 요청임을 서버에 알림
            },
            success: function (data) {
                // 서버로부터 받은 부분 HTML을 DOM에 삽입
                $('#reviewList').html($(data).find('#reviewList').html());
                $('#pagination').html($(data).find('#pagination').html());
            },
            error: function (xhr, status, error) {
                alert(messages.pageMoveError);
                console.error("Error fetching page:", error);
            }
        });
    });

    // 이미지 버튼 클릭 이벤트
    $('.m_l_view_itemimg_btn button').click(function () {
        var className = $(this).attr('class');

        $('.m_l_view_itemimg_box img').hide();
        $('.m_l_view_itemimg_box .' + className).show();

        $(".m_l_view_itemimg_btn button").css({
            'border': '2px solid #F9F9F9'
        });
        $(this).css({
            'border': '2px solid black'
        });
    });

    // 옵션 라디오 버튼 변경 이벤트
    $(".m_l_view_option input[type='radio']").change(function () {
        var selectedValues = [];

        $(".m_l_view_option input[type='radio']:checked").each(function () {
            selectedValues.push($(this).val());
        });

        $(".m_l_view_checkedoption").text(selectedValues.join(", "));

        var nextOptionBox = $(this).closest(".m_l_view_option").next(".m_l_view_option");

        if (nextOptionBox.length > 0) {
            var scrollTop = $('.m_l_view_itemoption_box').scrollTop() + nextOptionBox.position().top;

            $('.m_l_view_itemoption_box').animate({
                scrollTop: scrollTop
            }, 500);
        }
        $(".m_l_view_firstoption").prop('disabled', false);
        $(".m_l_view_firstoption").removeClass("hide");
    });

    // 옵션 초기화 버튼 클릭 이벤트
    $(".m_l_view_firstoption").click(function () {
        $(".m_l_view_option input[type='radio']").prop('checked', false);
        $(".m_l_view_firstoption").prop('disabled', true);
        $('.m_l_view_itemoption_box').animate({
            scrollTop: 0
        }, 500);
        $(".m_l_view_checkedoption").text("");
        $(".m_l_view_firstoption").addClass("hide");
    });

    // 첫 번째 이미지 버튼 트리거
    $('.m_l_view_itemimg_btn button:first').trigger('click');

    // 스크롤 버튼 클릭 이벤트
    $(".m_l_view_btn_itemexplain").click(function () {
        $('html, body').animate({
            scrollTop: $(".m_l_view_itemexplain").offset().top - 50
        }, 300);
    });

    $(".m_l_view_btn_review").click(function () {
        $('html, body').animate({
            scrollTop: $(".m_l_view_itemreview").offset().top - 50
        }, 300);
    });

    $(".m_l_view_btn_QandA").click(function () {
        $('html, body').animate({
            scrollTop: $(".m_l_view_itemQandA").offset().top - 50
        }, 300);
    });

    $(".m_l_view_btn_sellerinfo").click(function () {
        $('html, body').animate({
            scrollTop: $(".m_l_view_itemsellerinfo").offset().top - 50
        }, 300);
    });

    // 버튼 박스 고정 처리
    var boxOffsetTop = $('.m_l_view_btn_box').offset().top;
    $(window).scroll(function () {
        var scrollTop = $(window).scrollTop();
        if (scrollTop > boxOffsetTop) {
            $('.m_l_view_btn_box').addClass('fixed');
        } else {
            $('.m_l_view_btn_box').removeClass('fixed');
        }
    });

    // 스크롤 시 활성 버튼 변경
    $(window).scroll(function () {
        var scrollDistance = $(window).scrollTop();
        $('.page-section').each(function (i) {
            if ($(this).position().top <= scrollDistance + 51) {
                $('.m_l_view_btn_box button.active').removeClass('active');
                $('.m_l_view_btn_box button').eq(i).addClass('active');
            }
        });
    }).scroll();
});
