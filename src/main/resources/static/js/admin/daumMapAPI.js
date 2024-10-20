    $(function () {
        $('#zonecodetitle').click(function () {
            execDaumPostcode()
        });

        $('#addresstitle').click(function () {
            execDaumPostcode()
        });
    });

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                var addr = '';
                var extraAddr = '';

                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }

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
                    document.getElementById("addressInput").value = extraAddr;

                } else {
                    document.getElementById("addressInput").value = '';
                }

                try {
                    document.getElementById('zonecodeInput').value = data.zonecode;
                    zonecodeValid = true;
                } catch (error) {}
                try {
                    document.getElementById("addressInput").value = addr;
                    addressValid = true;
                } catch (error) {}
            }
        }).open();
    }