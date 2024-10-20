window.onload = function() {
    try{
    document.getElementById("Cancel_btn").addEventListener("click", function() {
        var confirmResult = confirm("회원 탈퇴를 진행하시겠습니까?");
        if(confirmResult) {
            var form = document.createElement("form");
            form.method = "POST";
            form.action = '/admin/buyers/' + b_idx + '/cancel'
            document.body.appendChild(form);
            form.submit();
        }
    });
    }catch(err){}
    
    try{
    document.getElementById("Cancel_btn2").addEventListener("click", function() {
        var confirmResult = confirm("회원 탈퇴를 진행하시겠습니까?");
        if(confirmResult) {
            var form = document.createElement("form");
            form.method = "POST";
            form.action = "cancelS.do";
            document.body.appendChild(form);
            form.submit();
        }
    });
    }catch(err){}
}


$(function() {
			// 닫기 버튼 클릭 이벤트
			$(".admin_close_btn2").click(function() {
				window.close(); // 현재 창을 닫음
			});
		});
		
		$(document).ready(function() {
			// 닫기 버튼 클릭 이벤트
			$(".admin_email_btn_close").click(function() {
				window.close(); // 현재 창을 닫음
			});
			
			$('#addressBtn').click(function(){
				execDaumPostcode()
				consol.log("클릭")
			})
		
});
		
		
		
		