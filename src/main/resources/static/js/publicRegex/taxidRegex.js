let taxidValid = false;
let taxidCheckValid = false;

// 사업자번호의 길이 검사
function taxidLength(value) {
    return value.length >= 12 && value.length <= 12;
}

$(function () {
    $('#taxidInput').blur(function () {
        if (this.value.length === 0) {
            $('#taxidInput').css("border", "1px solid #F74848")
            $('#taxidMsg').text("입력 되어 있지 않습니다.")
            taxidValid = false;
            taxidCheckValid = false;
        }
    });

    // 사업자번호 입력 중 이벤트
    $('#taxidInput').keyup(function () {
        var taxidInput = $('#taxidInput').val();

        if (taxidInput.length !== 0) {
            if (!taxidLength(taxidInput)) {
                $('#taxidInput').css("border", "1px solid #F74848");
                $('#taxidMsg').text("번호가 유효하지 않습니다.");
                taxidValid = false;
            } else {
                // 중복 여부 확인
                $.ajax({
                    type: 'post',
                    url: "/seller/businessnumCheckProcess",
                    data: { s_businessnum: taxidInput },
                    dataType: "json",
                    success: function (data) {
                        if (data === 1) {
                            $('#taxidInput').css("border", "1px solid #F74848");
                            $('#taxidMsg').text("사용 중인 사업자번호입니다.");
                            taxidCheckValid = false;
                            taxidValid = false;
                        } else {
                            $('#taxidInput').css("border", "1px solid #858585");
                            $('#taxidMsg').text("");
                            taxidCheckValid = true;
                            taxidValid = true;
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("통신 실패: " + textStatus + ", " + errorThrown);
                        console.log("응답 상태 코드: " + jqXHR.status);
                        console.log("응답 내용: " + jqXHR.responseText);
                    }
                });
            }
        } else {
            $('#taxidInput').css("border", "1px solid #F74848");
            $('#taxidMsg').text("입력 되어 있지 않습니다.");
            taxidValid = false;
        }
        try {
            taxiddisable();
        } catch (Exception) {}
        try {
            globaldisable();
        } catch (Exception) {}
    });
});