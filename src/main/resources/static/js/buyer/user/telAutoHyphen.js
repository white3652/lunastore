$(function () {
    $('#telInput').on('input', function () {
        autoHyphen(this);
    });

    function autoHyphen(target) {
        $(target).val(function (index, value) {
            return value.replace(/[^0-9]/g, '')
                .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
        });
    }
});