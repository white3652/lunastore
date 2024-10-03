$(document).ready(function() {
    // 모달 요소
    var modal = $('#editModal');
    var spanClose = $('.close');
    var cancelButton = $('.cancel-button');

    // "수정" 버튼 클릭 시 모달 열기
    $('.edit-button').on('click', function() {
        var qnaIdx = $(this).data('qna-idx');
        var qnaTitle = $(this).data('qna-title');
        var qnaQuestion = $(this).data('qna-question');
        var qnaAnswer = $(this).data('qna-answer');

        // 모달에 데이터 채우기
        $('#modal_qna_idx').val(qnaIdx);
        $('#modal_qna_title').val(qnaTitle);
        $('#modal_qna_question').val(qnaQuestion);
        $('#modal_qna_answer').val(qnaAnswer ? qnaAnswer : '');
        $('#modal_error_message').hide();

        // 폼 액션 업데이트 (Thymeleaf의 th:action을 무시하고 JavaScript에서 설정)
        $('#editForm').attr('action', '/seller/inquiry/edit');

        // 모달 표시
        modal.fadeIn(300);
    });

    // 닫기 버튼 클릭 시 모달 닫기
    spanClose.on('click', function() {
        modal.fadeOut(300);
    });

    // 취소 버튼 클릭 시 모달 닫기
    cancelButton.on('click', function() {
        modal.fadeOut(300);
    });

    // 모달 외부 클릭 시 모달 닫기 (제거 또는 주석 처리)
    /*
    $(window).on('click', function(event) {
        if ($(event.target).is(modal)) {
            modal.fadeOut(300);
        }
    });
    */

    // 폼 제출 시 AJAX 요청
    $('#editForm').on('submit', function(e) {
        e.preventDefault(); // 기본 폼 제출 방지

        var form = $(this);
        var formData = form.serialize();

        $.ajax({
            url: form.attr('action'),
            type: 'POST',
            data: formData,
            success: function(response) {
                alert(response); // 성공 메시지 표시
                modal.fadeOut(300);
                location.reload(); // 페이지 새로고침하여 변경 사항 반영
            },
            error: function(xhr) {
                if (xhr.status === 400) {
                    var errors = xhr.responseJSON;
                    $('#modal_error_message').html(errors.join('<br>')).show();
                } else {
                    $('#modal_error_message').text('서버 오류가 발생했습니다. 다시 시도해주세요.').show();
                }
            }
        });
    });
});
