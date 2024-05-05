// WebSocket 연결을 수행할 변수 선언
var socket = new SockJS('/connection');
var stompClient = Stomp.over(socket);

// 웹소켓 연결
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);

    // 메시지를 받았을 때의 동작 정의
    stompClient.subscribe('/topic/chat/room/{roomId}', function(message) {
        const messageBody = JSON.parse(message.body);
        // 메시지를 chatMessages 영역에 추가
        const chatMessagesDiv = document.getElementById("chatMessages");
        const messageParagraph = document.createElement("p");
        messageParagraph.textContent = messageBody.content;
        chatMessagesDiv.appendChild(messageParagraph);
    });
});

// 폼 제출 이벤트 리스너
document.getElementById("chatForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 폼 기본 동작 중단

    const messageInput = document.getElementById("message");
    const message = messageInput.value; // 입력된 메시지 가져오기

    // 메시지를 서버로 전송
    stompClient.send("/app/message", {}, JSON.stringify({
        content: message,
        // 채팅방 식별자 등의 추가 정보도 전달할 수 있음
    }));

    // 입력 폼 비우기
    messageInput.value = "";
});