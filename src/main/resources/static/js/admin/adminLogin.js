$(function () {
    var idInput = $('#idInput');
    var idRegion = $('#idRegion');
    var idTitle = $('#idTitle');
    var idArrowSmall = $('#idArrowSmall');
    var codeRegion = $('#codeRegion');
    var codeInput = $('#codeInput');
    var codeTitle = $('#codeTitle');
    var loginForm = $('form');
    var adminIDSmg = $('#adminIDSmg');
    var codeArrowSmall = $('#codeArrowSmall');
    if (idInput.val().trim() !== '') {
        idTitle.css({"bottom": "34px", "fontSize": "10px"});
    }
    if (adminIDSmg.length > 0 && adminIDSmg.text().trim() !== '') {
        codeRegion.show(); // 비밀번호 필드 표시
        idRegion.css("borderRadius", "10px 10px 0px 0px");
        idTitle.css({"bottom": "34px", "fontSize": "10px"});
    }

    idInput.focus(function () {
        idRegion.css({"border": "1px solid #0071E3", "boxShadow": "0px 0px 4px 0px #0071e3"});
        idTitle.css({"bottom": "34px", "fontSize": "10px"});
    }).blur(function () {
        idRegion.css({"border": "1px solid #ffffff", "boxShadow": "none"});
        if (idInput.val().trim() !== '') {
            idTitle.css({"bottom": "34px", "fontSize": "10px"});
        } else {
            idTitle.css({"bottom": "20px", "fontSize": "16px"});
        }
    });

    idArrowSmall.click(function (event) {
        event.stopPropagation();
        console.log('idArrowSmall clicked');
        if (idInput.val().trim() !== '') {
            codeRegion.slideDown();
            idRegion.css("borderRadius", "10px 10px 0px 0px");
            codeInput.focus();
        }
    });

    codeArrowSmall.click(function (event) {
        event.stopPropagation();
        console.log('codeArrowSmall clicked');
        var a_id = idInput.val().trim();
        var a_pw = codeInput.val().trim();

        if (a_id !== '' && a_pw !== '') {
            loginForm.submit();
        } else {
            if (a_id === '') {
                idInput.focus();
            } else if (a_pw === '') {
                codeInput.focus();
            }
        }
    });

    idInput.on('input', function () {
        if (idInput.val().trim() === '') {
            codeRegion.slideUp();
            idRegion.css("borderRadius", "10px");
            codeInput.val('');
            codeTitle.css({"bottom": "20px", "fontSize": "16px"});
        }
    });

    codeInput.focus(function () {
        codeRegion.css({"border": "1px solid #0071E3", "boxShadow": "0px 0px 4px 0px #0071e3"});
        codeTitle.css({"bottom": "34px", "fontSize": "10px"});
    }).blur(function () {
        codeRegion.css({"border": "1px solid #ffffff", "boxShadow": "none"});
        if (codeInput.val().trim() !== '') {
            codeTitle.css({"bottom": "34px", "fontSize": "10px"});
        } else {
            codeTitle.css({"bottom": "20px", "fontSize": "16px"});
        }
    });

    idInput.add(codeInput).on('input', function () {
        var a_id = idInput.val().trim();
        var a_pw = codeInput.val().trim();

        if (a_id !== '' && a_pw !== '') {
            codeArrowSmall.addClass('enabled');
        } else {
            codeArrowSmall.removeClass('enabled');
        }
    });

    codeArrowSmall.on('keypress', function(e) {
        if (e.which === 13) {
            $(this).click();
        }
    });
});
