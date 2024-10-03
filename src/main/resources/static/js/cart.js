const urlParams = new URLSearchParams(window.location.search);
const b_idx = urlParams.get('b_idx');
function checkLoginAndRedirect() {
    if (typeof window.isLoggedIn === 'undefined') {
        console.error('Login status is not available');
        return;
    }

    if (!window.isLoggedIn) {
        alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
        window.location.href = "/buyer/login";
    } else {
        fetch('/cart/cartList', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ b_idx: parseInt(b_idx) })
        })
            .then(response => response.json())
            .then(data => {
                window.location.href = "/cart/cart";
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}
document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/check-login')
        .then(response => response.json())
        .then(data => {
            window.isLoggedIn = data.isLoggedIn;
        })
        .catch(error => {
            console.error('Error:', error);
        });
});