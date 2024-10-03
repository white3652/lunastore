 // #region 카테고리 드랍다운
function categoryDropdown() {
    const sellerGlobalCategoryBtns = document.querySelectorAll(".seller_global_category_btn");
    const sellerGlobalDropdownWrap = document.querySelector(".seller_global_dropdown_wrap");
    let activeDropdown = null;

    sellerGlobalCategoryBtns.forEach((btn) => {
        btn.addEventListener("click", function () {
            const dropList = btn.nextElementSibling;

            if (dropList.classList.contains("active")) {
                dropList.classList.remove("active");
                btn.lastElementChild.classList.remove("active");
                activeDropdown = null;
            } else {
                dropList.classList.add("active");
                btn.lastElementChild.classList.add("active");
                activeDropdown = dropList;
            }
        });
    });
}