function updateBirthdayStyle() {
    if (yyyyinput.value.trim() !== '') {
        result.style.opacity = '1'
    }
}
$(function() {
    // 유효성 검사용 변수들 초기화
    window.emailCheckValid = false;
    let telValid = false;
    let newPwValid = false;
    let ckeckPwValid = false;
    let emailValid = false; // emailValid 변수를 초기화
    let telCheckValid = true;
    let termsValid = false;
    let firstNameValid = false;
    let lastNameValid = false;
    let birdayValid = false;
    // 이메일 형식 검사 함수
    function isValidEmail(email) {
        const emailPattern = /^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailPattern.test(email);
    }

    // 이메일 에러 메시지 설정
    function setEmailError(message) {
        $('#emailInput').css("border", "1px solid #F74848");
        $('#emailMsg').text(message);
        emailValid = false;
    }

    // 이메일 에러 메시지 초기화
    function clearEmailError() {
        $('#emailInput').css("border", "1px solid #858585");
        $('#emailMsg').text("");
        emailValid = true;
    }

    // 이메일 유효성 및 중복 체크
    $('#emailInput').on('keyup blur', function() {
        var emailInput = $(this).val();

        // 이메일 형식이 올바른지 검사
        if (!isValidEmail(emailInput)) {
            setEmailError(invalidMessage.invalidEmail);
            emailCheckValid = false;
            globaldisable();
            return;
        } else {
            clearEmailError();
        }

        // 이메일 중복 체크
        $.ajax({
            type: 'post',
            url: '/buyer/emailCheckProcess',
            data: { b_email: emailInput },
            dataType: "text",
            success: function(data) {
                if (data == 1) {
                    setEmailError(invalidMessage.emailInUse);
                    emailCheckValid = false; // 이메일 중복 체크 실패
                } else {
                    clearEmailError();
                    emailCheckValid = true; // 이메일 중복 체크 통과
                }
                globaldisable(); // 버튼 활성화 여부 체크
            },
            error: function() {
                console.log(invalidMessage.emailCheckFailed);
            }
        });
    });

    // 전화번호 유효성 검사
    $('#telInput').on('blur focus keyup', function() {
        var telPattern = /^0[19][016789]-\d{3,4}-\d{4}$/;
        telValid = telPattern.test($(this).val());

        if (telValid) {
            $('#telInput').css("border", "1px solid #858585");
            $('#telInput').css("boxShadow", "none");
        } else {
            $('#telInput').css("border", "1px solid #F74848");
            $('#telInput').css("boxShadow", "none");
        }
        saveToSessionStorage('b_tel', $(this).val());  // 세션 스토리지에 전화번호 저장
        globaldisable();
    });
    restoreFromSessionStorage('b_tel', '#telInput');  // 페이지 로드 시 전화번호 복원

    // 이름 필드 저장 및 복원 (성)
    $('#firstNameInput').on('blur', function() {
        $('#firstNameInput').css("border", "1px solid #858585");
        $('#firstNameInput').css("boxShadow", "none");
        saveToSessionStorage('b_firstname', $(this).val());  // 세션 스토리지에 성 저장
    }).on('focus', function() {
        $('#firstNameInput').css("border", "1px solid #0071E3");
        $('#firstNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3");
    });
    restoreFromSessionStorage('b_firstname', '#firstNameInput');  // 페이지 로드 시 성 복원

    // 이름 필드 저장 및 복원 (이름)
    $('#lastNameInput').on('blur', function() {
        $('#lastNameInput').css("border", "1px solid #858585");
        $('#lastNameInput').css("boxShadow", "none");
        saveToSessionStorage('b_lastname', $(this).val());  // 세션 스토리지에 이름 저장
    }).on('focus', function() {
        $('#lastNameInput').css("border", "1px solid #0071E3");
        $('#lastNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3");
    });
    restoreFromSessionStorage('b_lastname', '#lastNameInput');  // 페이지 로드 시 이름 복원

// 생년월일 필드 저장 및 복원
    $('#yyyyinput').on('blur', function() {
        // 숫자만 남기고 다른 문자 제거
        let birthValue = $(this).val().replace(/[^0-9]/g, '');

        // 입력된 값이 8자리일 때만 YYYY-MM-DD 형식으로 변환
        if (birthValue.length === 8) {
            birthValue = birthValue.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');  // YYYY-MM-DD 형식으로 변환
            $(this).val(birthValue);  // 변환된 값으로 필드를 업데이트
        }

        // 스타일 및 기타 처리
        $('#yyyyinput').css("border", "1px solid #858585");
        $('#yyyyinput').css("boxShadow", "none");
        $('#result').css("opacity", "0");
        updateBirthdayStyle();

        // 세션 스토리지에 생년월일 저장
        saveToSessionStorage('b_birth', birthValue);
    }).on('focus', function() {
        $('#yyyyinput').css("border", "1px solid #0071E3");
        $('#yyyyinput').css("boxShadow", "0px 0px 4px 0px #0071e3");
        $('#result').css("opacity", "1");
    });

// 페이지 로드 시 생년월일 복원
    restoreFromSessionStorage('b_birth', '#yyyyinput');
    // 비밀번호 필드 저장 및 복원
    $('#newPwInput, #ckeckPwInput').on('blur focus keyup', function() {
        var newPw = $('#newPwInput').val();
        var checkPw = $('#ckeckPwInput').val();
        newPwValid = newPw.length >= 8;
        ckeckPwValid = (newPw === checkPw);

        if (newPwValid) {
            $('#newPwInput').css("border", "1px solid #858585");
        } else {
            $('#newPwInput').css("border", "1px solid #F74848");
        }

        if (ckeckPwValid) {
            $('#ckeckPwInput').css("border", "1px solid #858585");
        } else {
            $('#ckeckPwInput').css("border", "1px solid #F74848");
        }
        saveToSessionStorage('b_pw', newPw);  // 비밀번호 저장
        saveToSessionStorage('b_pw_check', checkPw);  // 비밀번호 확인 저장
        globaldisable();
    });
    restoreFromSessionStorage('b_pw', '#newPwInput');  // 비밀번호 복원
    restoreFromSessionStorage('b_pw_check', '#ckeckPwInput');  // 비밀번호 확인 복원

    // 버튼 활성화/비활성화 함수
    function globaldisable() {
        // 첫 번째 페이지의 '다음' 버튼
        if (telValid && termsValid && telCheckValid){
            $('#nextPage').prop('disabled', false);
            $('#nextPageBtn').css("background-color", "#0071e3");
        } else {
            $('#nextPage').prop('disabled', true);
            $('#nextPageBtn').css("background-color", "#80befb");
        }
        // 두 번째 페이지의 '다음' 버튼
        if(firstNameValid && lastNameValid && birdayValid){
            $('#lastPage').prop('disabled', false);
            $('#lastPageBtn').css("background-color", "#0071e3");
        } else {
            $('#lastPage').prop('disabled', true);
            $('#lastPageBtn').css("background-color", "#80befb");
        }

        // 세 번째 페이지의 '가입하기' 버튼 활성화/비활성화
        $('#joinSubmit').prop('disabled', !(newPwValid && ckeckPwValid && emailCheckValid))
            .parent().css("background-color", (newPwValid && ckeckPwValid && emailCheckValid) ? "#0071e3" : "#80befb");
    }

    // 세션 스토리지에 데이터 저장
    function saveToSessionStorage(key, value) {
        sessionStorage.setItem(key, value);
    }

    // 세션 스토리지에서 데이터 복원
    function restoreFromSessionStorage(key, selector) {
        let value = sessionStorage.getItem(key);
        if (value) {
            $(selector).val(value);
        }
    }
});