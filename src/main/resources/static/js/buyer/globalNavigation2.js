function navigation() {
    const categoryAs = document.querySelectorAll('.global_nav_btns a');
    const categoryButtons = document.querySelectorAll('.global_nav_btns button');
    const allCategories = document.querySelectorAll('.global_nav_list');
    const navigation = document.querySelector('nav');
    const thisSection = document.querySelector('section');
    const globalNavCategories = document.querySelectorAll('.global_nav_categories');
    let btnHover; // btnHover 변수를 함수 스코프 내에 선언

    //#region -------- 대분류 호버 이벤트 ---------------------------------------------------------------------------

    categoryAs.forEach((a, index) => {
        if (index < 5) {
            a.addEventListener('mouseover', () => {
                btnHover = setTimeout(() => {
                    allCategories.forEach(cg => {
                        cg.classList.remove('active');
                    });
                    const selectedCategory = allCategories[index];
                    if (selectedCategory) {
                        selectedCategory.classList.add('active');
                        if (navigation && thisSection) {
                            navigation.classList.add('active');
                            thisSection.classList.add('blur');
                        }
                    }
                }, 200);
            });
            a.addEventListener('mouseout', () => {
                clearTimeout(btnHover);
            });
        }
    });

    categoryButtons.forEach((btn, index) => {
        btn.addEventListener('click', () => {
            allCategories.forEach(cg => {
                cg.classList.remove('active');
            });
            const selectedCategory = allCategories[index + 5];
            if (selectedCategory) {
                selectedCategory.classList.add('active');
                if (navigation && thisSection) {
                    navigation.classList.add('active');
                    thisSection.classList.add('blur');
                }
            }
        });
    });

    if (navigation && thisSection) {
        navigation.addEventListener('mouseleave', () => {
            allCategories.forEach(cg => {
                cg.classList.remove('active');
                navigation.classList.remove('active');
                thisSection.classList.remove('blur');
            });
        });
    } else {
        console.warn('Navigation or Section element not found.');
    }

    //#endregion -------- 대분류 호버 이벤트 ------------------------------------------------------------------------

    //#region -------- 중분류 호버 이벤트 ---------------------------------------------------------------------------

    globalNavCategories.forEach(gcg => {
        let categoriLi = gcg.querySelectorAll('ul li');
        let nextSibling = gcg.nextElementSibling;
        if (!nextSibling) {
            console.warn('Next sibling for .global_nav_categories not found.');
            return;
        }
        let categoriImg = nextSibling.querySelectorAll('img');
        if (categoriImg.length === 0) {
            console.warn('No images found in next sibling of .global_nav_categories.');
            return;
        }
        categoriImg.forEach(img => {
            if (!img.classList.contains('active')) {
                nextSibling.firstElementChild.classList.add('active');
            }
        });
        categoriLi.forEach((li, index) => {
            li.addEventListener('mouseover', () => {
                btnHover = setTimeout(() => {
                    categoriImg.forEach(img => {
                        img.classList.remove('active');
                    });
                    if (categoriImg[index]) {
                        categoriImg[index].classList.add('active');
                    }
                }, 200);
            });
            li.addEventListener('mouseout', () => {
                clearTimeout(btnHover);
            });
        });
    });

    //#endregion -------- 중분류 호버 이벤트 ------------------------------------------------------------------------

    //#region -------- 실시간 검색어 슬라이드 이벤트 -----------------------------------------------------------------

    const keywordsContainer = document.querySelector('.global_nav_keywords');
    const keywordsList = document.querySelector('.global_nav_list_keywords');
    if (keywordsContainer && keywordsList) {
        keywordsContainer.innerHTML = keywordsList.innerHTML;
        setInterval(() => {
            const activeItem = document.querySelector('.global_nav_keywords .active');
            if (activeItem) {
                activeItem.classList.remove('active');
                const nextItem = activeItem.nextElementSibling;
                if (!nextItem) {
                    const firstItem = document.querySelector('.global_nav_keywords li:first-child');
                    if (firstItem) {
                        firstItem.classList.add('active');
                    }
                } else {
                    nextItem.classList.add('active');
                }
            }
        }, 3000);
    } else {
        console.warn('Keywords container or list not found.');
    }

    //#endregion -------- 실시간 검색어 슬라이드 이벤트 --------------------------------------------------------------
}

$(function () {
    navigation();
});
