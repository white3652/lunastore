let emailValid = false;

function checkemailva(str) {
    return /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str)
}
$(function () {
    $('#emailInput').blur(function () {
        if (this.value.length === 0) {
            $('#emailInput').css("border", "1px solid #F74848")
            $('#emailMsg').text("입력 되어 있지 않습니다.")
            emailValid = false
            try {
                emailCheckValid = false
            } catch (Exception) {}
        }
    })
    $('#emailInput').keyup(function () {
        if (this.value.length !== 0) {
            if (checkemailva(this.value) === false) {
                $(function () {
                    $('#emailInput').css("border", "1px solid #F74848")
                    $('#emailMsg').text("이메일이 올바르지 않습니다.")
                    $('#active').css("height", "10px").delay(0.2)
                });
                emailValid = false
            } else if(emailCheckValid) {
                $(function () {
                    $('#emailInput').css("border", "1px solid #858585")
                    $('#emailMsg').text("")
                    $('#active').css("height", "0px").delay(0.2)
                });
                emailValid = true
            }
        } else {
            $(function () {
                $('#emailInput').css("border", "1px solid #F74848")
                $('#emailMsg').text("입력 되어 있지 않습니다.")
                $('#active').css("height", "10px").delay(0.2)
            });
            emailValid = false
        }
        try {
            emaildisable()
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
});