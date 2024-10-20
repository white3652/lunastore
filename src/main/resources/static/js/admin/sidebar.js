document.addEventListener('DOMContentLoaded', function() {
            const notReadyLinks = document.querySelectorAll('.not-ready');

            notReadyLinks.forEach(link => {
                link.addEventListener('click', function(event) {
                    event.preventDefault(); // 기본 동작 방지
                    alert('죄송합니다, 준비 중입니다.');
                });
            });
        });
document.addEventListener("DOMContentLoaded", function () {
    const btns = document.querySelectorAll(".admin_menu_toggle");

    btns.forEach((btn) => {
        btn.addEventListener("click", function () {
            let con = btn.nextElementSibling;
            if (con.classList.contains("active")) {
                con.classList.remove("active");
                btn.lastElementChild.classList.remove("arrow_small_bottom");
                btn.lastElementChild.classList.add("arrow_small_left");
            } else {
                con.classList.add("active");
                btn.lastElementChild.classList.add("arrow_small_bottom");
                btn.lastElementChild.classList.remove("arrow_small_left");
            }
        });
    });

    function closeAllDropdowns(exceptDropdown) {
        document.querySelectorAll(".sv").forEach((sv) => {
            if (sv !== exceptDropdown) {
                sv.style.display = "none";
            }
        });
    }
    document.addEventListener("click", function (event) {
        if (!event.target.matches(".sv_member") && !event.target.closest(".sv")) {
            closeAllDropdowns();
        }
    });

    document.querySelectorAll(".sv_member").forEach((member) => {
        member.addEventListener("click", function (event) {
            event.preventDefault();

            const dropdown = this.nextElementSibling;

            closeAllDropdowns(dropdown);

            dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
        });
    });
});