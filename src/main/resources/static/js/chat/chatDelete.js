document.addEventListener('DOMContentLoaded', function() {
    // 모든 삭제 버튼을 가져옵니다.
    var deleteButtons = document.querySelectorAll('.delete_btn');

    // 각 삭제 버튼에 클릭 이벤트 리스너를 추가합니다.
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            // 가장 가까운 부모 li 요소를 찾습니다.
            var listItem = button.closest('.chat_div');

            // DOM에서 부모 li 요소를 제거합니다.
            if (listItem) {
                listItem.remove();
            }
        });
    });
});
