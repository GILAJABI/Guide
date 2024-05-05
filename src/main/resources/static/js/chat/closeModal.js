// 모달창 요소를 선택
var modal = document.getElementById("chatModal");

// 모달창 배경 클릭 이벤트 리스너 추가
modal.addEventListener("click", function(event) {
    // 클릭한 요소가 모달창의 배경인지 확인
    if (event.target == modal) {
        // 모달창 닫기
        modal.style.display = "none";
    }
});
