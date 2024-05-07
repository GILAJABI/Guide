class ChatMessage {
    constructor(text, sender, timestamp) {
        this.id = uuidv4(); // 고유 식별자 생성
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
        this.isSent = false;
        this.isRead = false;
        this.syncId = this.id; // 다른 기기에서도 동기화되는 ID
    }

    send() {
        this.isSent = true;
        // 메시지 전송 처리 로직
    }

    read() {
        this.isRead = true;
        // 메시지 읽음 처리 로직
    }

    toJSON() {
        return {
            id: this.id,
            text: this.text,
            sender: this.sender,
            timestamp: this.timestamp,
            isSent: this.isSent,
            isRead: this.isRead,
            syncId: this.syncId
        };
    }
}