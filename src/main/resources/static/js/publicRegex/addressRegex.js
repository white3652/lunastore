let zonecodeValid = false;
let addressValid = false;

$(function () {
$('#zonecodeInput').blur(function () {
	if (this.value.length === 0) {
		$('#zonecodeInput').css("border", "1px solid #F74848")
        $('#zonecodeMsg').text("입력 되어 있지 않습니다.")
        zonecodeValid = false
	}
	})
$('#zonecodeInput').blur(function () {
	if (this.value.length === 0) {
		$('#zonecodeInput').css("border", "1px solid #F74848")
        $('#zonecodeMsg').text("입력 되어 있지 않습니다.")
        zonecodeValid = false
	}
	})
    $('#zonecodeInput').blur(function () {
        if (this.value.length !== 0) {
            $(function () {
                $('#zonecodeInput').css("border", "1px solid #858585")
                $('#addressInput').css("border", "1px solid #858585")
                $('#zonecodeMsg').text("")
            });
            zonecodeValid = true
        } else {
            $(function () {
                $('#zonecodeInput').css("border", "1px solid #F74848")
                $('#zonecodeMsg').text("입력 되어 있지 않습니다.")
            });
            zonecodeValid = false
        }
        try {
            addressdisable()
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
    
    $('#addressInput').blur(function () {
	if (this.value.length === 0) {
		$('#addressInput').css("border", "1px solid #F74848")
        $('#addressMsg').text("입력 되어 있지 않습니다.")
        addressValid = false
	}
	})
    $('#addressInput').blur(function () {
        if (this.value.length !== 0) {
            $(function () {
                $('#zonecodeInput').css("border", "1px solid #858585")
                $('#addressInput').css("border", "1px solid #858585")
                $('#addressMsg').text("")
            });
            addressValid = true
        } else {
            $(function () {
                $('#addressInput').css("border", "1px solid #F74848")
                $('#addressMsg').text("입력 되어 있지 않습니다.")
            });
            addressValid = false
        }
        try {
            addressdisable()
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
});