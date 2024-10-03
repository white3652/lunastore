let firstNameValid = false;
let lastNameValid = false;

function firstNameLength(value) {
    return value.length >= 1 && value.length <= 3;
}

function lastNameLength(value) {
    return value.length >= 1 && value.length <= 15;
}

function firstNameva(str) {
    return /^[가-힣ぁ-んァ-ン一-龯]+$/.test(str);
}

function lastNameva(str) {
    return /^[가-힣ぁ-んァ-ン一-龯]+$/.test(str);
}
$(function(){

    $('#firstNameInput').blur(function () {
        if (this.value.length === 0) {
            $('#firstNameInput').css("border", "1px solid #F74848")
            $('#firstNameMsg').text(invalidMessage.nameEmpty)
            firstNameValid = false
        }
    })
    $('#firstNameInput').keyup(function () {
        if (this.value.length !== 0) {
            if (firstNameLength(this.value) === false) {
                $(function () {
                    $('#firstNameInput').css("border", "1px solid #F74848")
                    $('#firstNameMsg').text(invalidMessage.nameTooLong)
                    $('#active').css("height", "10px").delay(0.2)
                });
                firstNameValid = false
            } else if (firstNameva(this.value) === false) {
                $(function () {
                    $('#firstNameInput').css("border", "1px solid #F74848")
                    $('#firstNameMsg').text( invalidMessage.nameInvalid)
                    $('#active').css("height", "10px").delay(0.2)
                });
                firstNameValid = false
            } else if (firstNameLength(this.value) && firstNameva(this.value)) {
                $(function () {
                    $('#firstNameInput').css("border", "1px solid #858585")
                    $('#firstNameMsg').text("")
                    $('#active').css("height", "0px").delay(0.2)
                });
                firstNameValid = true
            }
        } else {
            $(function () {
                $('#firstNameInput').css("border", "1px solid #F74848")
                $('#firstNameMsg').text(invalidMessage.nameEmpty)
                $('#active').css("height", "10px").delay(0.2)
            });
            firstNameValid = false
        }
        try {
            namedisable()
        } catch (Exception) {}
        try {
            globaldisable()
        } catch (Exception) {}
    });
});
$(function(){
    $('#lastNameInput').blur(function () {
        if (this.value.length === 0) {
            $('#lastNameInput').css("border", "1px solid #F74848")
            $('#lastNameMsg').text(invalidMessage.nameEmpty)
            lastNameValid = false
        }
    })

    $('#lastNameInput').keyup(function () {
        if (this.value.length !== 0) {
            if (lastNameLength(this.value) === false) {
                $(function () {
                    $('#lastNameInput').css("border", "1px solid #F74848")
                    $('#lastNameMsg').text(invalidMessage.nameTooLong)
                });
                lastNameValid = false
            } else if (lastNameva(this.value) === false) {
                $(function () {
                    $('#lastNameInput').css("border", "1px solid #F74848")
                    $('#lastNameMsg').text(invalidMessage.nameInvalid)
                });
                lastNameValid = false
            } else if (lastNameLength(this.value) && lastNameva(this.value)) {
                $(function () {
                    $('#lastNameInput').css("border", "1px solid #858585")
                    $('#lastNameMsg').text("")
                });
                lastNameValid = true
            }
        } else {
            $(function () {
                $('#lastNameInput').css("border", "1px solid #F74848")
                $('#lastNameMsg').text(invalidMessage.nameEmpty)
            });
            lastNameValid = false
        }
        try {
            namedisable()
        } catch (Exception) {}
        try {
            globaldisable()
        } catch (Exception) {}
    });

    function namedisable() {
        if (firstNameValid && lastNameValid) {
            namesubmit1.disabled = false
            $(function () {
                $('#edsubmit2').css("background-color", "#0071e3")
            });
        } else {
            namesubmit1.disabled = true
            $(function () {
                $('#edsubmit2').css("background-color", "#80befb")
            });
        }
    }
})