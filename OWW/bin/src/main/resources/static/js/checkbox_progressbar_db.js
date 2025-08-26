function updateContract(checkbox) {
    const checkboxId = checkbox.id;
	const baseName = checkboxId.replace(/_ch$/, '');
    const checked = checkbox.checked;

    fetch('/mypage/updateContract', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ baseName: baseName, checked: checked })
    })
    .then(response => response.json())
    .then(data => {
        if(data.success){
			const progressBar = document.getElementById('progress-bar');
            progressBar.style.width = data.progress + '%';
            progressBar.innerText = data.progress + '%';
        } else {
            alert('저장 실패: ' + data.error);
            checkbox.checked = !checked;
        }
    })
    .catch(err => {
        console.error(err);
        checkbox.checked = !checked;
    });
}
