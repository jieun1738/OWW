document.addEventListener("DOMContentLoaded", function () {
  const tableBody = document.querySelector(".budget-table tbody");

  // 이벤트 위임 방식으로 처리 (추가된 행도 자동 적용)
  tableBody.addEventListener("change", function (e) {
    if (!e.target.matches(".extraOption")) return; // select가 아니면 무시

    const row = e.target.closest("tr.extraRow");
    const input = row.querySelector(".extraInput");

    if (e.target.value) {
      // 선택하면 input 표시
      input.style.display = "inline-block";

      // 마지막 extraRow라면 새로운 행 추가
      const extraRows = tableBody.querySelectorAll("tr.extraRow");
      const lastExtraRow = extraRows[extraRows.length - 1];

      if (row === lastExtraRow) {
        const newRow = row.cloneNode(true);

        // 초기화
        const newSelect = newRow.querySelector(".extraOption");
        const newInput = newRow.querySelector(".extraInput");

        newSelect.value = "";
        newInput.value = "";
        newInput.style.display = "none";

        // 총 예산 행 위에 삽입
        const totalRow = tableBody.querySelector("#totalRow");
        totalRow.parentNode.insertBefore(newRow, totalRow);
      }
    } else {
      // 선택 취소하면 input 숨김
      input.style.display = "none";
    }
  });
});
/*

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
});*/