$(function () {
    // 우편번호 입력 필드 처리 (updated id 적용)
    $('#zonecodeInputUpdate').blur(function () {
        if (this.value.length === 0) {
            $('#zonecodeInputUpdate').css("border", "1px solid #F74848");
            $('#zonecodeMsgUpdate').text("입력 되어 있지 않습니다.");
            zonecodeValid = false;
        } else {
            $('#zonecodeInputUpdate').css("border", "1px solid #858585");
            $('#addressInputUpdate').css("border", "1px solid #858585");
            $('#zonecodeMsgUpdate').text("");
            zonecodeValid = true;
        }
        try {
            addressdisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });

    // 주소 입력 필드 처리 (updated id 적용)
    $('#addressInputUpdate').blur(function () {
        if (this.value.length === 0) {
            $('#addressInputUpdate').css("border", "1px solid #F74848");
            $('#addressMsgUpdate').text("입력 되어 있지 않습니다.");
            addressValid = false;
        } else {
            $('#addressInputUpdate').css("border", "1px solid #858585");
            $('#addressMsgUpdate').text("");
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