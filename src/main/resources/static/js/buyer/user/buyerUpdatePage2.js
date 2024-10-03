$(function () {
    $('#m_userinfosclo_updatename_region_buer').click(function () {
        $('#m_userinfosclo_updatename_region_buer').fadeOut(400)
        $('#m_userinfosclo_updatename_region_name').fadeOut(400)
        $('#m_userinfosclo_updatename_region_bday').fadeOut(400)
    });

    $('#m_userinfosclo_contentday').click(function () {
        $('#m_userinfosclo_updatename_region_buer').fadeIn(400)
        $('#m_userinfosclo_updatename_region_bday').fadeIn(400)
    });

    $('#m_userinfosclo_contentname').click(function () {
        $('#m_userinfosclo_updatename_region_buer').fadeIn(400)
        $('#m_userinfosclo_updatename_region_name').fadeIn(400)
    });

    $('#existingPwInput').blur(function () {
        if(duplicatePw){
        $('#existingPwInput').css("border", "1px solid #858585")
        $('#existingPwInput').css("boxShadow", "none")
        }else{
        $('#existingPwInput').css("border", "1px solid #F74848");
        $('#existingPwInput').css("boxShadow", "none")
        }
    });

    $('#existingPwInput').focus(function () {
        $('#existingPwInput').css("border", "1px solid #0071E3'")
        $('#existingPwInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#newPwInput').blur(function () {
        if(newPwValid){
        $('#newPwInput').css("border", "1px solid #858585")
        $('#newPwInput').css("boxShadow", "none")
        }else{
        $('#newPwInput').css("border", "1px solid #F74848");
        $('#newPwInput').css("boxShadow", "none")
        }
    });

    $('#newPwInput').focus(function () {
        $('#newPwInput').css("border", "1px solid #0071E3")
        $('#newPwInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#ckeckPwInput').blur(function () {
        if(ckeckPwValid){
        $('#ckeckPwInput').css("border", "1px solid #858585")
        $('#ckeckPwInput').css("boxShadow", "none")
        }else{
        $('#ckeckPwInput').css("border", "1px solid #F74848");
        $('#ckeckPwInput').css("boxShadow", "none")
        }
    });

    $('#ckeckPwInput').focus(function () {
        $('#ckeckPwInput').css("border", "1px solid #0071E3")
        $('#ckeckPwInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#telInput').blur(function () {
        if(telCheckValid && telValid){
        $('#telInput').css("border", "1px solid #858585")
        $('#telInput').css("boxShadow", "none")
        }else{
        $('#telInput').css("border", "1px solid #F74848");
        $('#telInput').css("boxShadow", "none")
        }
    });

    $('#telInput').focus(function () {
        $('#telInput').css("border", "1px solid #0071E3")
        $('#telInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });
});