// buyer.js

// 유효성 검사 변수 선언
let firstNameValid = true; // 기본 주소 사용 시 기본값을 true로 설정
let lastNameValid = true;
let telValid = true;
let addressValid = true;
let zonecodeValid = true;

let orderStateList = [];
let orderVars;
// 선택 상태 변수 선언
let addressSelected = false;
let paymentSelected = false;
let contactSelected = false; // 연락처는 초기에는 선택되지 않음

// 새 주소 유효성 검사 변수
let firstNameNewValid = false;
let lastNameNewValid = false;
let addressNewValid = false;
let zonecodeNewValid = false;

// 기본 주소 유효성 검사 변수
let firstNameUpdateValid = true;
let lastNameUpdateValid = true;
let addressUpdateValid = true;
let zonecodeUpdateValid = true;
// 유효성 검사 함수
function firstNameLength(value) {
    return value.length >= 1 && value.length <= 3;
}

function lastNameLength(value) {
    return value.length >= 1 && value.length <= 15;
}

function nameValidation(str) {
    return /^[가-힣a-zA-Zぁ-んァ-ン一-龥]+$/.test(str);
}

function telLength(value) {
    return value.length >= 11 && value.length <= 13;
}

function checkTelFormat(value) {
    const telPattern = /^0[19][016789]-\d{3,4}-\d{4}$/;
    return telPattern.test(value);
}

// 기본 주소 필드 유효성 검사 함수
function validateFirstNameUpdate(value) {
    if (value.length === 0) {
        return invalidMessage.telEmpty;
    } else if (!firstNameLength(value)) {
        return invalidMessage.nameTooLong;
    } else if (!nameValidation(value)) {
        return invalidMessage.nameInvalid;
    }
    return "";
}
function validateLastNameUpdate(value) {
    if (value.length === 0) {
        return invalidMessage.nameEmpty;
    } else if (!lastNameLength(value)) {
        return invalidMessage.nameTooLong;
    } else if (!nameValidation(value)) {
        return invalidMessage.nameInvalid;
    }
    return "";
}

function validateZonecodeUpdate(value) {
    const zonecodePattern = /^\d{5}$/; // 5글자의 숫자 패턴

    if (value.length === 0) {
        return invalidMessage.zipcodeEmpty;
    } else if (!zonecodePattern.test(value)) {
        return invalidMessage.zipcodeInvalid;
    } else {
        // Daum API를 통해 입력된 우편번호가 아니라면
        if (!window.daumPostcodeUsed) {
            alert(invalidMessage.zipcodeSearchPrompt);
            execDaumPostcode('ba_zipcode');  // 또는 'ba_zipcode_new'로 트리거
            return invalidMessage.zipcodeSearchPrompt;
        }
    }
    return "";
}

function validateAddressUpdate(value) {
    if (value.length === 0) {
        return invalidMessage.nameEmpty;
    }
    return "";
}

// 버튼 상태 업데이트 함수
function updateButtonState() {
    let addressInfoValid = false;

    if ($('#defaultAddress').hasClass('selected')) {
        // 기본 주소 선택 시 유효성 검사를 통과한 것으로 간주
        addressInfoValid = firstNameUpdateValid && lastNameUpdateValid && addressUpdateValid && zonecodeUpdateValid;
    } else if ($('#newAddress').hasClass('selected')) {
        // 새 주소 선택 시 해당 입력 필드들의 유효성 검사 결과 확인
        addressInfoValid = firstNameNewValid && lastNameNewValid && addressNewValid && zonecodeNewValid;
    }

    if (addressInfoValid && telValid && paymentSelected && contactSelected) {
        $('#submitButton').removeAttr('disabled').css({
            "background-color": "#0071e3",
            "cursor": "pointer"
        });
    } else {
        $('#submitButton').attr('disabled', 'disabled').css({
            "background-color": "#80befb",
            "cursor": "not-allowed"
        });
    }
}

