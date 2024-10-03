$(function () {
    $('#zonecodeInput').blur(function () {
        if (this.value.length === 0) {
            $('#zonecodeInput').css("border", "1px solid #F74848");
            $('#zonecodeMsg').text("입력 되어 있지 않습니다.");
            zonecodeValid = false;
        } else {
            $('#zonecodeInput').css("border", "1px solid #858585");
            $('#addressInput').css("border", "1px solid #858585");
            $('#zonecodeMsg').text("");
            zonecodeValid = true;
        }
        try {
            addressdisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });

    $('#addressInput').blur(function () {
        if (this.value.length === 0) {
            $('#addressInput').css("border", "1px solid #F74848");
            $('#addressMsg').text("입력 되어 있지 않습니다.");
            addressValid = false;
        } else {
            $('#addressInput').css("border", "1px solid #858585");
            $('#addressMsg').text("");
            addressValid = true;
        }
        try {
            addressdisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
});