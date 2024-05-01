package com.guide.ex.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {

    private String message_id;

    private long chat_msg;

    private LocalDateTime lastMessageSentAt;

    public void updateLastMessageSentAt() {
        this.lastMessageSentAt = LocalDateTime.now();
    }

}

