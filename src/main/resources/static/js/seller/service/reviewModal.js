// reviewModal.js

document.addEventListener('DOMContentLoaded', function () {
    // 모달 관련 요소 선택
    const modal = document.getElementById('reviewModal');
    const closeButton = modal ? modal.querySelector('.close-button') : null;
    const reviewDetails = modal ? modal.querySelector('#reviewDetails') : null;
    const loadingSpinner = modal ? modal.querySelector('#loadingSpinner') : null;

    // 리뷰 상세 링크 클릭 이벤트
    const reviewDetailLinks = document.querySelectorAll('.review-detail-link');
    reviewDetailLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 링크 동작 방지
            const reviewId = this.getAttribute('data-review-id');
            if (reviewId) {
                fetchReviewDetails(reviewId);
            }
        });
    });

    // 모달 닫기 버튼 클릭 이벤트
    if (closeButton) {
        closeButton.addEventListener('click', function () {
            closeModal();
        });
    }

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function (event) {
        if (modal && event.target === modal) {
            closeModal();
        }
    });

    // 리뷰 상세 정보 AJAX 요청 함수
    function fetchReviewDetails(reviewId) {
        if (reviewDetails && loadingSpinner) {
            loadingSpinner.style.display = 'flex';
            reviewDetails.innerHTML = ''; // 기존 내용 초기화
        }

        fetch(`/api/details?reviewId=${reviewId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답에 문제가 있습니다.');
                }
                return response.json();
            })
            .then(data => {
                populateModal(data);
                openModal();
            })
            .catch(error => {
                console.error('리뷰 상세 정보를 가져오는 데 실패했습니다:', error);
                if (reviewDetails) {
                    reviewDetails.innerHTML = '<p>리뷰 상세 정보를 불러오는 데 실패했습니다.</p>';
                }
                openModal();
            })
            .finally(() => {
                if (loadingSpinner) {
                    loadingSpinner.style.display = 'none';
                }
            });
    }

    // 모달 열기 함수
    function openModal() {
        if (modal) {
            modal.classList.remove('hide');
            modal.classList.add('show');
            modal.style.display = 'flex';
            modal.setAttribute('tabindex', '-1');
            modal.focus();
        }
    }

    // 모달 닫기 함수
    function closeModal() {
        if (modal) {
            modal.classList.remove('show');
            modal.classList.add('hide');
            setTimeout(() => {
                modal.style.display = 'none';
                if (reviewDetails) {
                    reviewDetails.innerHTML = ''; // 내용 초기화
                }
            }, 300); // 애니메이션 지속 시간과 동일하게 설정
        }
    }

    // 모달에 데이터 채우기 함수
    function populateModal(data) {
        if (!reviewDetails) return;
        const starWidth = data.reviewStar ? (data.reviewStar * 20) + '%' : '0%';
        let htmlContent = `
            <div class="review-author">
                <img src="${data.buyerProfileUrl}" alt="프로필 이미지">
                <div>
                    <div class="nickname">${data.buyerNickname}</div>
                    <div class="date">${data.reviewPostDate}</div>
                </div>
            </div>
<div class="review-star">
            <span class="m_l_view_star empty"></span>
            <span class="m_l_view_star full" style="width: ${starWidth};"></span>
            <span>${data.reviewStar}점</span>
        </div>
            <div class="review-content">
                ${data.reviewContent}
            </div>


                
        `;


        reviewDetails.innerHTML = htmlContent;
    }
});
