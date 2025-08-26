document.addEventListener("DOMContentLoaded", function() {
	console.log("Change event triggered");
    const extraOption = document.getElementById("extraOption");
    const extraInput = document.getElementById("extraInputInput");

	    // 변수들이 null인지 확인
	    console.log("extraOption:", extraOption);
	    console.log("extraInput:", extraInput);
    extraOption.addEventListener("change", function() {
        if (this.value) {
            extraInput.style.display = "inline-block"; // input만 나타나게
        } else {
            extraInput.style.display = "none"; // input 숨기기
        }
    });
});