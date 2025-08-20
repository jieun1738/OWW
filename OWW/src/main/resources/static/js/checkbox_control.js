document.addEventListener('DOMContentLoaded', () => {
	const categories = ['hall', 'stud', 'dres', 'make'];

	categories.forEach(category => {
		const contractCheckbox = document.getElementById(`contract_${category}_ch`);
		const payCheckbox = document.getElementById(`pay_${category}`);
		const buttons = document.querySelectorAll(`.${category} .btn, .${category} .payBtn`);

		if (!payCheckbox || !contractCheckbox) return;

		// 체크 여부 확인 함수
		function toggleDisabled() {
			if (payCheckbox.checked) {
				buttons.forEach(btn => btn.disabled = true);
				contractCheckbox.disabled = true;
				payCheckbox.disabled = true; // 체크 해제 방지
			}
		}


		// 초기 로드 시 확인
		toggleDisabled();

		// 체크박스 클릭 이벤트 (체크 해제 방지용)
		payCheckbox.addEventListener('click', (e) => {
			if (payCheckbox.checked) {
				e.preventDefault(); // 체크 해제 방지
			}
		});
		contractCheckbox.addEventListener('click', (e) => {
			if (contractCheckbox.checked) {
				e.preventDefault(); // 체크 해제 방지
			}
		});
	});
	});
