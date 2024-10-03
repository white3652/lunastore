$(function(){
    $('#nextBtn').click(function(){
        $('#findPage1').fadeOut(400, function() {
            $('#findPage2').fadeIn(400);
        });
    });

    $('#returnBtn').click(function(){
        $('#findPage2').fadeOut(400, function(){
            $.get("/reloadPage", function() {
            $('#findPage1').fadeIn(400);
        });
      });
    })

    $('#taxidInput').focus(function(){
        $('#taxidTitle').css("bottom", "50px")
        $('#taxidTitle').css("font-size", "14px")
    })

    $('#taxidInput').blur(function(){
        $('#taxidTitle').css("bottom", "28px")
        $('#taxidTitle').css("font-size", "20px")
        taxidvalue()
    })

    function taxidvalue(){
        if($('#taxidInput').val() !== ''){
            $('#taxidTitle').css("bottom", "50px")
            $('#taxidTitle').css("font-size", "14px")
        }
    }

    $('#emailInput').focus(function(){
        $('#emailTitle').css("bottom", "50px")
        $('#emailTitle').css("font-size", "14px")
    })

    $('#emailInput').blur(function(){
        $('#emailTitle').css("bottom", "28px")
        $('#emailTitle').css("font-size", "20px")
        emailvalue()
    })

    function emailvalue(){
        if($('#emailInput').val() !== ''){
            $('#emailTitle').css("bottom", "50px")
            $('#emailTitle').css("font-size", "14px")
        }
    }
});