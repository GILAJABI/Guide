package com.guide.ex.controller.chat;

import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatPageController {

    private final ChatService chatService;

    // 생성자 주입
    public ChatPageController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/api/chat/create")
    public ResponseEntity<?> createChatRoom(@RequestParam("senderId") Long senderId,
                                            @RequestParam("receiverId") Long receiverId) {
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        Long roomId = chatService.createChatRoom(chatRoomDTO);
        return ResponseEntity.ok().body(Map.of("roomId", roomId));
    }
}