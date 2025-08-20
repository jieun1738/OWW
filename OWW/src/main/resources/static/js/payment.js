const payButtons = document.querySelectorAll('.payBtn');

	payButtons.forEach(btn => {
	    btn.addEventListener('click', () => {
			const user_email = "user1@example.com";/*토큰의 email값 받아서 넣어주기*/
	        const form = btn.closest('form'); // 해당 버튼이 속한 form
	        const itemName = form.querySelector('input[name="product_name"]').value;
	        const amount = form.querySelector('input[name="cost"]').value;
			const category = form.querySelector('input[name="category"]').value;

	        const IMP = window.IMP;
	        IMP.init('imp87271311');

	        const requestData = {
	            pg: 'kakaopay',
	            pay_method: 'card',
	            merchant_uid: `order_${new Date().getTime()}`,
				name: itemName,
	            amount: amount,
				buyer_email: user_email,
				category: category
	        };

	        IMP.request_pay(requestData, (res) => {
	            if(res.success) {
					alert(`${itemName} 결제 완료!`);
					savePaymentToDB(res).then(() => {
				    window.location.href = `/mypage`;
				});
			    
			}else alert(`결제 실패: ${res.error_msg}`);
	        });
			
			function savePaymentToDB(paymentInfo) {
			    return fetch('/payment/success', {
			        method: 'POST',
			        headers: { 'Content-Type': 'application/json' },
			        body: JSON.stringify({
			            orderId: paymentInfo.imp_uid,
			            amount: paymentInfo.paid_amount,
			            itemName: paymentInfo.item_name,
						user_email: paymentInfo.buyer_email,
						category: category
			        })
			    })
			    .then(res => res.json())
				.then(data => {
				      console.log("DB 저장 완료", data);
				      return data;
				  })
			    .catch(err => console.error("DB 저장 실패", err));
			}
	    });
	});