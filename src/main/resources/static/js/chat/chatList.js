document.addEventListener("DOMContentLoaded", function() {
    // 모든 .list_div 요소를 선택하고 클릭 이벤트 리스너를 추가합니다.
    var listDivElements = document.querySelectorAll('.list_div');
    listDivElements.forEach(function(element) {
        element.addEventListener('click', function() {
            window.open('chatRoom.html' + roomId, '_blank', 'width=375px,height=667px');
        });
    });

    // 모든 삭제 버튼을 선택하고 클릭 이벤트 리스너를 추가합니다.
    var deleteButtons = document.querySelectorAll('.delete_btn');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            // 이벤트 버블링을 방지합니다.
            event.stopPropagation();

            // 가장 가까운 부모 .chat_div 요소를 찾습니다.
            var listItem = button.closest('.chat_div');

            // DOM에서 부모 .chat_div 요소를 제거합니다.
            if (listItem) {
                listItem.remove();
            }
        });
    });
});