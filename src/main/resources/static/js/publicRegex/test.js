let zonecodeValid = false;
let addressValid = false;
let exPwValid = true;
let newPwValid = false;
let ckeckPwValid = false;
let emailValid = false;
let firstNameValid = false;
let lastNameValid = false;
let birdayValid = false;
let termsValid = false;
let telCheckValid = false;
let taxidCheckValid = false;
let emailCheckValid = false;

// 공통 함수
function setInputError(inputId, msgId, message) {
    $(`#${inputId}`).css("border", "1px solid #F74848");
    $(`#${msgId}`).text(message);
}

function clearInputError(inputId, msgId) {
    $(`#${inputId}`).css("border", "1px solid #848484");
    $(`#${msgId}`).text("");
}

function updateButtonState(buttonId, containerId, isValid) {
    $(`#${buttonId}`).prop('disabled', !isValid);
    $(`#${containerId}`).css("background-color", isValid ? "#0071e3" : "#80befb");
}

function checkPwLength(value) {
    return value.length >= 8;
}

function checkPwComplexity(str) {
    return /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/.test(str);
}

function checkemailva(str) {
    return /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str);
}

function firstNameLength(value) {
    return value.length >= 1 && value.length <= 2;
}

function lastNameLength(value) {
    return value.length >= 1 && value.length <= 15;
}

function firstNameva(str) {
    return /^[가-힣]+$/.test(str);
}

function lastNameva(str) {
    return /^[가-힣]+$/.test(str);
}

function birLength(value) {
    return value.length == 8;
}

function checkBir(str) {
    return /^(19\d{2}|20\d{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$/.test(str);
}

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

    return `${year}년 ${month}월 ${day}일`;
}

// 공통 이벤트 처리
$(function () {
    // 우편번호 및 주소 입력 처리
    $('#zonecodeInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('zonecodeInput', 'zonecodeMsg', "입력 되어 있지 않습니다.");
            zonecodeValid = false;
        } else {
            clearInputError('zonecodeInput', 'zonecodeMsg');
            zonecodeValid = true;
        }
        updateGlobalState();
    });

    $('#addressInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('addressInput', 'addressMsg', "입력 되어 있지 않습니다.");
            addressValid = false;
        } else {
            clearInputError('addressInput', 'addressMsg');
            addressValid = true;
        }
        updateGlobalState();
    });

    // 이메일 유효성 검사
    $('#emailInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('emailInput', 'emailMsg', "입력 되어 있지 않습니다.");
            emailValid = false;
        } else if (!checkemailva(this.value)) {
            setInputError('emailInput', 'emailMsg', "이메일이 올바르지 않습니다.");
            emailValid = false;
        } else if (emailCheckValid) {
            clearInputError('emailInput', 'emailMsg');
            emailValid = true;
        }
        updateGlobalState();
    });

    // 비밀번호 유효성 검사
    $('#newPwInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('newPwInput', 'newPwMsg', "입력 되어 있지 않습니다.");
            newPwValid = false;
        } else if (!checkPwLength(this.value)) {
            setInputError('newPwInput', 'newPwMsg', "암호 길이는 최소 8자 이상입니다.");
            newPwValid = false;
        } else if (!checkPwComplexity(this.value)) {
            setInputError('newPwInput', 'newPwMsg', "암호는 대소문자, 숫자, 특수문자를 포함해야 합니다.");
            newPwValid = false;
        } else {
            clearInputError('newPwInput', 'newPwMsg');
            newPwValid = true;
        }
        updateGlobalState();
    });

    $('#ckeckPwInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', "입력 되어 있지 않습니다.");
            ckeckPwValid = false;
        } else if (this.value !== $('#newPwInput').val()) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', "암호가 일치하지 않습니다.");
            ckeckPwValid = false;
        } else {
            clearInputError('ckeckPwInput', 'ckeckPwMsg');
            ckeckPwValid = true;
        }
        updateGlobalState();
    });

    // 이름 유효성 검사
    $('#firstNameInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('firstNameInput', 'firstNameMsg', "입력 되어 있지 않습니다.");
            firstNameValid = false;
        } else if (!firstNameLength(this.value)) {
            setInputError('firstNameInput', 'firstNameMsg', "길이가 너무 깁니다.");
            firstNameValid = false;
        } else if (!firstNameva(this.value)) {
            setInputError('firstNameInput', 'firstNameMsg', "한글만 입력이 가능합니다.");
            firstNameValid = false;
        } else {
            clearInputError('firstNameInput', 'firstNameMsg');
            firstNameValid = true;
        }
        updateGlobalState();
    });

    $('#lastNameInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('lastNameInput', 'lastNameMsg', "입력 되어 있지 않습니다.");
            lastNameValid = false;
        } else if (!lastNameLength(this.value)) {
            setInputError('lastNameInput', 'lastNameMsg', "길이가 너무 깁니다.");
            lastNameValid = false;
        } else if (!lastNameva(this.value)) {
            setInputError('lastNameInput', 'lastNameMsg', "한글만 입력이 가능합니다.");
            lastNameValid = false;
        } else {
            clearInputError('lastNameInput', 'lastNameMsg');
            lastNameValid = true;
        }
        updateGlobalState();
    });

    // 생년월일 유효성 검사
    $('#yyyyinput').keyup(function () {
        const input = this.value.replace(/[^0-9]/g, '');
        const result = formatDate(input);
        $('#result').text(result);

        if (!birLength(input) || !checkBir(input)) {
            setInputError('yyyyinput', 'resultmsg', "생년월일이 유효하지 않습니다.");
            birdayValid = false;
        } else {
            clearInputError('yyyyinput', 'resultmsg');
            birdayValid = true;
        }
        updateGlobalState();
    });

    // 약관 동의 체크박스 처리
    $('#terms').click(function () {
        termsValid = $(this).prop('checked');
        updateGlobalState();
    });

    // 페이지 전환
    $('#nextPage').click(function () {
        $('#joinPage1').fadeOut(400, function () {
            $('#joinPage2').fadeIn(400);
        });
    });

    $('#middlePage').click(function () {
        $('#joinPage2').fadeOut(400, function () {
            $('#joinPage3').fadeIn(400);
        });
    });

    $('#lastPage').click(function () {
        $('#joinPage3').fadeOut(400, function () {
            $('#joinPage4').fadeIn(400);
        });
    });
});

function updateGlobalState() {
    updateButtonState('nextPage', 'nextPageBtn', zonecodeValid && addressValid);
    updateButtonState('middlePage', 'middlePageBtn', firstNameValid && lastNameValid && birdayValid);
    updateButtonState('lastPage', 'lastPageBtn', emailValid && newPwValid && ckeckPwValid);
    updateButtonState('joinSubmit', 'joinSubmitBtn', telCheckValid && emailCheckValid && taxidCheckValid);
}
