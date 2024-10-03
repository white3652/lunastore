$(function () {

    var telCheckUrl = $('#telInput').data('tel-check-url');
    var passwordCheckUrl = $('#existingPwInput').data('password-check-url');
    var storenameCheckUrl = $('#sellerTitleInput').data('storename-check-url');
    var defaultProfileImage = $('#profileImg').data('default-profile-image');

    let telCheckValid = false;

    function isImageFile(file) {
        var ext = file.name.split(".").pop().toLowerCase();
        return ["jpg", "jpeg", "gif", "png"].includes(ext);
    }

    function isOverSize(file) {
        var maxSize = 3 * 1024 * 1024; // 3MB로 제한
        return file.size <= maxSize;
    }

    $("#profileImgInput").on("change", function (event) {
        var file = event.target.files[0];
        if (file) {
            if (!isImageFile(file)) {
                alert("이미지 파일만 등록할 수 있습니다.");
                this.value = ''; // 파일 입력 초기화
                return;
            }
            if (!isOverSize(file)) {
                alert("3MB 이하의 파일만 등록할 수 있습니다.");
                this.value = ''; // 파일 입력 초기화
                return;
            }
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#profileImg").attr("src", e.target.result);
            }
            reader.readAsDataURL(file);
        }
    });

    // 이미지 초기화 기능 추가
    $("#returnBtn1").on("click", function() {
        $("#profileImg").attr("src", defaultProfileImage); // 기본 이미지 경로로 변경
        $("#profileImgInput").val(''); // 파일 입력 초기화
    });

    $("#telInput").keyup(function() {
        var telInput = $('#telInput').val();

        $.ajax({
            type: 'post',
            url: telCheckUrl,
            data: { s_tel: telInput },
            success: function(data) {
                if (data == 1) {
                    $("#telSmg").text("사용중인 전화번호입니다.");
                    $("#nextPage").attr("disabled", true);
                    $('#telInput').css("border", "1px solid #F74848")
                    telCheckValid = false;
                } else {
                    $("#nextPage").attr("disabled", false);
                    telCheckValid = true;
                }
            },
            error: function() {
                console.log("실패");
            }
        });
    });

    $("#existingPwInput").keyup(function() {
        var s_idx = $('#hiddenS_idx').val();
        var existingPwInput = $('#existingPwInput').val();

        $.ajax({
            type: 'post',
            url: passwordCheckUrl,
            data: {
                s_pw: existingPwInput,
                s_idx: s_idx
            },
            success: function(data) {
                if (data == 1) {
                    $("#existingPwMsg").text("");
                    $('#existingPwInput').css('border',"")
                    // 기존 비밀번호가 일치할 경우 새로운 비밀번호 입력란 활성화
                    $('#newPwInput').prop('disabled', false);
                    $('#ckeckPwInput').prop('disabled', false);
                    $('#upPwSubmit').prop('disabled', false);
                } else {
                    if ($("#existingPwInput").val() === "") {
                        $('#existingPwInput').css("border", "1px solid #F74848")
                        $('#existingPwMsg').text(invalidMessage.nameEmpty)
                    } else {
                        $("#existingPwMsg").text(invalidMessage.passwordInvalidFormat);
                        $('#existingPwInput').css("border", "1px solid #F74848")
                    }
                    // 기존 비밀번호가 일치하지 않을 경우 새로운 비밀번호 입력란 비활성화
                    $('#newPwInput').prop('disabled', true);
                    $('#ckeckPwInput').prop('disabled', true);
                    $('#upPwSubmit').prop('disabled', true);
                }
            },
            error: function() {
                console.log("실패");
            }
        });
    });

    $("#sellerTitleInput").keyup(function() {
        var s_idx = $('#hiddenS_idx').val();
        var sellerTitleInput = $('#sellerTitleInput').val();

        $.ajax({
            type: 'post',
            url: storenameCheckUrl,
            data: {
                s_storename: sellerTitleInput,
                s_idx: s_idx
            },
            success: function(data) {
                if ($('#sellerTitleInput').val() === "") {
                    $("#upSellerSubmit").attr("disabled", true);
                    $('#sellerTitleInput').css("border", "1px solid #F74848")
                    $('#sellerTitleMsg').text(invalidMessage.nameEmpty)
                } else if (data == 1) {
                    $("#sellerTitleMsg").text("사용중인 매장이름입니다.");
                    $("#upSellerSubmit").attr("disabled", true);
                    $('#sellerTitleInput').css("border", "1px solid #F74848")
                } else {
                    $('#sellerTitleInput').css("border", "1px solid #848484")
                    $("#sellerTitleMsg").text("");
                    $("#upSellerSubmit").attr("disabled", false);
                }
            },
            error: function() {
                console.log("실패");
            }
        });
    });

});