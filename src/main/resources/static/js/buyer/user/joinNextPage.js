$(function () {
    $('#nextPage').click(function () {
        $('#joinPage1').fadeOut(400, function () {
            $('#joinPage2').fadeIn(400);
        });
    });

    $('#lastPage').click(function () {
        $('#joinPage2').fadeOut(400, function () {
            $('#joinPage3').fadeIn(400);
        });
    });
});