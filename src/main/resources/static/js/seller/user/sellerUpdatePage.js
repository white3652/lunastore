

function globaldisable(){
    if(exPwValid && newPwValid && ckeckPwValid){
        upPwSubmit.disabled = false
    }else{
        upPwSubmit.disabled = true
    }
}

let sellerTitleValid = false

$(function(){
    $('#sellerTitleInput').blur(function(){
        if(this.value.length !== 0){
            sellerTitleValid = true
        }else{
            sellerTitleValid = false
            $('#sellerTitleInput').css("border", "1px solid #F74848")
            $('#sellerTitleMsg').text("이름이 있어야 합니다.")
        }
        try {
            globaldisable()
        } catch (error) {}
    });

	$("#profileImgInput").on("input", function() {
    if ($("#profileImgInput").val() !== "") {
        $("#upSellerSubmit").prop("disabled", false);
        $("#ReturnSellerprofileSubmit").prop("disabled", true);
    } else {
        $("#upSellerSubmit").prop("disabled", true);
         $("#ReturnSellerprofileSubmit").prop("disabled", false);
    }
});


	$("#profileImgInput").on("input", function() {
    if ($("#profileImgInput").val() !== "") {
        $("#upSellerSubmit").prop("disabled", false);
    } else {
        $("#upSellerSubmit").prop("disabled", true);
    }
});

$('#returnBtn1').click(function(){
    $('profileImgInput').val("")
})
    
    $('#updatePwBtn').click(function(){
        $('#updatePwPage').fadeIn(400);
        $('#cancelPwBtn').fadeIn(0);
        $('#updatePwBtn').fadeOut(0);
    });

    $('#cancelPwBtn').click(function(){
        $('#updatePwPage').fadeOut(400);
        $('#cancelPwBtn').fadeOut(0);
        $('#updatePwBtn').fadeIn(0);
    });

    $('#updateAddBtn').click(function(){
        $('#updateAddPage').fadeIn(400);
        $('#cancelAddBtn').fadeIn(0);
        $('#updateAddBtn').fadeOut(0);
        execDaumPostcode()
    });

    $('#cancelAddBtn').click(function(){
        $('#updateAddPage').fadeOut(400);
        $('#cancelAddBtn').fadeOut(0);
        $('#updateAddBtn').fadeIn(0);
        $('#addressInput').val('');
        $('#zonecodeInput').val('');
    });
    
    $('#cancelOpenPage').click(function () {
        $('#cancelPwPage').fadeIn(400)
        $('#cancelOpenPage').css("cursor", "default")
        $('#cancelOrNotRegion').css("box-shadow", "0px 0px 10px 0px #F74848")
        $('#cancelOrNotRegion').css("border", "1px solid #F74848")
    });

    $('#firstReturnBtn').click(function () {
        $('#cancelPwPage').fadeOut(400)
        $('#cancelOpenPage').css("cursor", "pointer")
        $('#cancelOrNotRegion').css("box-shadow", "none")
        $('#cancelOrNotRegion').css("border", "none")
    })

    $('#firstNextBtn').click(function () {
        $('#cancelOpenPage').css("cursor", "default")
        $('#firstBtnRegion').fadeOut(400, function () {
            $('#cancelSubmitPage').fadeIn(400)
        })
    })

    $('#lastReturnBtn').click(function () {
        $('#cancelPwPage').fadeOut(400)
        $('#cancelSubmitPage').fadeOut(400, function () {
            $('#firstBtnRegion').fadeIn(400)
        })
        $('#cancelOpenPage').css("cursor", "pointer")
        $('#cancelOrNotRegion').css("box-shadow", "none")
        $('#cancelOrNotRegion').css("border", "none")
    })
    
    $('#existingPwInput').keyup(function () {
	if (this.value.length === 0) {
		$('#existingPwInput').css("border", "1px solid #F74848")
        $('#existingPwMsg').text("입력 되어 있지 않습니다.")
        duplicatePw = false
	}
	});
});