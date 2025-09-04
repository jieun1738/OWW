let savingChart;

function initChart(labels, data) {
  console.log("initChart() 실행됨 ✅", labels, data);
  renderChart(labels, data);
}

function renderChart(labels, data) {
	console.log("savingChart.js 실행됨 ✅");
      
	const ctx = document.getElementById('savingChart').getContext('2d');
      
	if (savingChart) savingChart.destroy();

	  const maxY = Math.max(...data, 20000) * 1.1; // 10% 여유
	  const minY = 0;
	 	  
      savingChart = new Chart(ctx, {
        type: 'line',
		  data: {
			  labels,
			  datasets: [{
				  label: '저축금액',
				  data,
				  borderColor: 'purple',
				  pointRadius: 3,
				  tension: 0
			  }, {
				  label: '목표 기준선',
				  data: Array(labels.length).fill(10000), // Y축 10000 고정
				  borderColor: 'lightpink',    // 기준선 색상
				  borderDash: [],
				  pointRadius: 0,
				  fill: false,
				  tension: 0
			  }
			  ]
		  },
		  options: {
			  responsive: true,
			  maintainAspectRatio: false,
			  plugins: { legend: { display: true } },
			  scales: {
				  x: { grid: { display: false } },
				  y: { min: minY, max: maxY }
			  }
		  }
	  });
}
	

    function toDaily() {
		console.log("toDaily() 호출됨 ✅");
	 
	 // 선택한 월 가져오기
	     const selectedMonth = parseInt(document.getElementById("month").value, 10);

	     // 선택한 월에 해당하는 데이터만 필터링
	     const filtered = window.historyList.filter(h => {
	         const month = new Date(h.payment_date).getMonth() + 1; // 0~11이므로 +1
	         return month === selectedMonth;
	     });

	     // X축: 일자만
	     const labels = filtered.map(h => new Date(h.payment_date).getDate()+"일");
	     const data = filtered.map(h => h.amount);

	     renderChart(labels, data);
	     updateSuccessBar(data);

	     // 탭 active 설정
	     document.getElementById('tab-daily').classList.add('active');
	     document.getElementById('tab-monthly').classList.remove('active');
 
    }
    function toMonthly() {
      document.getElementById('tab-monthly').classList.add('active');
      document.getElementById('tab-daily').classList.remove('active');
      renderChart(Array.from({length: 12}, (_,i)=>`${i+1}월`), sampleMonthly);
      updateSuccessBar(sampleMonthly);
    }

    function updateSuccessBar(arr){
      const unit = Math.max(1, Math.round(targetAmount / (arr.length || 1)));
      const exceed = arr.filter(v => v >= unit).length;
      const ratio = Math.min(100, Math.round(exceed / arr.length * 100));
      const bar = document.getElementById('successBar');
      bar.style.width = ratio + '%';
      bar.textContent = ratio + '%';
    }

	// 초기 렌더링: 오늘 월 기준
	document.addEventListener("DOMContentLoaded", () => {
	    const today = new Date();
	    const currentMonth = today.getMonth() + 1;

	    // month select 초기값 설정
	    document.getElementById("month").value = currentMonth;
		
		console.log("historyList:", window.historyList);
	    // 오늘 월 데이터 필터링
	    const filtered = window.historyList.filter(h => {
	        const month = new Date(h.payment_date).getMonth() + 1;
	        return month === currentMonth;
	    });
	    const labels = filtered.map(h => new Date(h.payment_date).getDate() + "일");
	    const data = filtered.map(h => h.amount);

	    initChart(labels, data);
	
	document.getElementById("month").addEventListener("change", toDaily);
	document.getElementById('tab-daily').addEventListener('click', toDaily);
	document.getElementById('tab-monthly').addEventListener('click', toMonthly);
});