$(document).ready(function () {
    $('.m_buyerOrderView_item').each(function () {
        var $item = $(this);
        var statusCode = $item.find('.m_buyerOrderView_status').data('status');
        console.log("Status Code: ", statusCode);
        var $statusBar = $item.find('.statusBar');
        var $review = $item.find('.review');

        // 상태에 따른 처리
        switch(statusCode) {
            case 1: // 결제완료
                $statusBar.css("width", "15%");
                $item.find('.statusle1').css("color", "#111111");
                $item.find('.statusle2, .statusle3, .statusle4').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
            case 2: // 배송준비
                $statusBar.css("width", "35%");
                $item.find('.statusle1, .statusle2').css("color", "#111111");
                $item.find('.statusle3, .statusle4').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
            case 3: // 배송중
                $statusBar.css("width", "65%");
                $item.find('.statusle1, .statusle2, .statusle3').css("color", "#111111");
                $item.find('.statusle4').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
            case 4: // 배송완료
            case 10: // 구매확정
                $statusBar.css("width", "100%");
                $item.find('.statusle1, .statusle2, .statusle3, .statusle4').css("color", "#111111");
                $review.fadeIn(400).css("opacity", "1");
                break;
            case 5: // 주문취소
                $statusBar.css("width", "0%");
                $item.find('.statusle5').css("color", "#FF0000"); // 빨간색으로 강조
                $item.find('.statusle1, .statusle2, .statusle3, .statusle4').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
            case 6: // 환불
                $statusBar.css("width", "0%");
                $item.find('.statusle6').css("color", "#FFA500"); // 주황색으로 강조
                $item.find('.statusle1, .statusle2, .statusle3, .statusle4').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
            case 7: // 교환
                $statusBar.css("width", "0%");
                $item.find('.statusle7').css("color", "#0000FF"); // 파란색으로 강조
                $item.find('.statusle1, .statusle2, .statusle3, .statusle4').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
            default: // 알 수 없음
                $statusBar.css("width", "0%");
                $item.find('.m_buyerOrderView_statusle').css("color", "");
                $review.fadeOut(400).css("opacity", "");
                break;
        }
    });
    let isRequesting = false;
    // 구매확정 버튼 클릭 시 메시지 국제화 적용
    $('.m_buyerOrdrView_confirmBtn').on('click', function(event) {
        if (isRequesting) return; // 이미 요청 중이면 무시
        isRequesting = true;
        if (!confirm(messages.confirmPurchase)) {
            event.preventDefault(); // 확인을 취소하면 폼 제출 방지
        }
    });

    // 주문취소 버튼 클릭 시 메시지 국제화 적용
    $('.m_buyerOrdrView_cancelBtn').on('click', function(event) {
        if (!confirm(messages.confirmCancel)) {
            event.preventDefault(); // 확인을 취소하면 폼 제출 방지
        }
    });
});
