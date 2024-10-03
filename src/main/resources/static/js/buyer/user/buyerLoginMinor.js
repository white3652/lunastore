$(function () {
$('#emailArrowSmall').click(function () {
    if ($('#emailInput').val().trim() !== '') {
        $('#pwRegion').css("visibility","visible");
        $('#emailInput').css("borderRadius", "10px 10px 0px 0px");
    }
});

$('#emailInput').on('input', function () {
    if ($(this).val().trim() === '') {
    	$('#pwRegion').css("visibility","");
        $('#emailInput').css("borderRadius", "10px");
        $('#pwInput').val('');
    }
});

});

    $(function () {
        $('#emailInput, #pwInput').on('input', function () {
            var emailInputValue = $('#emailInput').val().trim();
            var pwInputValue = $('#pwInput').val().trim();

            if (emailInputValue !== '' && pwInputValue !== '') {
                $('#loginSubmit').prop('disabled', false);
            } else {
                $('#loginSubmit').prop('disabled', true);
            }
        });
    });