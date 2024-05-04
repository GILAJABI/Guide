function createChatRoom(username) {
    // 새로운 채팅방을 생성하는 코드 작성
    var ul = document.querySelector('.container ul');
    var li = document.createElement('li');
    var chatList = document.createElement('div');
    chatList.classList.add('chat_list');

    var img = document.createElement('img');
    img.classList.add('profile');
    img.src = "../../static/image/profile.png";

    var listDiv = document.createElement('div');
    listDiv.classList.add('list_div');

    var nameSpan = document.createElement('span');
    nameSpan.classList.add('name');
    nameSpan.textContent = username;

    listDiv.appendChild(nameSpan);

    var deleteDiv = document.createElement('div');
    deleteDiv.classList.add('delete_div');

    var deleteBtn = document.createElement('button');
    deleteBtn.classList.add('delete_btn');
    var deleteImg = document.createElement('img');
    deleteImg.src = "../../static/image/new_delete.png";
    deleteImg.alt = "Delete";
    deleteBtn.appendChild(deleteImg);
    deleteDiv.appendChild(deleteBtn);

    chatList.appendChild(img);
    chatList.appendChild(listDiv);
    chatList.appendChild(deleteDiv);
    li.appendChild(chatList);
    ul.appendChild(li);
}