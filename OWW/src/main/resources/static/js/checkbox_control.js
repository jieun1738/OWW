document.addEventListener('DOMContentLoaded', () => {
	const categories = ['hall', 'stud', 'dres', 'make'];

	categories.forEach(category => {
		const contractCheckbox = document.getElementById(`contract_${category}_ch`);
		const payCheckbox = document.getElementById(`pay_${category}`);
		const payAmount = document.getElementById(`payamount_${category}`);
		const buttons = document.querySelectorAll(`.${category} .btn, .${category} .payBtn`);
		const total_pay_btn = document.querySelectorAll(`.total_btn`);

		if (!payCheckbox || !contractCheckbox) return;

		// 체크 여부 확인 함수
		function toggleDisabled() {
			if (Number(payAmount.textContent)!= 0) {
				buttons.forEach(btn => btn.disabled = true);
				total_pay_btn.forEach(btn => btn.disabled = true);
				payCheckbox.checked = true;
				payCheckbox.disabled = true;
				contractCheckbox.disabled = true;
				contractCheckbox.checked = true;
				 // 체크 해제 방지
			}else {
			    buttons.forEach(btn => btn.disabled = false);
			    contractCheckbox.disabled = false;
				payCheckbox.disabled = false;
			}
		}


		// 초기 로드 시 확인
		toggleDisabled();

		// payCheckbox 상태 변경 시마다 적용
		//payCheckbox.addEventListener('change', toggleDisabled);
	});
	});
