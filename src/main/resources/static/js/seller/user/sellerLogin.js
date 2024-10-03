$(function () {
    $('#taxidInput').blur(function () {
        $('#taxidInput').css("border", "1px solid #858585")
        $('#taxidInput').css("boxShadow", "none")
        $('#emailArrowSmall').css("top", "")
        $('#emailArrowSmall').css("filter",
            "invert(61%) sepia(0%) saturate(1%) hue-rotate(158deg) brightness(85%) contrast(87%)"
            )
        updateIdStyle()
    });
    let taxidInput = document.getElementById('taxidInput')
    let emailArrowSmall = document.getElementById('emailArrowSmall')

    function updateIdStyle() {
        if (taxidInput.value.trim() !== '') {
            emailArrowSmall.style.top = '30px';
            emailArrowSmall.style.filter =
                'invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)';
        }
    }

    $('#taxidInput').focus(function () {
        $('#taxidInput').css("border", "1px solid #0071E3'")
        $('#taxidInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
        $('#emailArrowSmall').css("top", "30px")
    });

    $('#taxidInput').on('input', function () {
        if ($(this).val().trim() !== '') {
            $('#emailArrowSmall').css("filter",
                "invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)"
                );
        } else {
            $('#emailArrowSmall').css("filter", "invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)");
        }
    });

    $('#pwInput').blur(function () {
        $('#pwInput').css("border", "1px solid #858585")
        $('#pwInput').css("boxShadow", "none")
        $('#pwArrowSmall').css("top", "")
        $('#pwArrowSmall').css("filter",
            "invert(61%) sepia(0%) saturate(1%) hue-rotate(158deg) brightness(85%) contrast(87%)"
            )
        updateCodeStyle()
    });
    let pwInput = document.getElementById('pwInput')
    let pwArrowSmall = document.getElementById('pwArrowSmall')

    function updateCodeStyle() {
        if (pwInput.value.trim() !== '') {
            pwArrowSmall.style.top = '30px';
            pwArrowSmall.style.filter =
                'invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)';
        }
    }

    $('#pwInput').focus(function () {
        $('#pwInput').css("border", "1px solid #0071E3'")
        $('#pwInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
        $('#pwArrowSmall').css("top", "30px")
    });

    $('#pwInput').on('input', function () {
        if ($(this).val().trim() !== '') {
            $('#pwArrowSmall').css("filter",
                "invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)"
                );
        } else {
            $('#pwArrowSmall').css("filter", "invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)");
        }
    });
});