$(document).ready(function() {

    // 기본 주소 성 입력 유효성 검사
    $('#ba_firstname').on('blur keyup', function() {
        let value = $(this).val();
        let errorMsg = $('#firstNameMsgUpdate');

        let error = validateFirstNameUpdate(value);
        if (error) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(error).addClass('active');
            firstNameUpdateValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("").removeClass('active');
            firstNameUpdateValid = true;
        }
        updateButtonState();
    });

    // 기본 주소 이름 입력 유효성 검사
    $('#ba_lastname').on('blur keyup', function() {
        let value = $(this).val();
        let errorMsg = $('#lastNameMsgUpdate');

        let error = validateLastNameUpdate(value);
        if (error) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(error).addClass('active');
            lastNameUpdateValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("").removeClass('active');
            lastNameUpdateValid = true;
        }
        updateButtonState();
    });

    // 기본 주소 우편번호 유효성 검사
    $('#ba_zipcode').on('blur', function() {
        let value = $(this).val();
        let errorMsg = $('#zonecodeMsgUpdate');

        let error = validateZonecodeUpdate(value);
        if (error) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(error).addClass('active');
            zonecodeUpdateValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("").removeClass('active');
            zonecodeUpdateValid = true;
        }
        updateButtonState();
    });

    // 기본 주소 입력 유효성 검사
    $('#ba_address').on('blur keyup', function() {
        let value = $(this).val();
        let errorMsg = $('#addressMsgUpdate');

        let error = validateAddressUpdate(value);
        if (error) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(error).addClass('active');
            addressUpdateValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("").removeClass('active');
            addressUpdateValid = true;
        }
        updateButtonState();
    });
    // 성 입력 유효성 검사 for new address
    $('#ba_firstname_new').on('blur keyup', function() {
        let value = $(this).val();
        let errorMsg = $(this).closest('.input-group').next('.error-msg');

        if (value.length === 0) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameEmpty);
            firstNameNewValid = false;
        } else if (!firstNameLength(value)) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameTooLong);
            firstNameNewValid = false;
        } else if (!nameValidation(value)) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameInvalid);
            firstNameNewValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("");
            firstNameNewValid = true;
        }
        updateButtonState();
    });

    // 이름 입력 유효성 검사 for new address
    $('#ba_lastname_new').on('blur keyup', function() {
        let value = $(this).val();
        let errorMsg = $(this).closest('.input-group').next('.error-msg');

        if (value.length === 0) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameEmpty);
            lastNameNewValid = false;
        } else if (!lastNameLength(value)) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameTooLong);
            lastNameNewValid = false;
        } else if (!nameValidation(value)) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameInvalid);
            lastNameNewValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("");
            lastNameNewValid = true;
        }
        updateButtonState();
    });

    // 우편번호 유효성 검사 for new address
    $('#ba_zipcode_new').on('blur', function () {
        let errorMsg = $(this).closest('.input-group').next('.error-msg');

        if (this.value.length === 0) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameEmpty);
            zonecodeNewValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("");
            zonecodeNewValid = true;
        }
        updateButtonState();
    });

    // 주소 유효성 검사 for new address
    $('#ba_address_new').on('blur', function () {
        let errorMsg = $(this).closest('.input-group').next('.error-msg');

        if (this.value.length === 0) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.nameEmpty);
            addressNewValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("");
            addressNewValid = true;
        }
        updateButtonState();
    });

    // 전화번호 입력 유효성 검사
    $('#b_tel').on('blur keyup', function () {
        let value = $(this).val();
        let errorMsg = $('#telMsg');

        if (value.length === 0) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.telEmpty);
            telValid = false;
        } else if (!telLength(value) || !checkTelFormat(value)) {
            $(this).css("border", "1px solid #F74848");
            errorMsg.text(invalidMessage.telInvalid);
            telValid = false;
        } else {
            $(this).css("border", "1px solid #858585");
            errorMsg.text("");
            telValid = true;
        }
        updateButtonState();
    });

    // 기본 주소 선택 클릭 시 동작
    $('#defaultAddress').click(function () {
        $(this).toggleClass('selected');
        addressSelected = $(this).hasClass('selected');

        if ($(this).hasClass('selected')) {
            $('#newAddress').removeClass('selected');
            addressSelected = true;

            // 기본 주소 사용 시 유효성 검사 변수 초기화
            firstNameNewValid = false;
            lastNameNewValid = false;
            addressNewValid = false;
            zonecodeNewValid = false;

            $('#InputNewAddress').slideUp(400, function() {
                $('#ba_firstname_new, #ba_lastname_new, #ba_zipcode_new, #ba_address_new, #ba_restaddress_new').attr('disabled', true);
            });
            $('#updatedAddressSection').slideUp(400);
        } else {
            // 주소 선택이 해제되었으므로 유효성 검사 변수 초기화
            addressSelected = false;
        }

        updateButtonState();
    });

    // 새 주소 사용 클릭 시 동작
    $('#newAddress').click(function () {
        $(this).toggleClass('selected');
        addressSelected = $(this).hasClass('selected');

        if ($(this).hasClass('selected')) {
            $('#defaultAddress').removeClass('selected');

            // 새 주소 입력 필드 활성화
            $('#InputNewAddress').slideDown(400, function() {
                $('#ba_firstname_new, #ba_lastname_new, #ba_zipcode_new, #ba_address_new, #ba_restaddress_new').removeAttr('disabled');
            });

            // 기본 주소 사용 시 유효성 검사 변수 초기화
            firstNameNewValid = false;
            lastNameNewValid = false;
            addressNewValid = false;
            zonecodeNewValid = false;

            $('#updatedAddressSection').slideUp(400);
        } else {
            // 새 주소 입력 필드 비활성화
            $('#InputNewAddress').slideUp(400, function() {
                $('#ba_firstname_new, #ba_lastname_new, #ba_zipcode_new, #ba_address_new, #ba_restaddress_new').attr('disabled', true);
            });

            // 유효성 검사 변수 초기화
            firstNameNewValid = false;
            lastNameNewValid = false;
            addressNewValid = false;
            zonecodeNewValid = false;
        }

        updateButtonState();
    });

    // 결제 방법 선택 핸들러
    $('.payment-option').click(function() {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
            paymentSelected = false;
        } else {
            $('.payment-option').removeClass('selected');
            $(this).addClass('selected');
            paymentSelected = true;
        }

        updateButtonState();
    });

    // 연락처 선택 핸들러
    $('.contact-section').click(function() {
        $(this).toggleClass('selected');
        contactSelected = $(this).hasClass('selected');

        updateButtonState();
    });

    // 연락처 수정 버튼 클릭 시 동작
    $('.tel-update').click(function (event) {
        event.stopPropagation(); // 이벤트 전파 중지
        // 연락처 수정 폼 열기
        $('#telUpdate').slideToggle(400, function() {
            if ($(this).is(':visible')) {
                $('#b_tel').removeAttr('disabled');
                $('.tel-update').css("color", "#0e7ff0");
                $('#confirmTelEditBtn').show();
                $('#cancelTelEditBtn').show();
            } else {
                $('#b_tel').attr('disabled', true);
                $('.tel-update').css("color", "#6e6e73");
                $('#confirmTelEditBtn').hide();
                $('#cancelTelEditBtn').hide();
            }
        });
    });

    // 자동 하이픈 처리
    $('#b_tel').on('input', function () {
        autoHyphen(this);
    });

    // 연락처 수정하기 버튼 클릭 시 동작
    $('#confirmTelEditBtn').click(function() {
        // 입력된 값 가져오기
        const tel = $('#b_tel').val();
        const b_idx = $('#b_idx').val();

        // 전화번호 유효성 검사
        if (!checkTelFormat(tel)) {
            $('#telMsg').text(invalidMessage.telInvalid);
            $('#b_tel').css("border", "1px solid #F74848");
            telValid = false;
            updateButtonState();
            return;
        } else {
            $('#telMsg').text("");
            $('#b_tel').css("border", "1px solid #858585");
            telValid = true;
        }

        // AJAX 요청 보내기
        $.ajax({
            url: '/order/updateContact', // 백엔드의 OrderController에 매핑된 URL
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                b_idx: parseInt(b_idx),
                b_tel: tel
                // 필요한 다른 필드가 있다면 추가
            }),
            success: function(response) {
                if(response.success) {
                    alert('연락처가 수정되었습니다.');
                    // 폼 필드 비활성화 및 UI 업데이트
                    $('#telUpdate').slideUp(400);
                    $('#confirmTelEditBtn').hide();
                    $('#cancelTelEditBtn').hide();
                    $('#b_tel').attr('disabled', true);
                    // 업데이트된 연락처를 화면에 반영
                    $('.contact-text').text(tel);
                    updateButtonState();
                } else {
                    alert('연락처 수정에 실패했습니다: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                alert('오류가 발생했습니다: ' + error);
            }
        });
    });

    // 연락처 수정 취소 버튼 클릭 시 동작
    $('#cancelTelEditBtn').click(function() {
        // 입력된 내용을 초기화합니다.
        $('#b_tel').val($('.contact-text').text());
        // 수정 모드를 종료하고 버튼을 숨깁니다.
        $('#telUpdate').slideUp(400);
        $('#confirmTelEditBtn').hide();
        $('#cancelTelEditBtn').hide();
        // 입력 필드를 비활성화합니다.
        $('#b_tel').attr('disabled', true);
        // 유효성 검사 변수 업데이트
        telValid = checkTelFormat($('#b_tel').val());
        updateButtonState();
    });

    // 주소 수정 버튼 클릭 시 동작 (기본 주소 수정)
    $('.default-address .action-text').click(function (event) {
        event.stopPropagation();
        $('#updatedAddressSection').slideToggle(400, function() {
            if ($(this).is(':visible')) {
                $('#ba_firstname, #ba_lastname, #ba_zipcode, #ba_address, #ba_restaddress').removeAttr('disabled');
                $('.default-address .action-text').css("color", "#0e7ff0");
                $('#confirmEditBtn').show();
                $('#cancelEditBtn').show();
            } else {
                $('#ba_firstname, #ba_lastname, #ba_zipcode, #ba_address, #ba_restaddress').attr('disabled', true);
                $('.default-address .action-text').css("color", "#6e6e73");
                $('#confirmEditBtn').hide();
                $('#cancelEditBtn').hide();
            }
        });
        // 다른 섹션 닫기
        $('#InputNewAddress').slideUp(400);
        $('#newAddress').removeClass('selected');
        addressSelected = $('#defaultAddress').hasClass('selected') || $('#newAddress').hasClass('selected');
        updateButtonState();
    });

    // 주소 수정하기 버튼 클릭 시 동작
    $('#confirmEditBtn').click(function() {
        // 입력된 값 가져오기
        const firstName = $('#ba_firstname').val();
        const lastName = $('#ba_lastname').val();
        const zipcode = $('#ba_zipcode').val();
        const address = $('#ba_address').val();
        const restAddress = $('#ba_restaddress').val();
        const b_idx = $('#b_idx').val();

        // 유효성 검사
        if (!(firstNameLength(firstName) && nameValidation(firstName))) {
            alert(invalidMessage.firstNameInvalidFormat);
            return;
        }
        if (!(lastNameLength(lastName) && nameValidation(lastName))) {
            alert(invalidMessage.lastNameInvalidFormat);
            return;
        }
        if (zipcode.length === 0) {
            alert(invalidMessage.zipcodeEmpty);
            return;
        }
        if (address.length === 0) {
            alert(invalidMessage.addressEmpty);
            return;
        }

        // AJAX 요청 보내기
        $.ajax({
            url: '/order/updateAddress',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                ba_idx: 0, // 필요한 경우 수정
                b_idx: parseInt(b_idx),
                ba_firstname: firstName,
                ba_lastname: lastName,
                ba_zipcode: zipcode,
                ba_address: address,
                ba_restaddress: restAddress,
                ba_contact: '', // 필요 시 추가
                ba_check: 'Y', // 기본 주소로 설정
                b_contact: '' // 필요 시 추가
            }),
            success: function(response) {
                if(response.success) {
                    alert(invalidMessage.addressUpdateSuccess);
                    // 폼 필드 비활성화 및 UI 업데이트
                    $('#updatedAddressSection').slideUp(400);
                    $('#confirmEditBtn').hide();
                    $('#cancelEditBtn').hide();
                    $('#ba_firstname, #ba_lastname, #ba_zipcode, #ba_address, #ba_restaddress').attr('disabled', true);
                    // 업데이트된 주소를 화면에 반영
                    location.reload();
                } else {
                    alert('주소 수정에 실패했습니다: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                alert('오류가 발생했습니다: ' + error);
            }
        });
    });

    // 주소 수정 취소 버튼 클릭 시 동작
    $('#cancelEditBtn').click(function() {
        // 입력된 내용을 초기화합니다.
        // 필요에 따라 원래 값으로 복원할 수 있습니다.
        $('#updatedAddressSection').slideUp(400);
        $('#confirmEditBtn').hide();
        $('#cancelEditBtn').hide();
        $('#ba_firstname, #ba_lastname, #ba_zipcode, #ba_address, #ba_restaddress').attr('disabled', true);
    });

    // 자동 하이픈 처리 함수
    function autoHyphen(target) {
        $(target).val(function (index, value) {
            return value.replace(/[^0-9]/g, '')
                .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
        });
    }

    // 초기화 시 연락처가 기본적으로 선택되도록 설정
    // $('.contact-section').addClass('selected');
    contactSelected = false;

    // 초기 버튼 상태 업데이트
    updateButtonState();

    // 주문 및 결제 관련 변수 초기화
    let telCheckValidPayment = true;

    function initializeOrderVariables() {
        var IMP = window.IMP;
        if (!IMP) {
            console.error("아임포트 SDK가 로드되지 않았습니다.");
            return;
        }
        IMP.init("imp78130364");

        var b_idx = $("#b_idx").val();
        var bo_idx = null;
        var bo_totalprice = 0;
        var bo_item_name = $("#itemsName").val();
        var b_name = $("#buyerName").val();
        var b_tel = $("#telInput").val();
        var ba_address = $("#baAddress").text();
        var ba_zipcode = $("#zonecodeInput").val();

        var i_idxArray = [];
        var optionArray = [];
        var priceArray = [];
        var cartItemsCountValue = $('#cartItemsCount').val();

        var countArray = [];
        // 각 배열 초기화
        $('.cartItemId').each(function () {
            var val = parseInt($(this).val());
            if (!isNaN(val)) {
                i_idxArray.push(val);
            }
        });
        $('.cartOption').each(function () {
            optionArray.push($(this).text().trim());
        });
        $('.cartPrice').each(function () {
            var priceText = $(this).text().replace(/[^\d.]/g, '');
            var price = parseInt(priceText, 10);
            if (!isNaN(price)) {
                priceArray.push(price);
            }
        });
        // 가격 포맷팅
        priceArray = priceArray.map(function(price) {
            return parseFloat(price).toFixed(2);  // 소수점 둘째 자리까지 유지
        });
        console.log(priceArray);
        $('.cartCount').each(function () {
            var countText = $(this).text().trim();
            var count = parseInt(countText, 10);
            if (!isNaN(count)) {
                countArray.push(count);
            }
        });

        console.log('i_idxArray:', i_idxArray);
        console.log('optionArray:', optionArray);
        console.log('priceArray:', priceArray);
        console.log('countArray:', countArray);

        try {
            countArray = cartItemsCountValue ? JSON.parse(cartItemsCountValue) : [];
        } catch (e) {
            console.error("JSON.parse 에러:", e);
            countArray = [];
        }

        // $('.cartItems').each(function () {
        //     i_idxArray.push(parseInt($(this).val()));
        // });
        $('.cartOptions').each(function () {
            optionArray.push($(this).val());
        });
        // $('.cartPrice').each(function () {
        //     priceArray.push(parseInt($(this).val()));
        // });

        // 총 가격 계산
        for (var i = 0; i < priceArray.length; i++) {
            bo_totalprice += priceArray[i] * (countArray[i] || 0);
        }

        // 서버에서 전달된 formattedTotalWithShipping 사용
        var formattedTotalWithShippingText = $("#formattedTotalWithShipping").text().replace('₩', '').replace(/,/g, '');
        var formattedTotalWithShipping = parseInt(formattedTotalWithShippingText);

        // Submit 버튼 활성화
        if (b_idx && bo_item_name && b_name && b_tel && ba_address && ba_zipcode) {
            $('#submitButton').prop('disabled', false);
        }

        return {
            IMP: IMP,
            b_idx: b_idx,
            bo_idx: bo_idx,
            bo_totalprice: bo_totalprice,
            bo_item_name: bo_item_name,
            b_name: b_name,
            b_tel: b_tel,
            ba_address: ba_address,
            ba_zipcode: ba_zipcode,
            i_idxArray: i_idxArray,
            optionArray: optionArray,
            priceArray: priceArray,
            countArray: countArray,
            formattedTotalWithShipping: formattedTotalWithShipping
        };
    }

    $('#submitButton').click(function(event) {
        event.preventDefault();

        // 폼 유효성 검사
        if (!validateForm()) {
            return;
        }

        // 주문 및 결제 변수 초기화
        const orderVars = initializeOrderVariables();

        // 주소 제출 및 주문 생성 후 결제 처리
        submitAddress(orderVars).then(function(bo_idx) {
            submitOrderAndPay(orderVars, bo_idx);
        }).catch(function(error) {
            console.error("주문 처리 중 오류 발생:", error);
        });
    });

    // 폼 검증 함수
    function validateForm() {
        // 주소 선택 여부 확인
        if (!($('#defaultAddress').hasClass('selected') || $('#newAddress').hasClass('selected'))) {
            alert('주소를 선택해주세요.');
            return false;
        }

        // 결제 방법 선택 여부 확인
        if (!paymentSelected) {
            alert('결제 방법을 선택해주세요.');
            return false;
        }

        // 연락처 유효성 검사 확인
        if (!telValid) {
            alert(invalidMessage.contactInvalid);
            return false;
        }

        // 새 주소가 선택된 경우
        if ($('#newAddress').hasClass('selected')) {
            // 새 주소의 유효성 검사 결과 확인
            if (!(firstNameNewValid && lastNameNewValid && addressNewValid && zonecodeNewValid)) {
                alert(invalidMessage.newAddressInvalidFormat);
                return false;
            }
        }

        return true;
    }

    // 주소 제출 함수
    function submitAddress(orderVars) {
        return new Promise(function(resolve, reject) {
            let orderData = {};
            if ($('#newAddress').hasClass('selected')) {
                // 새 주소 사용 시
                orderData = {
                    // 주문 데이터
                    b_idx: $('#b_idx').val(),
                    bo_itemname: $('#itemsName').val(),
                    bo_name: $('#buyerName').val(),
                    bo_contact: $('#telInput').val(),
                    totalPrice: parseFloat($('#formattedTotalWithShipping').text().replace('₩', '').replace(/,/g, '').trim()),
                    // 주소 데이터
                    ba_firstname: $('#buyerFirstName').val(), // 구매자의 성 정보가 저장된 요소
                    ba_lastname: $('#buyerLastName').val(),   // 구매자의 이름 정보가 저장된 요소
                    ba_zipcode: $('#ba_zipcode_display').text().trim(),
                    ba_address: $('#ba_address_display').text().trim(),
                    ba_restaddress: '', // 필요 시 추가
                    bo_address: $('#baAddress').text().trim() + ' ' + $('#ba_restaddress').text().trim(),
                    bo_zipcode: $('#ba_zipcode_new').val(),
                };
            } else if ($('#defaultAddress').hasClass('selected')) {
                // 기본 주소 사용 시
                orderData = {
                    // 주문 데이터
                    b_idx: $('#b_idx').val(),
                    bo_itemname: $('#itemsName').val(),
                    bo_name: $('#buyerName').val(),
                    bo_contact: $('#telInput').val(),
                    totalPrice: parseFloat($('#formattedTotalWithShipping').text().replace('₩', '').replace(/,/g, '').trim()),
                    // 주소 데이터
                    ba_firstname: $('#buyerFirstName').val(), // 구매자의 성 정보가 저장된 요소
                    ba_lastname: $('#buyerLastName').val(),   // 구매자의 이름 정보가 저장된 요소
                    ba_zipcode: $('#ba_zipcode_display').text().trim(),
                    ba_address: $('#baAddress').text().trim() + ' ' + $('#ba_restaddress').text().trim(),
                    ba_restaddress: '', // 필요 시 추가
                    bo_address: $('#baAddress').text().trim() + ' ' + $('#ba_restaddress').text().trim(),
                    bo_zipcode: $('#zonecodeInput').val(),
                };
            }

            // 주문 항목 데이터 추가
            orderData.orderStateVO = orderVars.i_idxArray.map(function (i_idx, index) {
                return {
                    i_idx: i_idx,
                    bos_option: orderVars.optionArray[index],
                    bos_price: orderVars.priceArray[index],
                    bos_count: orderVars.countArray[index] || 0
                };
            });
            console.log('orderData.orderStateVO:', orderData.orderStateVO);
            // AJAX 요청으로 주문 생성 및 주소 제출
            $.ajax({
                url: '/order/submitNewOrder',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(orderData),
                success: function(response) {
                    if (response.success) {
                        alert('결제창으로 이동합니다.');
                        resolve(response.bo_idx); // 주문 ID 반환
                    } else {
                        alert('주문 처리에 실패했습니다: ' + response.message);
                        reject(response.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert('오류가 발생했습니다: ' + error);
                    reject(error);
                }
            });
        });
    }

    // 주문 및 결제 함수
    function submitOrderAndPay(orderVars, bo_idx) {
        // 결제 처리만 수행
        orderVars.IMP.request_pay({
            pg: "kcp",
            pay_method: "card",
            merchant_uid: "order_" + bo_idx,
            name: orderVars.bo_item_name,
            amount: orderVars.formattedTotalWithShipping,
            buyer_name: orderVars.b_name,
            buyer_tel: orderVars.b_tel,
            buyer_addr: orderVars.ba_address,
            buyer_postcode: orderVars.ba_zipcode,
        }, function (rsp) {
            if (rsp.success) {
                // 결제 성공 시 서버에 결제 정보 전송
                $.ajax({
                    url: "/order/payment",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        merchantUid: rsp.merchant_uid,
                        amount: rsp.paid_amount,
                        bo_idx: bo_idx
                    }),
                    success: function (response) {
                        alert(invalidMessage.paymentSuccess);
                        window.location.href = "/";
                    },
                    error: function (xhr, status, error) {
                        alert(invalidMessage.paymentFailed);
                    }
                });
            } else {
                // 결제 실패 시 주문 취소 처리
                alert("결제에 실패했습니다: " + rsp.error_msg);
                $.ajax({
                    type: "POST",
                    url: "/order/deleteOrder",
                    data: JSON.stringify({ bo_idx: bo_idx }),
                    contentType: "application/json;charset=utf-8;",
                    success: function (data) {
                        alert("주문이 취소되었습니다.");
                    },
                    error: function (xhr, status, error) {
                        alert("주문 취소 중 오류가 발생했습니다.");
                    }
                });
            }
        });
    }
});
