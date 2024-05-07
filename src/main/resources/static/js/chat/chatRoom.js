// document.addEventListener('DOMContentLoaded', () => {
//     const chatContentInput = document.querySelector('#chat_content');
//     const chatButton = document.querySelector('#chat_btn');
//     let chatList = document.querySelector('#chat_list');
//     const closeButton = document.querySelector('.chat_close button');
//
//     // 채팅 목록이 존재하지 않는 경우 생성
//     if (!chatList) {
//         chatList = document.createElement('div');
//         chatList.id = 'chat_list';
//         document.body.appendChild(chatList);
//     }
//
//     // 채팅 보내기 버튼에 대한 클릭 이벤트 리스너
//     chatButton.addEventListener('click', () => {
//         const chatContent = chatContentInput.value.trim();
//         if (chatContent !== '') {
//             const chatItem = document.createElement('div');
//             const text = document.createElement('span');
//
//             text.textContent = chatContent;
//
//             chatItem.appendChild(text);
//             chatList.appendChild(chatItem);
//
//             chatContentInput.value = ''; // 입력 필드 초기화
//         }
//     });
//
//     // 닫기 버튼에 대한 클릭 이벤트 리스너
//     closeButton.addEventListener('click', () => {
//         window.close(); // 창 닫기
//     });
// });
