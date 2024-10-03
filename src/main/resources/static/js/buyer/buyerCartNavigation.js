$(function () {
    const b_idx = $("#b_idx").val();

    $("#cartBtn").click(function () {
        $(".global_nav_cartList").html("");
        $(".global_nav_cartView").html("");

        if (b_idx !== "") {
            $.ajax({
                type: 'post',
                url: "/cart/cartList",
                data: {
                    b_idx: b_idx
                },
                success: function (data) {
                    if (data[0]) {
                        $('.global_nav_cartList').append('<h1>' + cart.cart + '</h1>');
                    } else {
                        $('.global_nav_cartList').append('<h1>' + cart.cartEmpty + '</h1>');
                    }
                    for (let i = 0; i < 3; i++) {
                        if (data[i]) {
                            var price = parseInt(data[i].i_price);

                            var cartItem =
                                $('<div class="global_nav_cartItem">' +
                                    '<a href="/item/view?i_idx=' + data[i].i_idx + '">' +
                                    '<img src="/uploads/' + data[i].i_img + '" alt="' + cart.productImageAlt + '">' +
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
                        $('<a href="/cart/cart?b_idx=' + b_idx + '"><span>' + cart.cartCheck + '</span></a>' +
                            '<span class="global_nav_itemCount">' + cartNum + ' ' + cart.cartViewMore + '</span>');
                    $('.global_nav_cartView').append(cartOtherNum);
                },
                error: function (error) {
                    // 에러 처리 로직을 여기에 추가할 수 있습니다.
                }
            });
        } else {
            var cartItem = $('<div class="global_nav_cartItem">' +
                '<span class="global_nav_notLogin">' + cart.loginPrompt +
                '<a class="global_nav_login" href="/buyer/login">' + cart.login + '</a>' + cart.loginPrompt2 +
                '</span></div>');
            $('.global_nav_cartList').append('<h1>' + cart.cartEmpty + '</h1>');
            $('.global_nav_cartList').append(cartItem);
        }
    });
});