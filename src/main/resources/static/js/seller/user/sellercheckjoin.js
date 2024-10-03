window.globaldisable = function () {
    if (telValid && termsValid){
        nextPage.disabled = false
        $('#nextPageBtn').css("background-color", "#0071e3")
    } else {
        nextPage.disabled = true
        $('#nextPageBtn').css("background-color", "#80befb")
    }
    if(firstNameValid && lastNameValid&&taxidValid&&birdayValid){
        middlePage.disabled = false
        $('#middlePageBtn').css("background-color", "#0071e3")
    } else {
        middlePage.disabled = true
        $('#middlePageBtn').css("background-color", "#80befb")
    }
    if(zonecodeValid && addressValid){
        lastPage.disabled = false
        $('#lastPageBtn').css("background-color", "#0071e3")
    } else {
        lastPage.disabled = true
        $('#lastPageBtn').css("background-color", "#80befb")
    }
    if(emailValid && newPwValid&&ckeckPwValid){
        joinSubmit.disabled = false
        $('#joinSubmitBtn').css("background-color", "#0071e3")
    } else {
        joinSubmit.disabled = true
        $('#joinSubmitBtn').css("background-color", "#80befb")
    }
}