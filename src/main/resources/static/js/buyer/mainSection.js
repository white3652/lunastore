function mainSection() {
    // 메인 배너 관련 요소 선택
    const mainBanner = document.querySelector(".main_banner");
    const mainBannerImgCount = document.querySelector(".main_banner_img_count");
    const mainBannerImgs = document.querySelector('.main_banner_imgs');
    const mainBannerClass = ['prev_prev_perv', 'prev_prev', 'prev', 'view', 'next', 'next_next', 'next_next_next'];
    const mainBannerBtns = document.querySelectorAll(".main_banner_btns button");
    const mainImgCount = mainBannerImgs.childElementCount;
    let mainImgNum = 1;
    let intervalId;

    // 자동 슬라이드 시작
    resumeInterval();
    mainBanner.addEventListener("mouseover", pauseInterval);
    mainBanner.addEventListener("mouseout", resumeInterval);

    function pauseInterval() {
        clearInterval(intervalId);
    }

    function resumeInterval() {
        intervalId = setInterval(() => {
            handleImageChange(1);
        }, 5000);
    }

    // 배너 버튼 클릭 이벤트
    mainBannerBtns.forEach(mbb => {
        mbb.addEventListener("click", () => {
            if (mbb.disabled) return; // 버튼 비활성화 상태일 때 클릭 방지
            mbb.disabled = true;
            setTimeout(() => {
                mbb.disabled = false;
            }, 500);

            if (mbb.classList.contains("main_banner_prev")) {
                handleImageChange(-1);
            } else if (mbb.classList.contains("main_banner_next")) {
                handleImageChange(1);
            } else if (mbb.classList.contains("main_banner_next_next")) {
                handleImageChange(1);
                handleImageChange(1);
            }
        });
    });

    // 배너 이미지 변경 함수
    function handleImageChange(direction) {
        mainBannerClass.forEach(thisClass => {
            changeImage(thisClass, direction);
        });
        mainImgNum = (mainImgNum + direction - 1 + mainImgCount) % mainImgCount + 1;
        const bgPosition = `${100 - 5.26325 * (mainImgNum - 1)}% 50%`;
        mainBannerImgCount.style.backgroundPosition = bgPosition;
    }

    // 개별 배너 이미지 클래스 변경 함수
    function changeImage(thisClass, direction) {
        let thisImg = document.querySelector(`.${thisClass}`);
        thisImg.classList.remove(`${thisClass}`);

        let newImg;
        if (direction === 1) {
            newImg = thisImg.nextElementSibling || mainBannerImgs.firstElementChild;
        } else {
            newImg = thisImg.previousElementSibling || mainBannerImgs.lastElementChild;
        }

        newImg.classList.add(`${thisClass}`);
    }

    // -----------------------------------------------------------------------------------------------------------

    // 갤러리 관련 요소 선택
    const galleries = document.querySelectorAll('.gallery');

    // 스크롤 시 갤러리 활성화 처리
    function handleScroll() {
        const scrollPosition = window.scrollY;
        galleries.forEach(g => {
            const galleryPosition = g.offsetTop - window.innerHeight + 100;

            if (scrollPosition > galleryPosition) {
                g.classList.add('active');
            } else {
                g.classList.remove('active');
            }
        });
    }

    // 각 갤러리에 대해 캐러셀 설정
    galleries.forEach(g => {
        const galleryBtn = g.parentNode.querySelectorAll('.gallery_btns button');
        const galleryPrev = g.parentNode.querySelector('.prev');
        const galleryNext = g.parentNode.querySelector('.next');
        const galleryItems = g.querySelectorAll('.gallery_li');
        const galleryCnt = galleryItems.length;
        const gStyle = window.getComputedStyle(g);
        const gBoxStyle = window.getComputedStyle(g.parentNode);
        let gBoxWidth = parseInt(gBoxStyle.getPropertyValue("width"));
        let galleryNum = 0;

        // 간격 조절: 아이템 수에 따라 gap 설정
        if (galleryCnt <= 3) {
            g.style.justifyContent = 'flex-start';
            g.style.gap = '20px';
        } else {
            g.style.justifyContent = 'flex-start'; // space-between 제거
            g.style.gap = '40px';
        }

        // 갤러리 버튼 숨김/보임 함수 (무한 루프이므로 버튼 활성화 유지)
        function galleryBtnHide() {
            // 무한 루프이므로 버튼 숨김 필요 없음
            // 추가적인 조건이 필요하면 여기에 작성
        }

        // 리사이즈 이벤트 처리
        window.addEventListener('resize', function () {
            gBoxWidth = parseInt(gBoxStyle.getPropertyValue("width"));
            galleryNum = 0;
            g.style.transform = `translateX(-${galleryNum * 400}px)`;
        });

        // 버튼 클릭 이벤트 (중복 클릭 방지)
        galleryBtn.forEach(btn => {
            btn.addEventListener('click', () => {
                if (btn.disabled) return;
                btn.disabled = true;
                setTimeout(() => {
                    btn.disabled = false;
                }, 500);
            });
        });

        // 이전 버튼 클릭 시
        galleryPrev.addEventListener('click', () => {
            if (galleryNum > 0) {
                galleryNum--;
            } else {
                // 첫 슬라이드에서 마지막 슬라이드로 이동 (무한 루프)
                galleryNum = galleryCnt - Math.floor(gBoxWidth / 400);
                if (galleryNum < 0) galleryNum = 0;
            }
            g.style.transform = `translateX(-${galleryNum * 400}px)`;
            galleryBtnHide();
        });

        // 다음 버튼 클릭 시
        galleryNext.addEventListener('click', () => {
            if ((galleryNum + Math.floor(gBoxWidth / 400)) < galleryCnt) {
                galleryNum++;
            } else {
                // 마지막 슬라이드에서 첫 슬라이드로 이동 (무한 루프)
                galleryNum = 0;
            }
            g.style.transform = `translateX(-${galleryNum * 400}px)`;
            galleryBtnHide();
        });

        // 갤러리 마우스 오버 시 버튼 숨김 처리 (필요시 구현)
        g.addEventListener('mouseover', galleryBtnHide);
    });

    // 스크롤 이벤트 리스너 등록
    window.addEventListener('scroll', handleScroll);
}
