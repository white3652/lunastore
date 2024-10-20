document.addEventListener("DOMContentLoaded", function() {

    var admin_link3 = document.querySelector("#sendFullEmail");
    var admin_emailSize = "width=650, height=500, top=150, left=350";
    
    var admin_link4 = document.querySelector("#ExcelDownloads");
    var admin_emailSize = "width=650, height=500, top=150, left=350";
    
    admin_link4.addEventListener("click", function (event) {
        event.preventDefault();
        window.open(admin_link4.href, "_blank", admin_emailSize);
    });
    
    admin_link3.addEventListener("click", function (event) {
        event.preventDefault();
        window.open(admin_link3.href, "_blank", admin_emailSize);
    });
    
    
});