// validation.js
// <script th:src="@{/js/common.js}"></script>

$(document).ready(function() {
    $("#emailInput").keyup(function() {
        var emailInput = $('#emailInput').val();

        $.ajax({
            type: 'post',
            url: '/buyer/emailCheckProcess?b_email=' + emailInput,
            data: { email: emailInput },
            dataType: "text",
            success: function(data) {
                if (data == 1) {
                    $("#emailMsg").text("사용중인 이메일입니다.");
                    $('#emailInput').css("border", "1px solid #F74848");
                    emailCheckValid = false;
                } else {
                    emailCheckValid = true;
                }
            },
            error: function() {
                console.log("실패");
            }
        });

        try {
            globaldisable();
        } catch (Exception) {}
    });

    $("#telInput").keyup(function() {
        var telInput = $('#telInput').val();

        $.ajax({
            type: 'post',
            url: '/buyer/telCheckProcess?b_tel=' + telInput,
            data: { tel: telInput },
            dataType: "text",
            success: function(data) {
                if (data == 1) {
                    $("#telSmg").text("사용중인 전화번호입니다.");
                    $('#telInput').css("border", "1px solid #F74848");
                    telCheckValid = false;
                } else {
                    telCheckValid = true;
                }
            },
            error: function() {
                console.log("실패");
            }
        });

        try {
            globaldisable();
        } catch (Exception) {}
    });
});