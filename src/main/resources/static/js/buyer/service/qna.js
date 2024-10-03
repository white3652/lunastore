$(document).ready(function(){
    // Q&A 제목 클릭 및 키보드 접근성 - 이벤트 위임 사용
    $(document).on('click keypress', '.qna-title', function(e){
        console.log("Q&A title clicked or keypressed:", e.type, e.key);
        // 클릭 또는 Enter 키 이벤트 처리
        if(e.type === 'click' || e.key === 'Enter'){
            var content = $(this).closest('.qna-item').find('.qna-content');
            console.log("Toggling content for Q&A:", content);

            // 다른 Q&A 내용 닫기 및 제목 활성화 상태 제거
            $('.qna-content').not(content).slideUp();
            $('.qna-title').not(this).removeClass('active').attr('aria-expanded', 'false');

            // 현재 Q&A 내용 토글
            $(this).toggleClass('active');
            content.slideToggle(300);

            // aria-expanded 속성 업데이트
            var isExpanded = $(this).hasClass('active');
            $(this).attr('aria-expanded', isExpanded);
            console.log("Toggled Q&A content:", isExpanded);
        }
    });

    // 플래시 메시지 서서히 사라지기
    var flashMessage = $('#flashMessage');
    if(flashMessage.length){
        setTimeout(function(){
            flashMessage.fadeOut('slow');
        }, 3000); // 3초 후에 서서히 사라짐
    }

    // 제출 버튼 클릭 시 로딩 스피너 추가
    $(document).on('submit', '.qna-submit form', function(){
        var submitButton = $(this).find('button[type="submit"]');
        submitButton.prop('disabled', true).html('제출 중... <i class="fa-solid fa-spinner fa-spin"></i>');
    });

    // 답변 제출 버튼 클릭 시 로딩 스피너 추가
    $(document).on('submit', '.answer-form', function(){
        var submitButton = $(this).find('button[type="submit"]');
        submitButton.prop('disabled', true).html('제출 중... <i class="fa-solid fa-spinner fa-spin"></i>');
    });
});
