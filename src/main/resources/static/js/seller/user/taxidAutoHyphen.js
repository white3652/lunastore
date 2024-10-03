$(function () {
    $('#taxidInput').on('input', function () {
        autoHyphen(this);
    });

    function autoHyphen(target) {
        $(target).val(function (index, value) {
            return value.replace(/[^0-9]/g, '')
                        .replace(/^(\d{3})(\d{2})(\d{5})$/, '$1-$2-$3');
        });
    }
});