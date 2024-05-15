// const roomId = "YOUR_ROOM_ID"; // 실제 채팅방의 식별자로 대체해야 합니다.

// STOMP 클라이언트 생성
const socket = new SockJS('http:172.16.101.68:8888/connection');
// const socket = new SockJS('http://192.168.0.12:8888/connection');
const stompClient = Stomp.over(socket);

// 연결 시도
let authToken;
let myId = sessionStorage.getItem("member_id")
let memberName = sessionStorage.getItem("member_name")

// console.log(roomId);


stompClient.connect( {Authorization: 'Bearer ' + authToken}, function(frame) {
    console.log('Connected: ' + frame);
    // 메시지를 받았을 때의 동작 정의
    stompClient.subscribe("/topic/chat/room/" + roomId, function(message) {
        const messageBody = JSON.parse(message.body);
        // 메시지를 chatMessages 영역에 추가
        const chatMessagesDiv = document.getElementById("chat_list");
        const messageParagraph = document.createElement("span");
        const senderName = messageBody.memberName; // 보낸 사람의 이름 또는 ID
        const messageContent = messageBody.chatMsg; // 메시지 내용
        const senderId = messageBody.memberId; // 보낸 사람의 ID
        console.log(myId);
        console.log(senderId);

        if(senderName === memberName){
            messageParagraph.classList.add('my_msg');
        } else {
            messageParagraph.classList.add('other_msg')
        }

        messageParagraph.textContent = senderName +  ": " + messageContent; // 보낸 사람과 ID, 메시지 내용 표시
        chatMessagesDiv.appendChild(messageParagraph);

        function scrollToBottom() {
            const chatMessagesDiv = document.getElementById("chat_list");
            chatMessagesDiv.scrollTop = chatMessagesDiv.scrollHeight;
        }

        scrollToBottom();

    });
});


document.addEventListener("DOMContentLoaded", function() {



    document.getElementById("chatForm").addEventListener("submit", function(event) {
        event.preventDefault(); // 폼 기본 동작 중단
        //
        // const messageInput = document.getElementById("chat_content");
        // console.log("messageInput: ",messageInput)
        //
        // const message = messageInput.value; // 입력된 메시지 가져오기
        // console.log("message: ",message)


        const messageInput = document.querySelector("#chat_content");
        console.log(messageInput);
        const message = messageInput.value;

        if (!message.trim()) { // 메시지가 비어 있는지 확인
            console.log('메시지가 비어 있습니다. 전송되지 않았습니다.');
            return; // 전송 중단
        }

        console.log('test: ', message);
        console.log("Message entered:", message);

        // 메시지를 서버로 전송
        // let memberId = sessionStorage.getItem("session_member_id")
        // let memberName = sessionStorage.getItem("member_name")
        const chatMessageDTO = {
            chatMsg: message,
            memberId: myId,
            memberName: memberName,
            roomId: roomId,
            // registDate: new Date().toISOString()
        };
        console.log(chatMessageDTO)
        stompClient.send("/app/message", {}, JSON.stringify(chatMessageDTO));
        // 입력 폼 비우기
        messageInput.value = "";

    });
});
