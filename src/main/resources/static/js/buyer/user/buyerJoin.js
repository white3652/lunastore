// 유효성 검사에 필요한 전역 변수 선언
var emailCheckValid = false;
var telValid = false;
var newPwValid = false;
var ckeckPwValid = false;
var emailValid = false;
var telCheckValid = true;
var termsValid = false;
var firstNameValid = false;
var lastNameValid = false;
var birthdayValid = false;

// 함수 정의
function updateBirthdayStyle() {
    if ($('#yyyyinput').val().trim() !== '') {
        $('#result').css('opacity', '1');
    }
}

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
    if(firstNameValid && lastNameValid && birthdayValid){
        $('#lastPage').prop('disabled', false);
        $('#lastPageBtn').css("background-color", "#0071e3");
    } else {
        $('#lastPage').prop('disabled', true);
        $('#lastPageBtn').css("background-color", "#80befb");
    }
    // 세 번째 페이지의 '가입하기' 버튼
    if(emailValid && newPwValid && ckeckPwValid && emailCheckValid){
        $('#joinSubmit').prop('disabled', false);
        $('#joinSubmitBtn').css("background-color", "#0071e3");
    } else {
        $('#joinSubmit').prop('disabled', true);
        $('#joinSubmitBtn').css("background-color", "#80befb");
    }
}

// 세션 스토리지에 데이터 저장 함수
function saveToSessionStorage(key, value) {
    sessionStorage.setItem(key, value);
}

// 세션 스토리지에서 데이터 복원 함수
function restoreFromSessionStorage(key, selector) {
    let value = sessionStorage.getItem(key);
    if (value) {
        $(selector).val(value);
    }
}

// 전화번호 길이 검사 함수
function telLength(value) {
    return value.length >= 11 && value.length <= 13;
}

// 전화번호 형식 검사 함수
function checkTelFormat(value) {
    const telPattern = /^0[19][016789]-\d{3,4}-\d{4}$/;
    return telPattern.test(value);
}

// 전화번호 자동 하이픈 함수
function autoHyphen(target) {
    $(target).val(function (index, value) {
        return value.replace(/[^0-9]/g, '')
            .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
    });
}

// 이름 길이 검사 함수
function firstNameLength(value) {
    return value.length >= 1 && value.length <= 3;
}

function lastNameLength(value) {
    return value.length >= 1 && value.length <= 15;
}

// 이름 유효성 검사 함수
function firstNameva(str) {
    return /^[가-힣ぁ-んァ-ン一-龯]+$/.test(str);
}

function lastNameva(str) {
    return /^[가-힣ぁ-んァ-ン一-龯]+$/.test(str);
}

// 생년월일 길이 검사 함수
function birLength(value) {
    return value.length == 8;
}

