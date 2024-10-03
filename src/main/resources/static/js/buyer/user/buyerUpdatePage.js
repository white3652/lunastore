$(function () {
    $('#profileInput').change(setThumbnail);
    $('#Regionprofile').click(function(){
        $('#backBlur').fadeIn(400)
        $('#profileUpRegion').fadeIn(400)
    })

    $('#cancelBtn').click(function(){
        $('#backBlur').fadeOut(150)
        $('#profileUpRegion').fadeOut(150)
    })

    $('#nickNamebox').click(function(){
        $('#backBlur').fadeIn(150)
        $('#nickNameRegion').fadeIn(150)
    })

    $('#nickNamecancelBtn').click(function(){
        $('#backBlur').fadeOut(150)
        $('#nickNameRegion').fadeOut(150)
    })

    $('#nickNameInput').focus(function(){
        $('#nickNameInput').css("border", "1px solid #0071E3")
        $('#nickNameInput').css("boxShadow", "0px 0px 4px 0px #0071e3")
    })
    
    $('#nickNameInput').blur(function () {
		$('#nickNameInput').css("boxShadow", "")
		if($('#nickNameInput').val() === ""){
		        	$("#nickNamesubmitBtn").attr("disabled", true);
		            $('#nickNameInput').css("border", "1px solid #F74848")
		            $('#nickNameMsg').text("닉네임이 있어야 합니다.")
		        }
	})
});


function setThumbnail(event) {
    var reader = new FileReader();

    reader.onload = function (event) {
        var img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        $('#image_container').css('background-image', "url('" + event.target.result + "')");
        $('#profile1').hide();
    };

    reader.readAsDataURL(event.target.files[0]);
}