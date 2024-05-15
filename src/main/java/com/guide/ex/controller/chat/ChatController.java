package com.guide.ex.controller.chat;

import com.guide.ex.dto.chat.ChatMessageDTO;
import com.guide.ex.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/message")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {
        chatService.sendMessage(chatMessageDTO);
        Long roomId = chatMessageDTO.getRoomId();
        messagingTemplate.convertAndSend("/topic/chat/room/" + roomId, chatMessageDTO);
    }
}