// 생년월일 유효성 검사 함수
function checkBir(str) {
    return /^(19\d{2}|20\d{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$/.test(str);
}

// 생년월일 포맷팅 함수
function formatDate(input) {
    let year = 'yyyy';
    let month = 'mm';
    let day = 'dd';

    if (input.length >= 4) {
        year = input.substring(0, 4);
    } else {
        year = input + year.substring(input.length);
    }

    if (input.length >= 6) {
        month = input.substring(4, 6);
    } else if (input.length > 4) {
        month = input.substring(4) + month.substring(input.length - 4);
    }

    if (input.length >= 8) {
        day = input.substring(6, 8);
    } else if (input.length > 6) {
        day = input.substring(6) + day.substring(input.length - 6);
    }

    return `${year}${invalidMessage.date.yearSuffix} ${month}${invalidMessage.date.monthSuffix} ${day}${invalidMessage.date.daySuffix}`;
}

// 비밀번호 길이 검사 함수
function checkPwLength(value) {
    return value.length >= 8;
}

// 비밀번호 복잡도 검사 함수
function checkPwComplexity(str) {
    return /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/.test(str);
}

// 입력 에러 표시 함수
function setInputError(inputId, msgId, message) {
    $(`#${inputId}`).css("border", "1px solid #F74848");
    $(`#${msgId}`).text(message);
}

// 입력 에러 초기화 함수
function clearInputError(inputId, msgId) {
    $(`#${inputId}`).css("border", "1px solid #858585");
    $(`#${msgId}`).text("");
}

$(function() {
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
            emailValid = true;
        }

        // 이메일 중복 체크
        $.ajax({
            type: 'post',
            url: '/buyer/emailCheckProcess',
            data: { b_email: emailInput },
            dataType: "json",
            success: function(data) {
                if (data === 1) {
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

    // 전화번호 유효성 검사 및 자동 하이픈 처리
    $('#telInput').on('blur keyup input', function () {
        var telValue = $(this).val();

        // 자동 하이픈
        autoHyphen(this);

        if (telValue.length === 0) {
            $('#telInput').css("border", "1px solid #F74848");
            $('#telSmg').text(invalidMessage.telEmpty);
            telValid = false;
            telCheckValid = false;
        } else {
            if (!telLength(telValue) || !checkTelFormat(telValue)) {
                $('#telInput').css("border", "1px solid #F74848");
                $('#telSmg').text(invalidMessage.telInvalid);
                $('#active').css("height", "10px").delay(0.2);
                telValid = false;
            } else {
                $('#telInput').css("border", "1px solid #858585");
                $('#telSmg').text("");
                $('#active').css("height", "0px").delay(0.2);
                telValid = true;
            }
        }
        saveToSessionStorage('b_tel', telValue);
        globaldisable();
    });
    // restoreFromSessionStorage('b_tel', '#telInput');  // 전화번호 복원

    // 약관 동의 체크박스 처리
    $('#terms').click(function(){
        termsValid = $(this).prop('checked');
        globaldisable();
    });

    // 이름 필드 유효성 검사 및 저장
    $('#firstNameInput').on('blur keyup', function () {
        var firstName = $(this).val();
        if (firstName.length === 0) {
            $('#firstNameInput').css("border", "1px solid #F74848");
            $('#firstNameMsg').text(invalidMessage.nameEmpty);
            firstNameValid = false;
        } else {
            if (!firstNameLength(firstName)) {
                $('#firstNameInput').css("border", "1px solid #F74848");
                $('#firstNameMsg').text(invalidMessage.nameTooLong);
                $('#active').css("height", "10px").delay(0.2);
                firstNameValid = false;
            } else if (!firstNameva(firstName)) {
                $('#firstNameInput').css("border", "1px solid #F74848");
                $('#firstNameMsg').text(invalidMessage.nameInvalid);
                $('#active').css("height", "10px").delay(0.2);
                firstNameValid = false;
            } else {
                $('#firstNameInput').css("border", "1px solid #858585");
                $('#firstNameMsg').text("");
                $('#active').css("height", "0px").delay(0.2);
                firstNameValid = true;
            }
        }
        saveToSessionStorage('b_firstname', firstName);
        globaldisable();
    });
    // restoreFromSessionStorage('b_firstname', '#firstNameInput');  // 성 복원

    $('#lastNameInput').on('blur keyup', function () {
        var lastName = $(this).val();
        if (lastName.length === 0) {
            $('#lastNameInput').css("border", "1px solid #F74848");
            $('#lastNameMsg').text(invalidMessage.nameEmpty);
            lastNameValid = false;
        } else {
            if (!lastNameLength(lastName)) {
                $('#lastNameInput').css("border", "1px solid #F74848");
                $('#lastNameMsg').text(invalidMessage.nameTooLong);
                $('#active').css("height", "10px").delay(0.2);
                lastNameValid = false;
            } else if (!lastNameva(lastName)) {
                $('#lastNameInput').css("border", "1px solid #F74848");
                $('#lastNameMsg').text(invalidMessage.nameInvalid);
                $('#active').css("height", "10px").delay(0.2);
                lastNameValid = false;
            } else {
                $('#lastNameInput').css("border", "1px solid #858585");
                $('#lastNameMsg').text("");
                $('#active').css("height", "0px").delay(0.2);
                lastNameValid = true;
            }
        }
        saveToSessionStorage('b_lastname', lastName);
        globaldisable();
    });
    // restoreFromSessionStorage('b_lastname', '#lastNameInput');  // 이름 복원

    // 생년월일 유효성 검사 및 저장
    $('#yyyyinput').on('keyup blur focus', function(){
        var input = $(this).val().replace(/[^0-9]/g, '');
        $(this).val(input);

        const formattedDate = formatDate(input);
        $('#result').text(formattedDate);

        if (input.length === 0) {
            $('#yyyyinput').css("border", "1px solid #F74848");
            $('#resultmsg').text(invalidMessage.birthInvalid);
            birthdayValid = false;
        } else {
            if (!birLength(input) || !checkBir(input)) {
                $('#yyyyinput').css("border", "1px solid #F74848");
                $('#resultmsg').text(invalidMessage.birthInvalid);
                birthdayValid = false;
            } else {
                $('#yyyyinput').css("border", "1px solid #858585");
                $('#resultmsg').text("");
                birthdayValid = true;
            }
        }
        updateBirthdayStyle();
        saveToSessionStorage('b_birth', input);
        globaldisable();
    });
    // restoreFromSessionStorage('b_birth', '#yyyyinput');  // 생년월일 복원

    // 비밀번호 유효성 검사 및 저장
    $('#newPwInput').on('blur keyup', function () {
        var newPw = $(this).val();
        if (newPw.length === 0) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.nameEmpty);
            newPwValid = false;
        } else if (!checkPwLength(newPw)) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.passwordTooShort);
            newPwValid = false;
        } else if (!checkPwComplexity(newPw)) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.passwordInvalid);
            newPwValid = false;
        } else {
            clearInputError('newPwInput', 'newPwMsg');
            newPwValid = true;
        }
        saveToSessionStorage('b_pw', newPw);
        globaldisable();
    });
    // restoreFromSessionStorage('b_pw', '#newPwInput');  // 비밀번호 복원

    $('#ckeckPwInput').on('blur keyup', function () {
        var checkPw = $(this).val();
        var newPw = $('#newPwInput').val();
        if (checkPw.length === 0) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', invalidMessage.nameEmpty);
            ckeckPwValid = false;
        } else if (checkPw !== newPw) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', invalidMessage.passwordMismatch);
            ckeckPwValid = false;
        } else {
            clearInputError('ckeckPwInput', 'ckeckPwMsg');
            ckeckPwValid = true;
        }
        saveToSessionStorage('b_pw_check', checkPw);
        globaldisable();
    });
    // restoreFromSessionStorage('b_pw_check', '#ckeckPwInput');  // 비밀번호 확인 복원

    // 페이지 이동 처리
    $('#nextPage').click(function () {
        $('#joinPage1').fadeOut(400, function () {
            $('#joinPage2').fadeIn(400);
        });
    });

    $('#lastPage').click(function () {
        $('#joinPage2').fadeOut(400, function () {
            $('#joinPage3').fadeIn(400);
        });
    });

    // 폼 제출 시 세션 스토리지 데이터를 히든 필드로 추가
    $('form').submit(function() {
        $('<input>').attr({
            type: 'hidden',
            name: 'b_tel',
            value: sessionStorage.getItem('b_tel')
        }).appendTo(this);

        $('<input>').attr({
            type: 'hidden',
            name: 'b_firstname',
            value: sessionStorage.getItem('b_firstname')
        }).appendTo(this);

        $('<input>').attr({
            type: 'hidden',
            name: 'b_lastname',
            value: sessionStorage.getItem('b_lastname')
        }).appendTo(this);

        $('<input>').attr({
            type: 'hidden',
            name: 'b_birth',
            value: sessionStorage.getItem('b_birth')
        }).appendTo(this);
    });
});
