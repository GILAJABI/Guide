package com.guide.ex.controller;

import com.guide.ex.domain.ChatMessage;
import com.guide.ex.domain.ChatRoom;
import com.guide.ex.repository.ChatMessageRepository;
import com.guide.ex.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessage message) {
        ChatRoom room = chatRoomRepository.findById(message.getChatRoom().getRoomId()).orElse(null);
        if (room != null) {
            chatMessageRepository.save(message);
            messagingTemplate.convertAndSend("/sub/chat/room/" + room.getRoomId(), message);
        }
    }
}
