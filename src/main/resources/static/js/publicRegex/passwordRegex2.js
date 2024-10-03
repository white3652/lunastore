$(function () {
    let exPwValid = true;
    window.newPwValid = false;
    window.ckeckPwValid = false;

    function checkPwLength(value) {
        return value.length >= 8;
    }

    function checkPwComplexity(str) {
        return /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/.test(str);
    }

    function setInputError(inputId, msgId, message) {
        $(`#${inputId}`).css("border", "1px solid #F74848");
        $(`#${msgId}`).text(message);
    }

    function clearInputError(inputId, msgId) {
        $(`#${inputId}`).css("border", "1px solid #848484");
        $(`#${msgId}`).text("");
    }

    function updateButtonState(buttonId, containerIdm, isValid) {
        $(`#${buttonId}`).prop('disabled', !isValid);
        $(`#${containerIdm}`).css("background-color", isValid ? "#0071e3" : "#80befb");
    }

    $('#existingPwInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('existingPwInput', 'existingPwMsg', "입력되어 있지 않습니다.");
            exPwValid = false;
        }
    });

    $('#newPwInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('newPwInput', 'newPwMsg', "입력되어 있지 않습니다.");
            newPwValid = false;
        }
        updateGlobalState();
    });

    $('#newPwInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('newPwInput', 'newPwMsg', "입력되어 있지 않습니다.");
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

    $('#ckeckPwInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', "입력되어 있지 않습니다.");
            ckeckPwValid = false;
        }
        updateGlobalState();
    });

    $('#ckeckPwInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', "입력되어 있지 않습니다.");
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

    function updateGlobalState() {
        try {
            globaldisable();
        } catch (error) {
            console.error("globaldisable 함수 호출 중 오류:", error);
        }
        updatePwDisable();
    }

    function updatePwDisable() {
        updateButtonState('pwSubmit', 'ckeckSubmit', exPwValid && newPwValid && ckeckPwValid);
    }
});