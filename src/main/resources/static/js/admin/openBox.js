document.addEventListener("DOMContentLoaded", function() {

    var admin_link = document.querySelectorAll("#openNewWindow");
    var admin_openSize = "width=950, height=700, top=100, left=150";

    var admin_link2 = document.querySelectorAll("#openNewWindow2");
    var admin_emailSize = "width=650, height=500, top=150, left=350";

    var admin_link3 = document.querySelector("#sendFullEmail");
    var admin_emailSize = "width=650, height=500, top=150, left=350";

    var admin_link4 = document.querySelector("#ExcelDownloads");
    var admin_emailSize = "width=650, height=500, top=150, left=350";

    var admin_link5 = document.querySelectorAll("#openNewWindow3");
    var admin_openSize2 = "width=950, height=700, top=100, left=150";

    var admin_link6 = document.querySelectorAll("#openNewWindow4");
    var admin_emailSize2 = "width=650, height=500, top=150, left=350";

    admin_link.forEach(l => {
        l.addEventListener("click", function (event) {
            event.preventDefault();

            window.open(l.href, "_blank", admin_openSize);
        });
    });
    admin_link2.forEach(l => {
        l.addEventListener("click", function (event) {
            event.preventDefault();

            window.open(l.href, "_blank", admin_emailSize);
        });
    });

    admin_link5.forEach(l => {
        l.addEventListener("click", function (event) {
            event.preventDefault();

            window.open(l.href, "_blank", admin_openSize2);
        });
    });
    admin_link6.forEach(l => {
        l.addEventListener("click", function (event) {
            event.preventDefault();

            window.open(l.href, "_blank", admin_emailSize2);
        });
    });
});