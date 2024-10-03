let birdayValid = false;

function birLength(value) {
    return value.length == 8;
}

function checkBir(str) {
    return /^(19\d{2}|20\d{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$/.test(str);
}

$(function(){
    $('#yyyyinput').blur(function () {
        if (this.value.length === 0) {
            $('#yyyyinput').css("border", "1px solid #F74848")
            $('#resultmsg').text(invalidMessage.nameEmpty)
            birdayValid = false
        }
    })
    $('#yyyyinput').keyup(function () {
        if (this.value.length !== 0) {
            if (birLength(this.value) === false) {
                birdayValid = false
                $(function () {
                    $('#yyyyinput').css("border", "1px solid #F74848")
                    $('#resultmsg').text(invalidMessage.birthInvalid)
                    $('#edsubmit').css("background-color", "#80befb")
                });
            } else if (checkBir(this.value) === false) {
                birdayValid = false
                $(function () {
                    $('#yyyyinput').css("border", "1px solid #F74848")
                    $('#resultmsg').text(invalidMessage.birthInvalid)
                    $('#edsubmit').css("background-color", "#80befb")
                });
            } else if (birLength(this.value) && checkBir(this.value)) {
                birdayValid = true
                $(function () {
                    $('#yyyyinput').css("border", "1px solid #858585")
                    $('#resultmsg').text("")
                    $('#edsubmit').css("background-color", "#0071e3")
                });
            }
        } else {
            birdayValid = false
            $(function () {
                $('#yyyyinput').css("border", "1px solid #F74848")
                $('#resultmsg').text(invalidMessage.birthInvalid)
                $('#edsubmit').css("background-color", "#80befb")
            });
        }
        try {
            birdaydisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
});
function birdaydisable() {
    if (birdayValid) {
        submit.disabled = false
    } else {
        submit.disabled = true
    }
}