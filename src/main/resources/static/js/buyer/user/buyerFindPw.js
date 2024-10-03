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

    $('#telInput').focus(function(){
        $('#telTitle').css("bottom", "50px")
        $('#telTitle').css("font-size", "14px")
    })

    $('#telInput').blur(function(){
        $('#telTitle').css("bottom", "28px")
        $('#telTitle').css("font-size", "20px")
        telvalue()
    })

    function telvalue(){
        if($('#telInput').val() !== ''){
            $('#telTitle').css("bottom", "50px")
            $('#telTitle').css("font-size", "14px")
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