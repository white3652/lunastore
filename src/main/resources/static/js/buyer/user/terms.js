$(function(){
    // 전역 변수로 정의
    window.termsValid = false;

    $('#terms').click(function(){
        // 체크박스 상태에 따라 변수 값 업데이트
        window.termsValid = $(this).prop('checked');

        try {
            globaldisable();
        } catch (Exception) {}
    });
});