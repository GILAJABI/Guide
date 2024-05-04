var modal = document.getElementById('chatModal');
var icon = document.querySelector('.profile');

function openModal() {
  modal.style.display = 'block';
}

function closeModal() {
  modal.style.display = 'none';
}

icon.addEventListener('click', openModal);

var closeButton = document.querySelector('.close');
closeButton.addEventListener('click', closeModal);

window.onclick = function(event) {
  if (event.target == modal) {
    closeModal();
  }
}

document.addEventListener("DOMContentLoaded", function() {
  // 1:1채팅 버튼을 클릭할 때마다 새로운 채팅 리스트를 추가합니다.
  document.querySelector(".newChat").addEventListener("click", function() {
    addNewChatList();
  });
});

function addNewChatList() {
  // 리스트 아이템 생성
  var newListItem = document.createElement("li");

  // 채팅 리스트 HTML 작성
  newListItem.innerHTML = `
    <div class="chat_list">
      <img class="profile" src="../../static/image/profile.png">
      <div class="list_div">
        <span class="name">username3</span>
        <!-- 채팅 신청 버튼 -->
      </div>
      <div class="delete_div">
        <button class="delete_btn">
          <img src="../../static/image/new_delete.png" alt="Delete">
        </button>
      </div>
    </div>
  `;

  // 기존 리스트에 새로운 아이템 추가
  document.querySelector(".container ul").appendChild(newListItem);

  var newChatDiv = newListItem.querySelector('.list_div');
  newChatDiv.addEventListener('click', function() {
    window.open('chatRoom.html', '_blank', 'width=375px,height=667px');
  });
}
