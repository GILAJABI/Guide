package com.guide.ex.controller.chat;

import com.guide.ex.dto.chat.ChatMessageDTO;
import com.guide.ex.repository.chat.ChatMessageRepository;
import com.guide.ex.repository.chat.ChatRoomRepository;
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

    @MessageMapping("/message")
    public void sendMessage(ChatMessageDTO message) {
//        ChatRoom room = chatRoomRepository.findById(message.getChatRoom().getRoomId()).orElse(null);
//        if (room != null) {
//            chatMessageRepository.save(message);
        System.out.println(message.toString());
            messagingTemplate.convertAndSend("/topic/chat/room/", message);
        //}
    }
}
