document.addEventListener("DOMContentLoaded", function() {
    // 모든 .list_div 요소를 선택합니다.
    var listDivElements = document.querySelectorAll('.list_div');

    // 모든 .list_div 요소에 대해 반복하며 클릭 이벤트를 추가합니다.
    listDivElements.forEach(function(element) {
        element.addEventListener('click', function() {
            window.open('chatRoom.html', '_blank', 'width=375px,height=667px');
        });
    });
});

// var link = document.querySelector('a[href="../../../templates/chat/chatRoom]')

// link.onclick = function(event) {
//     window.open(this.href, '_blank', 'width=375px,height=667px');

//     event.preventDefault();
// };
