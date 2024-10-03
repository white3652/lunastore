let firstNameValid = false;
let lastNameValid = false;

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

$(function() {
    // 성 입력 필드 처리 (updated id 적용)
    $('#firstNameInputUpdate').on('blur keyup', function () {
        if (this.value.length === 0) {
            $('#firstNameInputUpdate').css("border", "1px solid #F74848");
            $('#firstNameMsgUpdate').text("입력 되어 있지 않습니다.");
            firstNameValid = false;
        } else if (firstNameLength(this.value) === false) {
            $('#firstNameInputUpdate').css("border", "1px solid #F74848");
            $('#firstNameMsgUpdate').text("길이가 너무 깁니다.");
            firstNameValid = false;
        } else if (firstNameva(this.value) === false) {
            $('#firstNameInputUpdate').css("border", "1px solid #F74848");
            $('#firstNameMsgUpdate').text("한글만 입력이 가능합니다.");
            firstNameValid = false;
        } else {
            $('#firstNameInputUpdate').css("border", "1px solid #858585");
            $('#firstNameMsgUpdate').text("");
            firstNameValid = true;
        }

        try {
            namedisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });

    // 이름 입력 필드 처리 (updated id 적용)
    $('#lastNameInputUpdate').on('blur keyup', function () {
        if (this.value.length === 0) {
            $('#lastNameInputUpdate').css("border", "1px solid #F74848");
            $('#lastNameMsgUpdate').text("입력 되어 있지 않습니다.");
            lastNameValid = false;
        } else if (lastNameLength(this.value) === false) {
            $('#lastNameInputUpdate').css("border", "1px solid #F74848");
            $('#lastNameMsgUpdate').text("길이가 너무 깁니다.");
            lastNameValid = false;
        } else if (lastNameva(this.value) === false) {
            $('#lastNameInputUpdate').css("border", "1px solid #F74848");
            $('#lastNameMsgUpdate').text("한글만 입력이 가능합니다.");
            lastNameValid = false;
        } else {
            $('#lastNameInputUpdate').css("border", "1px solid #858585");
            $('#lastNameMsgUpdate').text("");
            lastNameValid = true;
        }

        try {
            namedisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });

    // 이름 필드 유효성 검사에 따른 버튼 활성화/비활성화
    function namedisable() {
        if (firstNameValid && lastNameValid) {
            namesubmit1.disabled = false;
            $('#edsubmit2').css("background-color", "#0071e3");
        } else {
            namesubmit1.disabled = true;
            $('#edsubmit2').css("background-color", "#80befb");
        }
    }
});