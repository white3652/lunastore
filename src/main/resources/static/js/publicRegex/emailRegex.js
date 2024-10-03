let emailValid = false;

function checkemailva(str) {
    return /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str)
}
$(function () {
    $('#emailInput').blur(function () {
        if (this.value.length === 0) {
            $('#emailInput').css("border", "1px solid #F74848")
            $('#emailMsg').text(invalidMessage.telEmpty)
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
                    $('#emailMsg').text(invalidMessage.invalidEmail)
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
                $('#emailMsg').text(invalidMessage.nameEmpty)
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