$(function () {

    $('#orderByType').change(function () {
        $("#formBox").submit();
    });
    $('#viewNum').change(function () {
        $("#formBox").submit();
    });

    $('h4, h2').click(function () {

        $('h4, h2').not(this).removeClass('active');
        // 클릭된 요소에 active 클래스 추가
        $(this).addClass('active');

        var classValue = $(this).attr('class').split(' ')[0]; // 첫 번째 클래스만 추출
        var otherClasses = classValue.replace('active', '').trim();

        if (otherClasses !== '') {
            // 클릭된 요소가 h4인 경우
            if ($(this).is('h4')) {
                $('input[name="c_idx"]').val(otherClasses);
                $('input[name="p_idx"]').val('0');
            } else if ($(this).is('h2')) { // 클릭된 요소가 h2인 경우
                $('input[name="p_idx"]').val(otherClasses);
                $('input[name="c_idx"]').val('0');
            }
        }
        $("#formBox").submit();
    });

    var queryString = window.location.search;
    var searchParams = new URLSearchParams(queryString);
    var c_idxValue = searchParams.get('c_idx');
    var p_idxValue = searchParams.get('p_idx');
    if (c_idxValue > 0) {
        $('h4.' + c_idxValue).addClass('active');
        $('input[name="c_idx"]').val(c_idxValue);
        $('input[name="p_idx"]').val('0');
    } else if (p_idxValue > 0) {
        $('h2.' + p_idxValue).addClass('active');
        $('input[name="c_idx"]').val('0');
        $('input[name="p_idx"]').val(p_idxValue);
    } else {
        $('h2[class="0"]').addClass('active');
        $('input[name="c_idx"]').val('0');
        $('input[name="p_idx"]').val('0');
    }

    $(".m_list_categoris div").each(function(){
        if ($(this).find("h4.active").length > 0) {
            $(this).css("height", "225px");
        } else {
            $(this).css("height", "");
        }
    });

});