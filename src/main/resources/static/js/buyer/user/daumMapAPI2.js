// Daum 우편번호 API 사용 여부를 추적하기 위한 전역 변수
window.daumPostcodeUsed = false;

$(function () {
    let windowOpened = false; // 주소 검색창이 열렸는지 확인하는 변수

    // 클릭 이벤트 처리 - zonecodeInput과 zonecodeInputUpdate
    $('#ba_zipcode, #ba_zipcode_new').on('click', function () {
        if (!windowOpened) {  // 창이 열려있지 않을 때만 실행
            execDaumPostcode(this.id);
        }
    });

    // 입력 필드에서 벗어나면 창을 다시 열 수 있도록 설정
    $('#ba_zipcode, #ba_zipcode_new, #ba_address, #ba_address_new').on('blur', function () {
        windowOpened = false; // 다른 필드로 이동 시 창이 다시 열릴 수 있도록 설정
    });
});

// Daum 우편번호 API 실행 함수
function execDaumPostcode(targetId) {
    windowOpened = true;  // 창이 열렸다고 표시
    new daum.Postcode({
        oncomplete: function (data) {
            window.daumPostcodeUsed = true;  // API 사용 여부를 true로 설정

            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 도로명 주소 참고항목 처리
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            // 클릭된 필드에 따라 우편번호와 주소를 설정
            if (targetId === 'ba_zipcode') {
                $('#ba_zipcode').val(data.zonecode);
                $('#ba_address').val(addr + extraAddr);
                zonecodeUpdateValid = true; // 유효성 검사 통과 플래그 설정
                addressUpdateValid = true; // 유효성 검사 통과 플래그 설정
            } else if (targetId === 'ba_zipcode_new') {
                $('#ba_zipcode_new').val(data.zonecode);
                $('#ba_address_new').val(addr + extraAddr);
                zonecodeNewValid = true; // 유효성 검사 통과 플래그 설정
                addressNewValid = true; // 유효성 검사 통과 플래그 설정
                updateButtonState();
            }

            updateButtonState(); // 상태 업데이트
            windowOpened = false; // 창이 닫힌 후 다시 열릴 수 있도록 초기화
        },
        onclose: function () {
            windowOpened = false;  // 창이 닫히면 다시 열릴 수 있도록 설정
        }
    }).open();
}
