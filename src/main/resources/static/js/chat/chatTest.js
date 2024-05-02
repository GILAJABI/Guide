document.addEventListener("DOMContentLoaded", function() {
    var ws;
    var form = document.getElementById('messageForm');
    var messagesList = document.getElementById('messagesList');
    var messageInput = document.getElementById('messageInput');

    function connect() {
        ws = new WebSocket('ws://localhost:8080/chat/message');

        ws.onmessage = function(event) {
            var message = document.createElement('li');
            message.textContent = event.data;
            messagesList.appendChild(message);
        };

        ws.onopen = function(event) {
            console.log('Connection opened');
        };

        ws.onclose = function(event) {
            console.log('Connection closed');
            setTimeout(connect, 1000);  // Try to reconnect every second
        };

        ws.onerror = function(event) {
            console.error('WebSocket error', event);
        };
    }

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        if (ws.readyState === WebSocket.OPEN && messageInput.value) {
            ws.send(messageInput.value);
            messageInput.value = '';
        }
    });

    connect(); // Connect immediately on page load
});
