async function barGraph() {
    const sellerMainContentsGraphBox = document.querySelector(".seller_main_contents_graph_box");
    const sellerMainContentsGraphTitle = document.querySelector(".seller_main_contents_graph_title > div");
    const sellerMainContentsGraphDate = document.querySelector(".seller_main_contents_graph_date");

    // 서버로부터 매출액 데이터 가져오기
    let data = [];
    try {
        const response = await fetch('/api/dailySalesData');
        if (!response.ok) {
            if (response.status === 401) {
                // 로그인 필요
                alert(window.messages.loginRequired);
                window.location.href = '/seller/login';
            } else {
                throw new Error(window.messages.dataFetchError);
            }
        }
        data = await response.json();
        console.log('Fetched sales data:', data);
    } catch (error) {
        console.error(window.messages.salesDataError, error);
        return;
    }

    // 날짜별 매출액을 매핑하여 그래프에 필요한 데이터 생성
    const salesData = [];
    const dateLabels = [];

    // 오늘 날짜 기준으로 지난 20일간의 날짜 생성
    const today = new Date();
    for (let i = 20; i >= 0; i--) {
        const date = new Date(today);
        date.setDate(today.getDate() - i);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const dateString = `${year}-${month}-${day}`; // 'YYYY-MM-DD' 형식

        dateLabels.push(i === 0 ? window.messages.todayTexts : String(date.getDate()));

        // 해당 날짜의 매출액 찾기
        const sales = data.find(d => d.date === dateString);
        salesData.push(sales ? sales.salesAmount : 0);
    }
    console.log('Date Labels:', dateLabels); // 로그 추가
    console.log('Sales Data:', salesData); // 로그 추가
    // 그래프 그리기
    const maxData = Math.max(...salesData, 1); // maxData가 0이 되지 않도록 1 추가
    sellerMainContentsGraphBox.innerHTML = ''; // 기존 그래프 초기화

    salesData.forEach((value, index) => {
        const bar = document.createElement('div');
        const heightPercentage = (value / maxData) * 100;

        bar.style.height = `${heightPercentage}%`;
        bar.classList.add('bar');
        bar.setAttribute('name', `${window.messages.currencySymbol} ${value.toLocaleString()}`);

        sellerMainContentsGraphBox.appendChild(bar);
    });

    // 그래프 아래 날짜 표시
    sellerMainContentsGraphDate.innerHTML = ''; // 기존 날짜 초기화
    dateLabels.forEach(date => {
        const dateElement = document.createElement('span');
        dateElement.textContent = date;
        sellerMainContentsGraphDate.appendChild(dateElement);
    });

    // 오늘 매출액 표시 업데이트
    const todaySales = salesData[salesData.length - 1];
    const formattedTodaySales = todaySales.toLocaleString();
    const todaySalesElement = sellerMainContentsGraphTitle.querySelector('span:nth-child(2)');
    if (todaySalesElement) {
        todaySalesElement.textContent = `${window.messages.currencySymbol} ${formattedTodaySales}`;
    }
}

// 페이지 로드 시 그래프 그리기 함수 호출
document.addEventListener('DOMContentLoaded', () => {
    barGraph();
    updateTimestamp();
});

function updateTimestamp() {
    const sellerMainContentsTime = document.querySelector(".seller_main_contents_time > span");

    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = String(currentDate.getMonth() + 1).padStart(2, "0");
    const day = String(currentDate.getDate()).padStart(2, "0");
    const hours = String(currentDate.getHours()).padStart(2, "0");
    const minutes = String(currentDate.getMinutes()).padStart(2, "0");

    const formattedTimestamp = `${year}-${month}-${day} ${hours}:${minutes} ${window.messages.timestampBasis}`;

    if (sellerMainContentsTime) {
        sellerMainContentsTime.textContent = formattedTimestamp;
    }
}