function globaldisable() {
    if (telValid && termsValid && telCheckValid){
        nextPage.disabled = false
        $('#nextPageBtn').css("background-color", "#0071e3")
    } else {
        nextPage.disabled = true
        $('#nextPageBtn').css("background-color", "#80befb")
    }
    if(firstNameValid && lastNameValid&&birdayValid){
        lastPage.disabled = false
        $('#lastPageBtn').css("background-color", "#0071e3")
    } else {
        lastPage.disabled = true
        $('#lastPageBtn').css("background-color", "#80befb")
    }
    if(emailValid && newPwValid&&ckeckPwValid && emailCheckValid){
        joinSubmit.disabled = false
        $('#joinSubmitBtn').css("background-color", "#0071e3")
    } else {
        joinSubmit.disabled = true
        $('#joinSubmitBtn').css("background-color", "#80befb")
    }
}