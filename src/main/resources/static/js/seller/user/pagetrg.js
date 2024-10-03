$(function () {
    $('#nextPage').click(function () {
        $('#joinPage1').fadeOut(100, function () {
            $('#mainLog').css({
                "transition": "width 0.4s, height 0.4s",
                "width": "140px",
                "height": "140px"
            });
            setTimeout(function () {
                $('#joinPage2').fadeIn(400);
            }, 400);
        });
    });
});