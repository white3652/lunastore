$(function () {
    $('#emailInput').blur(function () {
        $('#emailArrowSmall').css("top", "")
        $('#emailArrowSmall').css("filter",
            "invert(61%) sepia(0%) saturate(1%) hue-rotate(158deg) brightness(85%) contrast(87%)"
            )
        updateIdStyle()
    });
    let emailArrowSmall = document.getElementById('emailArrowSmall')

    function updateIdStyle() {
        if (emailInput.value.trim() !== '') {
            emailArrowSmall.style.top = '30px';
            emailArrowSmall.style.filter =
                'invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)';
        }
    }

    $('#emailInput').focus(function () {
        $('#emailArrowSmall').css("top", "30px")
    });

    $('#emailInput').on('input', function () {
        if ($(this).val().trim() !== '') {
            $('#emailArrowSmall').css("filter",
                "invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)"
                );
        } else {
            $('#emailArrowSmall').css("filter", "invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)");
        }
    });

    $('#pwInput').blur(function () {
        $('#pwArrowSmall').css("top", "")
        $('#pwArrowSmall').css("filter",
            "invert(61%) sepia(0%) saturate(1%) hue-rotate(158deg) brightness(85%) contrast(87%)"
            )
        updateCodeStyle()
    });
    let pwArrowSmall = document.getElementById('pwArrowSmall')

    function updateCodeStyle() {
        if (pwInput.value.trim() !== '') {
            pwArrowSmall.style.top = '30px';
            pwArrowSmall.style.filter =
                'invert(28%) sepia(98%) saturate(1518%) hue-rotate(194deg) brightness(94%) contrast(106%)';
        }
    }

    $('#pwInput').focus(function () {
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