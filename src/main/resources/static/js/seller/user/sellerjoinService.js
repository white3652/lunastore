$(function(){
    $('#telInput').blur(function(){
    	if(telCheckValid && telValid){
        $('#telInput').css("border", "1px solid #858585")
        $('#telInput').css("boxShadow", "none")
        }else{
        $('#telInput').css("border", "1px solid #F74848");
        $('#telInput').css("boxShadow", "none")
        }
    });

    $('#telInput').focus(function(){
        $('#telInput').css("border", "1px solid #0071E3")
        $('#telInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#firstNameInput').blur(function(){
        $('#firstNameInput').css("border", "1px solid #858585")
        $('#firstNameInput').css("boxShadow", "none")
    });
    $('#firstNameInput').focus(function(){
        $('#firstNameInput').css("border", "1px solid #0071E3")
        $('#firstNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#lastNameInput').blur(function(){
        $('#lastNameInput').css("border", "1px solid #858585")
        $('#lastNameInput').css("boxShadow", "none")
    });

    $('#lastNameInput').focus(function(){
        $('#lastNameInput').css("border", "1px solid #0071E3")
        $('#lastNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#taxidInput').blur(function(){
        if(taxidCheckValid && taxidValid){
        $('#taxidInput').css("border", "1px solid #858585")
        $('#taxidInput').css("boxShadow", "none")
        }else{
        $('#taxidInput').css("border", "1px solid #F74848");
        $('#taxidInput').css("boxShadow", "none")
        }
    });

    $('#taxidInput').focus(function(){
        $('#taxidInput').css("border", "1px solid #0071E3")
        $('#taxidInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    });

    $('#yyyyinput').blur(function(){
        $('#yyyyinput').css("border", "1px solid #858585")
        $('#yyyyinput').css("boxShadow", "none")
        $('#result').css("opacity", "0")
        updateBirthdayStyle()
    });

    let yyyyinput = document.getElementById('yyyyinput');
    let result = document.getElementById('result')

    function updateBirthdayStyle() {
        if (yyyyinput.value.trim() !== '') {
            result.style.opacity = '1'
        }
    }
    $('#yyyyinput').focus(function(){
        $('#yyyyinput').css("border", "1px solid #0071E3")
        $('#yyyyinput').css("boxShadow", "0px 0px 4px 0px #0071e3")
        $('#result').css("opacity", "1")
    });

    $('#emailInput').blur(function(){
        if(emailCheckValid && emailValid){
        $('#emailInput').css("border", "1px solid #858585")
        $('#emailInput').css("boxShadow", "none")
        }else{
        $('#emailInput').css("border", "1px solid #F74848");
        $('#emailInput').css("boxShadow", "none")
        }
    });

    $('#emailInput').focus(function(){
        $('#emailInput').css("border","1px solid #0071E3")
        $('#emailInput').css("boxShadow","0px 0px 4px 0px #0071e3")
    });

    $('#newPwInput').blur(function(){
        $('#newPwInput').css("border","1px solid #858585")
        $('#newPwInput').css("boxShadow","none")
    });
    $('#newPwInput').focus(function(){
        $('#newPwInput').css("border","1px solid #0071E3")
        $('#newPwInput').css("boxShadow","0px 0px 4px 0px #0071e3")
    });

    $('#ckeckPwInput').blur(function(){
        $('#ckeckPwInput').css("border","1px solid #858585")
        $('#ckeckPwInput').css("boxShadow","none")
    });

    $('#ckeckPwInput').focus(function(){
        $('#ckeckPwInput').css("border","1px solid #0071E3")
        $('#ckeckPwInput').css("boxShadow","0px 0px 4px 0px #0071e3")
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
});