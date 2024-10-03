function globaldisable() {
    if (telValid && telCheckValid) {
        emailsubmit1.disabled = false
        $(function () {
            $('#edsubmit1').css("background-color", "#0071e3")
        });
    } else {
        emailsubmit1.disabled = true
        $(function () {
            $('#edsubmit1').css("background-color", "#80befb")
        });
    }
}
