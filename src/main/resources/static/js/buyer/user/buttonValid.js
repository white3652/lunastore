let firstNameValid = false;
let lastNameValid = false;
let telValid = false;
let addressValid = false;
let zonecodeValid = false;

// 각 필드가 유효성 검사를 통과했는지 확인하고, 버튼 활성화/비활성화
function updateButtonState() {
    if (firstNameValid && lastNameValid && telValid && addressValid && zonecodeValid) {
        $('#submitButton').removeAttr('disabled').css("background-color", "#0071e3");
    } else {
        $('#submitButton').attr('disabled', 'disabled').css("background-color", "#80befb");
    }
}

$(function() {
    // 성 입력 유효성 검사
    $('#firstNameInputUpdate').on('blur keyup', function() {
        if (this.value.length === 0 || !/^[가-힣ぁ-んァ-ン一-龥]+$/.test(this.value)) {
            $('#firstNameInputUpdate').css("border", "1px solid #F74848");
            firstNameValid = false;
        } else {
            $('#firstNameInputUpdate').css("border", "1px solid #858585");
            firstNameValid = true;
        }
        updateButtonState(); // 버튼 상태 업데이트
    });

    // 이름 입력 유효성 검사
    $('#lastNameInputUpdate').on('blur keyup', function() {
        if (this.value.length === 0 || !/^[가-힣ぁ-んァ-ン一-龥]+$/.test(this.value)) {
            $('#lastNameInputUpdate').css("border", "1px solid #F74848");
            lastNameValid = false;
        } else {
            $('#lastNameInputUpdate').css("border", "1px solid #858585");
            lastNameValid = true;
        }
        updateButtonState(); // 버튼 상태 업데이트
    });

    // 전화번호 입력 유효성 검사
    $('#telInput').on('blur keyup', function() {
        const telPattern = /^01[016789]-\d{3,4}-\d{4}$/;
        if (!telPattern.test(this.value)) {
            $('#telInput').css("border", "1px solid #F74848");
            telValid = false;
        } else {
            $('#telInput').css("border", "1px solid #858585");
            telValid = true;
        }
        updateButtonState(); // 버튼 상태 업데이트
    });

    // 우편번호 유효성 검사
    $('#zonecodeInputUpdate').on('blur keyup', function() {
        if (this.value.length === 0) {
            $('#zonecodeInputUpdate').css("border", "1px solid #F74848");
            zonecodeValid = false;
        } else {
            $('#zonecodeInputUpdate').css("border", "1px solid #858585");
            zonecodeValid = true;
        }
        updateButtonState(); // 버튼 상태 업데이트
    });

    // 주소 유효성 검사
    $('#addressInputUpdate').on('blur keyup', function() {
        if (this.value.length === 0) {
            $('#addressInputUpdate').css("border", "1px solid #F74848");
            addressValid = false;
        } else {
            $('#addressInputUpdate').css("border", "1px solid #858585");
            addressValid = true;
        }
        updateButtonState(); // 버튼 상태 업데이트
    });
});