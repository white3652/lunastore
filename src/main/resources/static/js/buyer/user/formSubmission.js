// formSubmission.js
document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('form[name="buyerJoin"]').addEventListener('submit', function(event) {
        event.preventDefault(); // 기본 동작 방지
        if (validateForm()) {
            // 서버로 데이터 전송
            this.submit();
        }
    });
});