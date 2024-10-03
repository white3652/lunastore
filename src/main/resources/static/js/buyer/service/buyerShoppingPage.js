$(function () {
    // 기본 주소 수정 버튼 클릭 시 동작
    $('.m_buyerShopping_addressDefault').click(function () {
        // 기본 주소 수정 섹션을 슬라이드로 토글
        $('#updatedAddressSection').slideToggle(400, function() {
            if ($('#updatedAddressSection').is(':visible')) {
                // 슬라이드가 열릴 때 입력 필드 활성화
                $('#firstNameInputUpdate, #lastNameInputUpdate, #zonecodeInputUpdate, #addressInputUpdate').removeAttr('disabled');
                $('.m_buyerShopping_addressDefault').css("color", "#0e7ff0");  // 열릴 때 글자 색상을 파란색으로
            } else {
                // 슬라이드가 닫힐 때 입력 필드 비활성화
                $('#firstNameInputUpdate, #lastNameInputUpdate, #zonecodeInputUpdate, #addressInputUpdate').attr('disabled', true);
                $('.m_buyerShopping_addressDefault').css("color", "#6e6e73");  // 닫힐 때 글자 색상을 원래대로 복구
            }
        });

        // 새 주소 입력 섹션을 숨김
        $('#InputNewAddress').slideUp(400);
        $('#newAddress').removeClass('active');
        $('.AInput').attr("disabled", false);
        $('.nAInput').attr("disabled", true);
    });
    // 기본 주소 선택 div 클릭 시 테두리 색상 토글
    $('#defaultAddress').click(function () {
        // 테두리 색상을 토글하는 방식
        $(this).toggleClass('selected');

        if ($(this).hasClass('selected')) {
            $(this).css("border", "1px solid #0e7ff0");  // 선택 시 테두리 파란색으로 변경
        } else {
            $(this).css("border", "1px solid #848484");  // 선택 취소 시 원래 색상으로 복구
        }

        $('#newAddress').css("border", "1px solid #848484");  // 다른 섹션의 테두리 초기화
    });

    $('#newAddress').click(function () {
        $('#InputNewAddress').slideToggle(400);
        $('#updatedAddressSection').slideUp(400);
        $(this).toggleClass('active');

        if ($(this).hasClass('active')) {
            $(this).css("border", "1px solid #0e7ff0");
        } else {
            $(this).css("border", "1px solid #848484");
        }

        $('#defaultAddress').css("border", "1px solid #848484");

        var isVisible = $('#InputNewAddress').is(':visible');
        $('.nAInput').attr("disabled", !isVisible);
        $('.AInput').attr("disabled", isVisible);
    });

    $('#cardRegion').click(function(){
        $('#cardRegion').css("border", "1px solid #0e7ff0");
    });
    // 기본 주소 수정 시 우편번호 및 주소 입력 필드 클릭 이벤트
    $('#zonecodeInputUpdate, #addressInputUpdate').off('click').on('click', function(){
        execDaumPostcode();  // Daum 주소 검색 API 실행
    });

    // 기본 주소 수정 시 입력 필드 포커스 시 테두리 색상 변경
    $('#zonecodeInputUpdate, #addressInputUpdate, #plusAddressRegionUpdate, #firstNameInputUpdate, #lastNameInputUpdate').focus(function () {
        $(this).css("border", "1px solid #0e7ff0");
    });
    // 기본 주소 수정 필드 블러 시 오류 메시지 처리
    $('#firstNameInputUpdate').blur(function () {
        if (this.value.length === 0) {
            $('#firstNameMsgUpdate').text("성 입력이 필요합니다.");
            $(this).css("border", "1px solid #F74848");
        } else {
            $('#firstNameMsgUpdate').text("");
            $(this).css("border", "1px solid #858585");
        }
    });

    $('#lastNameInputUpdate').blur(function () {
        if (this.value.length === 0) {
            $('#lastNameMsgUpdate').text("이름 입력이 필요합니다.");
            $(this).css("border", "1px solid #F74848");
        } else {
            $('#lastNameMsgUpdate').text("");
            $(this).css("border", "1px solid #858585");
        }
    });

    $('#zonecodeInputUpdate').blur(function () {
        if (this.value.length === 0) {
            $('#zonecodeMsgUpdate').text("우편번호 입력이 필요합니다.");
            $(this).css("border", "1px solid #F74848");
        } else {
            $('#zonecodeMsgUpdate').text("");
            $(this).css("border", "1px solid #858585");
        }
    });

    $('#addressInputUpdate').blur(function () {
        if (this.value.length === 0) {
            $('#addressMsgUpdate').text("주소 입력이 필요합니다.");
            $(this).css("border", "1px solid #F74848");
        } else {
            $('#addressMsgUpdate').text("");
            $(this).css("border", "1px solid #858585");
        }
    });


    // 주소 입력 필드 클릭 이벤트 한 번만 등록
    $('#zonecodeInput, #addressInput').off('click').on('click', function(){
        execDaumPostcode();
    });

    // 주소 입력 필드 포커스 시 테두리 색상 변경
    $('#zonecodeInput, #addressInput').focus(function () {
        $(this).css("border", "1px solid #0e7ff0");
    });

    // 기타 입력 필드 포커스 시 테두리 색상 변경
    $('#firstNameInput, #lastNameInput, #plusAddressInput, #telInput').focus(function () {
        $(this).css("border", "1px solid #0e7ff0");
    });

    // 전화번호 입력 필드 블러 시 테두리 초기화
    $('#telInput').blur(function () {
        $(this).css("border", "");
    });

    // 이메일 입력 필드 포커스 및 블러 이벤트
    $('#emailInput').focus(function () {
        $('#emailTitle').css({
            "margin-bottom": "30px",
            "font-size": "12px"
        });
        $('#emailRegion').css("border", "1px solid #0e7ff0");
    });
    $('#emailInput').blur(function () {
        if ($(this).val() !== '') {
            $('#emailTitle').css({
                "margin-bottom": "30px",
                "font-size": "12px"
            });
            $('#emailRegion').css("border", "1px solid #0e7ff0");
        } else {
            $('#emailTitle').css({
                "margin-bottom": "",
                "font-size": ""
            });
            $('#emailRegion').css("border", "");
        }
    });
});
document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById('paymentModal');
    var openModalBtn = document.getElementById('openPaymentModal');
    var closeModalBtn = document.getElementById('closeModal');

    // 모달 열기
    openModalBtn.addEventListener('click', function() {
        modal.style.display = 'block';
    });

    // 모달 닫기
    closeModalBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // 모달 바깥 영역 클릭 시 닫기
    window.addEventListener('click', function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });
    $(function () {
        // 모달 열기 및 닫기 로직
        var modal = $('#updatedAddressModal');
        var openModalBtn = $('.m_buyerShopping_addressDefault');
        var closeModalBtn = $('#closeModal');
        var confirmBtn = $('#confirmAddressUpdate');

        // 모달 열기
        openModalBtn.click(function () {
            modal.show();
        });

        // 모달 닫기 (X 버튼 또는 확인 버튼 클릭 시 닫힘)
        closeModalBtn.click(function () {
            modal.hide();
        });

        confirmBtn.click(function () {
            // 확인 버튼 클릭 시 처리할 로직 추가 가능
            modal.hide();  // 모달 닫기
        });

        // 모달 외부 클릭 시 닫기
        $(window).click(function (event) {
            if (event.target.id === 'updatedAddressModal') {
                modal.hide();
            }
        });
    });
});