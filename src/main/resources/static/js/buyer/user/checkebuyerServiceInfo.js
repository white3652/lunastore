function globaldisable(){
    if(firstNameValid && lastNameValid && zonecodeValid && addressValid ){
        addressSubmit.disabled = false
        $(function () {
            $('#addressSubmitTitle').css("background-color", "#0071e3")
        });
    }else{
        addressSubmit.disabled = true
        $(function () {
            $('#addressSubmitTitle').css("background-color", "#80befb")
        });
    }
    if(telValid){
        telSubmit.disabled = false
        $(function () {
            $('#telSubmitTitle').css("background-color", "#0071e3")
        });
    }else{
        telSubmit.disabled = true
        $(function () {
            $('#telSubmitTitle').css("background-color", "#80befb")
        });
    }
}