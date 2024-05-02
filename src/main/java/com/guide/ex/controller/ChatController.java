package com.guide.ex.controller;

import com.guide.ex.domain.ChatMessage;
import com.guide.ex.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("chat/message")
    public void message(ChatMessage message) {
        System.out.println("message 호출");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}