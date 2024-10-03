$(function () {
    let exPwValid = true; // 에이작스 중복검사 만들고 false 만들기
    let newPwValid = false;
    let ckeckPwValid = false;

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
            setInputError('existingPwInput', 'existingPwMsg', invalidMessage.nameEmpty);
            exPwValid = false;
        }
    });

    $('#newPwInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.nameEmpty);
            newPwValid = false;
        }
        updateGlobalState();
    });

    $('#newPwInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.nameEmpty);
            newPwValid = false;
        } else if (!checkPwLength(this.value)) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.passwordTooShort);
            newPwValid = false;
        } else if (!checkPwComplexity(this.value)) {
            setInputError('newPwInput', 'newPwMsg', invalidMessage.passwordInvalid);
            newPwValid = false;
        } else {
            clearInputError('newPwInput', 'newPwMsg');
            newPwValid = true;
        }
        updateGlobalState();
    });

    $('#ckeckPwInput').blur(function () {
        if (this.value.length === 0) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', invalidMessage.nameEmpty);
            ckeckPwValid = false;
        }
        updateGlobalState();
    });

    $('#ckeckPwInput').keyup(function () {
        if (this.value.length === 0) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', invalidMessage.nameEmpty);
            ckeckPwValid = false;
        } else if (this.value !== $('#newPwInput').val()) {
            setInputError('ckeckPwInput', 'ckeckPwMsg', invalidMessage.passwordMismatch);
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