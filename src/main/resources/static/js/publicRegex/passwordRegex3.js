$(function () {

    var telCheckUrl = $('#telInput').data('tel-check-url');
    var passwordCheckUrl = $('#existingPwInput').data('password-check-url');
    var storenameCheckUrl = $('#sellerTitleInput').data('storename-check-url');
    var defaultProfileImage = $('#profileImg').data('default-profile-image');

    let telCheckValid = false;
    let exPwValid = false;
    let newPwValid = false;
    let checkPwValid = false;

    function isImageFile(file) {
        var ext = file.name.split(".").pop().toLowerCase();
        return ["jpg", "jpeg", "gif", "png"].includes(ext);
    }

    function isOverSize(file) {
        var maxSize = 3 * 1024 * 1024; // 3MB로 제한
        return file.size <= maxSize;
    }

    function setInputError(inputId, msgId, message) {
        $(`#${inputId}`).css("border", "1px solid #F74848");
        $(`#${msgId}`).text(message);
    }

    function clearInputError(inputId, msgId) {
        $(`#${inputId}`).css("border", "1px solid #848484");
        $(`#${msgId}`).text("");
    }

    // 버튼 상태 업데이트 함수
    function updateButtonState(buttonId, containerId, isValid) {
        console.log("Button valid state: ", isValid);
        $(`#${buttonId}`).prop('disabled', !isValid);
        $(`#${containerId}`).css("background-color", isValid ? "#0071e3" : "#80befb");
    }

    // 비밀번호 상태 업데이트 함수
    function updatePwDisable() {
        console.log("exPwValid: ", exPwValid, "newPwValid: ", newPwValid, "checkPwValid: ", checkPwValid);
        updateButtonState('upPwSubmit', 'checkSubmit', exPwValid && newPwValid && checkPwValid);
    }

    // 프로필 이미지 업로드 및 표시
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

    // 이미지 초기화 버튼
    $("#returnBtn1").on("click", function() {
        $("#profileImg").attr("src", defaultProfileImage); // 기본 이미지로 변경
        $("#profileImgInput").val(''); // 파일 입력 초기화
    });

    // 전화번호 유효성 검사
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
                    $('#telInput').css("border", "1px solid #F74848");
                    telCheckValid = false;
                } else {
                    $("#telSmg").text("");
                    $("#nextPage").attr("disabled", false);
                    $('#telInput').css("border", "1px solid #848484");
                    telCheckValid = true;
                }
                // 필요에 따라 다른 버튼 상태 업데이트
            },
            error: function() {
                console.log("전화번호 유효성 검사 실패");
            }
        });
    });

    // 기존 비밀번호 유효성 검사 (AJAX)
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
                    $('#existingPwInput').css('border', "");
                    // 기존 비밀번호가 일치할 경우 새로운 비밀번호 입력란 활성화
                    $('#newPwInput').prop('disabled', false);
                    $('#checkPwInput').prop('disabled', false);
                    exPwValid = true;
                } else {
                    if ($("#existingPwInput").val() === "") {
                        $('#existingPwInput').css("border", "1px solid #F74848");
                        $('#existingPwMsg').text(invalidMessage.nameEmpty);
                    } else {
                        $("#existingPwMsg").text("기존 비밀번호가 일치하지 않습니다.");
                        $('#existingPwInput').css("border", "1px solid #F74848");
                    }
                    // 기존 비밀번호가 일치하지 않을 경우 새로운 비밀번호 입력란 비활성화
                    $('#newPwInput').prop('disabled', true);
                    $('#checkPwInput').prop('disabled', true);
                    exPwValid = false;
                }
                updatePwDisable();
            },
            error: function() {
                console.log("기존 비밀번호 유효성 검사 실패");
            }
        });
    });

    // 새로운 비밀번호 유효성 검사 (blur 이벤트)
    $("#newPwInput").blur(function () {
        if (this.value.length === 0) {
            setInputError('newPwInput', 'newPwMsg', "입력되어 있지 않습니다.");
            newPwValid = false;
        }
        updatePwDisable();
    });

    // 새로운 비밀번호 유효성 검사 (keyup 이벤트)
    $("#newPwInput").keyup(function () {
        var newPw = this.value;

        if (newPw.length === 0) {
            setInputError('newPwInput', 'newPwMsg', "입력되어 있지 않습니다.");
            newPwValid = false;
        } else if (newPw.length < 8) {
            setInputError('newPwInput', 'newPwMsg', "암호 길이는 최소 8자 이상입니다.");
            newPwValid = false;
        } else if (!/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/.test(newPw)) {
            setInputError('newPwInput', 'newPwMsg', "암호는 대소문자, 숫자, 특수문자를 포함해야 합니다.");
            newPwValid = false;
        } else {
            clearInputError('newPwInput', 'newPwMsg');
            newPwValid = true;
        }

        // 비밀번호 확인과 일치 여부 검증
        var checkPw = $('#checkPwInput').val();
        if (checkPw.length > 0) {
            if (checkPw !== newPw) {
                setInputError('checkPwInput', 'checkPwMsg', "암호가 일치하지 않습니다.");
                checkPwValid = false;
            } else {
                clearInputError('checkPwInput', 'checkPwMsg');
                checkPwValid = true;
            }
        }

        updatePwDisable();
    });

    // 비밀번호 확인 유효성 검사 (blur 이벤트)
    $("#checkPwInput").blur(function () {
        var checkPwInput = this.value;
        var newPw = $('#newPwInput').val();

        if (checkPwInput.length === 0) {
            setInputError('checkPwInput', 'checkPwMsg', "입력되어 있지 않습니다.");
            checkPwValid = false;
        } else if (checkPwInput !== newPw) {
            setInputError('checkPwInput', 'checkPwMsg', "암호가 일치하지 않습니다.");
            checkPwValid = false;
        } else {
            clearInputError('checkPwInput', 'checkPwMsg');
            checkPwValid = true;
        }

        console.log("checkPwValid: ", checkPwValid);
        updatePwDisable();
    });

    // 비밀번호 확인 유효성 검사 (keyup 이벤트)
    $("#checkPwInput").keyup(function () {
        var checkPwInput = this.value;
        var newPw = $('#newPwInput').val();

        if (checkPwInput.length === 0) {
            setInputError('checkPwInput', 'checkPwMsg', "입력되어 있지 않습니다.");
            checkPwValid = false;
        } else if (checkPwInput !== newPw) {
            setInputError('checkPwInput', 'checkPwMsg', "암호가 일치하지 않습니다.");
            checkPwValid = false;
        } else {
            clearInputError('checkPwInput', 'checkPwMsg');
            checkPwValid = true;
        }

        console.log("checkPwValid: ", checkPwValid);
        updatePwDisable();
    });

    // 스토어 이름 유효성 검사
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
                    $('#sellerTitleInput').css("border", "1px solid #F74848");
                    $('#sellerTitleMsg').text("이름이 있어야 합니다.");
                } else if (data == 1) {
                    $("#sellerTitleMsg").text("사용중인 매장이름입니다.");
                    $("#upSellerSubmit").attr("disabled", true);
                    $('#sellerTitleInput').css("border", "1px solid #F74848");
                } else {
                    $('#sellerTitleInput').css("border", "1px solid #848484");
                    $("#sellerTitleMsg").text("");
                    $("#upSellerSubmit").attr("disabled", false);
                }
            },
            error: function() {
                console.log("스토어 이름 유효성 검사 실패");
            }
        });
    });

});
