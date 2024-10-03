$(document).ready(function() {
    // 폼 제출
    $('form').on('submit', function(e) {
        e.preventDefault();
        let errorMessage = validateForm();
        if (errorMessage === '') {
            submitForm();
        } else {
            alert(errorMessage);
        }
    });

    // 폼 유효성 검사
    function validateForm() {
        // 카테고리 확인
        if ($('#categoryidx').val() === '') {
            return '카테고리를 선택해주세요.';
        }

        // 상품명 확인
        if ($('input[name="i_name"]').val().trim() === '') {
            return '상품명을 입력해주세요.';
        }

        // 가격 확인 (숫자인지 확인)
        let price = $('input[name="i_price"]').val().trim();
        if (price === '' || isNaN(price)) {
            return '유효한 가격을 입력해주세요.';
        }

        // 수량 확인
        if ($('input[name="i_count"]').val().trim() === '') {
            return '수량을 입력해주세요.';
        }

        // 이미지 업로드 확인
        if ($('.itemimgbox').children().length === 0) {
            return '최소 한 개의 이미지를 업로드해주세요.';
        }

        // 상품 설명 확인
        if ($('#itemexplain').val().trim() === '') {
            return '상세한 상품 설명을 입력해주세요.';
        }

        // 모든 검사를 통과한 경우
        return '';
    }

    // 폼 제출
    function submitForm() {
        $('form')[0].submit(); // 일반적인 폼 제출 방식 사용
    }

    // 가격 입력 유효성 검사 (숫자만 허용)
    $('input[name="i_price"]').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

    // 폼 초기화
    $('#item_reset_btn').on('click', function(e) {
        e.preventDefault();
        if (confirm('모든 입력 내용이 초기화됩니다. 계속하시겠습니까?')) {
            $('form')[0].reset();
            $('.itemimgbox').empty();
            // 리치 텍스트 에디터 내용 초기화
            // (사용 중인 에디터에 맞는 적절한 메소드를 사용해야 합니다)
        }
    });

    // 폼 취소
    $('#item_cancel_btn').on('click', function(e) {
        e.preventDefault();
        if (confirm('정말 취소하시겠습니까? 모든 입력 데이터가 손실됩니다.')) {
            window.location.href = '/seller/dashboard';
        }
    });
});