$(document).ready(function() {
    var totalAmountText = window.i18n.totalAmountText;
    // 로딩 스피너 제어
    $(document).ajaxStart(function(){
        $('#loadingSpinner').show();
    }).ajaxStop(function(){
        $('#loadingSpinner').hide();
    });

    // 전체 장바구니 금액 업데이트
    function updateTotalPrice() {
        var totalCartPrice = 0;

        $('.m_buyerItemCart_items').each(function () {
            var itemPrice = parseInt($(this).find('.itemPrice').val().replace(/[^\d]/g, ""), 10);
            var itemCount = parseInt($(this).find('.m_buyerItemCart_itemsCount').val(), 10);

            if (!isNaN(itemPrice) && !isNaN(itemCount)) {
                totalCartPrice += itemPrice * itemCount;  // 각 상품의 금액에 수량을 곱해서 합산
            }
        });

        // 소계 및 총계 업데이트
        $('.itemTotalPrice').text("₩" + totalCartPrice.toLocaleString());
        $('.allTotalPrice').text("₩" + (totalCartPrice + 3000).toLocaleString());  // 배송비 포함 계산
        $('.m_buyerItemCart_bagMainTotalPrice').text(totalAmountText + $('.allTotalPrice').text());
    }

    // 수량 변경 시 이벤트
    $('.m_buyerItemCart_itemsCount').on('input', function () {
        var $input = $(this);
        var i_idx = $input.data('item-id');
        var itemCount = parseInt($input.val(), 10);
        var b_idx = $('#b_idx').val();

        // 최소 수량 보장
        if (isNaN(itemCount) || itemCount < 1) {
            itemCount = 1;
            $input.val(itemCount);
        }

        // Ajax로 서버에 수량 업데이트
        $.ajax({
            url: '/cart/updateQuantity',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                b_idx: b_idx,
                i_idx: i_idx,
                i_count: itemCount
            }),
            success: function (response) {
                if (response.success) {
                    // 수량만 업데이트하고, 가격은 고정
                    updateTotalPrice();  // 총계 업데이트
                } else {
                    alert('수량 변경에 실패했습니다.');
                }
            },
            error: function () {
                alert('서버 오류로 수량을 변경할 수 없습니다.');
            }
        });
    });

    // 수량 증가/감소 버튼
    $('.quantity-controls .increment').click(function () {
        var $input = $(this).siblings('.m_buyerItemCart_itemsCount');
        var currentVal = parseInt($input.val(), 10);
        if (!isNaN(currentVal)) {
            $input.val(currentVal + 1).trigger('input');
        }
    });

    $('.quantity-controls .decrement').click(function () {
        var $input = $(this).siblings('.m_buyerItemCart_itemsCount');
        var currentVal = parseInt($input.val(), 10);
        if (!isNaN(currentVal) && currentVal > 1) {
            $input.val(currentVal - 1).trigger('input');
        }
    });

    // 초기 로드 시 총계 계산
    updateTotalPrice();

    $("#deleteBtn").on("click", function () {
        if (!confirm('정말로 이 아이템을 삭제하시겠습니까?')) {
            return;
        }

        var i_idx = $(this).data('item-id');
        var b_idx = $('#b_idx').val();

        $.ajax({
            url: '/cart/removeItem',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                b_idx: parseInt(b_idx),
                i_idx: parseInt(i_idx)
            }),
            success: function(response) {
                if (response === "success") {
                    alert('아이템이 삭제되었습니다.');
                    location.reload();
                } else {
                    alert('아이템 삭제에 실패했습니다.');
                }
            },
            error: function(xhr, status, error) {
                alert('서버 오류로 아이템 삭제에 실패했습니다.');
            }
        });
    });

});