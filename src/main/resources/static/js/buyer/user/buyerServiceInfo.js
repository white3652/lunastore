$(function () {
    $('#firstNameInput').blur(function () {
        $('#firstNameInput').css("border", "1px solid #858585")
        $('#firstNameInput').css("boxShadow", "none")
    });

    $('#firstNameInput').focus(function () {
        $('#firstNameInput').css("border", "1px solid #0071E3")
        $('#firstNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#lastNameInput').blur(function () {
        $('#lastNameInput').css("border", "1px solid #858585")
        $('#lastNameInput').css("boxShadow", "none")
    });

    $('#lastNameInput').focus(function () {
        $('#lastNameInput').css("border", "1px solid #0071E3")
        $('#lastNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

         $('#zonecodeInput').blur(function () {
        $('#zonecodeInput').css("border", "1px solid #858585")
        $('#zonecodeInput').css("boxShadow", "none")
    });
    
    $('#zonecodeInput').focus(function () {
        $('#zonecodeInput').css("border", "1px solid #0071E3")
        $('#zonecodeInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#addressInput').blur(function () {
        $('#addressInput').css("border", "1px solid #858585")
        $('#addressInput').css("boxShadow", "none")
    });
    $('#addressInput').focus(function () {
        $('#addressInput').css("border", "1px solid #0071E3")
        $('#addressInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#addAddressInput').blur(function () {
        $('#addAddressInput').css("border", "1px solid #858585")
        $('#addAddressInput').css("boxShadow", "none")
    });

    $('#addAddressInput').focus(function () {
        $('#addAddressInput').css("border", "1px solid #0071E3")
        $('#addAddressInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#viewAddressInput').blur(function () {
        $('#viewAddressInput').css("border", "1px solid #858585")
        $('#viewAddressInput').css("boxShadow", "none")
    });
    $('#viewAddressInput').focus(function () {
        $('#viewAddressInput').css("border", "1px solid #0071E3")
        $('#viewAddressInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#deledit').click(function () {
        $('#mainRegion').fadeIn(200)
        $('#delRegion').fadeIn(600)
    });

    $('#mainRegion').click(function () {
        $('#mainRegion').fadeOut(600)
        $('#delRegion').fadeOut(200)
    });

    $('#teledit').click(function () {
        $('#mainRegion').fadeIn(200)
        $('#telMainRegion').fadeIn(600)
        $('#telMainRegion').css("display", "block")
    });

    $('#mainRegion').click(function () {
        $('#mainRegion').fadeOut(600)
        $('#telMainRegion').fadeOut(200)
    });

    $('#telInput').blur(function(){
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
})