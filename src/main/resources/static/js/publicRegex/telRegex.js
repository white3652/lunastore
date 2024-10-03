let telValid = false;
let telCheckValid = true;

function telLength(value) {
    return value.length >= 11 && value.length <= 13;
}

function checkTelFormat(value) {
    const telPattern = /^0[19][016789]-\d{3,4}-\d{4}$/;
    return telPattern.test(value);
}

$(function () {
    $('#telInput').blur(function () {
        if (this.value.length === 0) {
            $('#telInput').css("border", "1px solid #F74848")
            $('#telSmg').text( invalidMessage.telEmpty)
            telValid = false
            try {
                telCheckValid = false
            } catch (Exception) {}
        }
    });

    $('#telInput').keyup(function () {
        if (this.value.length !== 0) {
            if (telLength(this.value) === false || checkTelFormat(this.value) === false) {
                // 번호 길이 또는 형식이 맞지 않으면
                $('#telInput').css("border", "1px solid #F74848")
                $('#telSmg').text(invalidMessage.telInvalid)
                $('#active').css("height", "10px").delay(0.2)
                telValid = false
            } else if (telCheckValid) {
                // 번호가 유효하면
                $('#telInput').css("border", "1px solid #858585")
                $('#telSmg').text("")
                $('#active').css("height", "0px").delay(0.2)
                telValid = true
            }
        } else {
            $('#telInput').css("border", "1px solid #F74848")
            $('#telSmg').text(invalidMessage.telEmpty)
            $('#active').css("height", "10px").delay(0.2)
            telValid = false
        }
        try {
            teldisable()
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
});