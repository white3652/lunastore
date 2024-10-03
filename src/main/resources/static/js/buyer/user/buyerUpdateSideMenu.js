	$(function() {
	//페이지 마다 구분할 수 있는 색상 구현 필요
		$('#page').click(function() {
			$('#page').addClass('select');
			$('#page1').removeClass('select');
			$('#page2').removeClass('select');
			$('#page3').removeClass('select');
		});

		$('#page1').click(function() {
			$('#page').removeClass('select');
			$('#page1').addClass('select');
			$('#page2').removeClass('select');
			$('#page3').removeClass('select');
		});

		$('#page2').click(function() {
			$('#page').removeClass('select');
			$('#page1').removeClass('select');
			$('#page2').addClass('select');
			$('#page3').removeClass('select');
		});

		$('#page3').click(function() {
			$('#page').removeClass('select');
			$('#page1').removeClass('select');
			$('#page2').removeClass('select');
			$('#page3').addClass('select');
		});


		//계정 삭제에 대한 자스 구현 완료
		$('#cancelOpenBtn').click(function() {
			$('#cancelBlur').fadeIn(400);
			$('#cancelRegion').fadeIn(400);
		})

		$('#cancelBlur').click(function() {
			$('#cancelBlur').fadeOut(400);
			$('#cancelRegion').fadeOut(400);
		})

		$('#exPwInput').focus(function() {
			$('#exPwTitle').css({
				"font-size" : "12px",
				"bottom" : "40px"
			})
		})

		$('#exPwInput').blur(function() {
			$('#exPwTitle').css({
				"font-size" : "16px",
				"bottom" : "20px"
			})
			passwordInput();
		})

		function passwordInput() {
			if ($('#exPwInput').val() !== '') {
				$('#exPwTitle').css({
					"font-size" : "12px",
					"bottom" : "40px"
				})
				$('#exPwMsg').text('')
				$('#nextCancelBtn').prop('disabled', false);
			}else{
				$('#exPwMsg').text('암호를 입력해 주세요.')
				$('#nextCancelBtn').prop('disabled', true);
			}
		}
		
		$('#nextCancelBtn').click(function(){
			$('#cancelPage1').fadeOut(400);
			$('#cancelPage2').fadeIn(400);
			$('#cancelOkBtn').prop('disabled', false);
		})
	})