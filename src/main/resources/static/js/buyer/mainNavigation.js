function navigation() {
    const categoryAs = document.querySelectorAll('.global_nav_btns a');
    const categoryButtons = document.querySelectorAll('.global_nav_btns button');
    const allCategories = document.querySelectorAll('.global_nav_list');
    const navigation = document.querySelector('nav');
    const thisSection = document.querySelector('section');
    const globalNavCategories = document.querySelectorAll('.global_nav_categories');

    //#region -------- 스크롤 이벤트 --------------------------------------------------------------------------------

    navBar();
    window.addEventListener('scroll', navBar);

    function navBar() {
        const scrollY = window.scrollY;
        const boxTop = navigation.getBoundingClientRect().top;

        if (boxTop < 0) {
            navigation.classList.add('fixed');
        } else if (scrollY < (boxTop + 95)) {
            navigation.classList.remove('fixed');
        }
    }

    //#endregion -------- 스크롤 이벤트 -----------------------------------------------------------------------------

    //#region -------- 대분류 호버 이벤트 ---------------------------------------------------------------------------

    categoryAs.forEach((a, index) => {
        if (index < 5) {
            a.addEventListener('mouseover', () => {
                btnHover = setTimeout(() => {
                    allCategories.forEach(cg => {
                        cg.classList.remove('active');
                    });
                    const selectedCategory = allCategories[index];
                    selectedCategory.classList.add('active');
                    navigation.classList.add('active');
                    thisSection.classList.add('blur');
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
            selectedCategory.classList.add('active');
            navigation.classList.add('active');
            thisSection.classList.add('blur');
        });
    })


    navigation.addEventListener('mouseleave', () => {
        allCategories.forEach(cg => {
            cg.classList.remove('active');
            navigation.classList.remove('active');
            thisSection.classList.remove('blur');
        });
    });

    //#endregion -------- 대분류 호버 이벤트 ------------------------------------------------------------------------

    //#region -------- 중분류 호버 이벤트 ---------------------------------------------------------------------------

    globalNavCategories.forEach(gcg => {
        let categoriLi = gcg.querySelectorAll('ul li');
        let categoriImg = gcg.nextElementSibling.querySelectorAll('img');
        categoriImg.forEach(img => {
            if (!img.classList.contains('active')) {
                gcg.nextElementSibling.firstElementChild.classList.add('active');
            }
        })
        categoriLi.forEach((li, index) => {
            li.addEventListener('mouseover', () => {
                btnHover = setTimeout(() => {
                    categoriImg.forEach(img => {
                        img.classList.remove('active');
                    })
                    categoriImg[index].classList.add('active');
                }, 200);
            })
            li.addEventListener('mouseout', () => {
                clearTimeout(btnHover);
            });
        })
    })

    //#endregion -------- 중분류 호버 이벤트 ------------------------------------------------------------------------

    //#region -------- 실시간 검색어 슬라이드 이벤트 -----------------------------------------------------------------

    document.querySelector('.global_nav_keywords').innerHTML = document.querySelector('.global_nav_list_keywords').innerHTML;
    setInterval(() => {
        const activeItem = document.querySelector('.global_nav_keywords .active');
        if (activeItem) {
            activeItem.classList.remove('active');
            const nextItem = activeItem.nextElementSibling;
            if (!nextItem) {
                const firstItem = document.querySelector('.global_nav_keywords li:first-child');
                firstItem.classList.add('active');
            } else {
                nextItem.classList.add('active');
            }
        }
    }, 3000);

    //#endregion -------- 실시간 검색어 슬라이드 이벤트 --------------------------------------------------------------
};