$(document).ready(function(){
    $('form').on('submit', function(e){
        var answer = $('#qna_answer').val().trim();
        if(answer.length === 0){
            alert('답변 내용을 입력하세요.');
            e.preventDefault();
        }
    });
});
