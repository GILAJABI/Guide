package com.guide.ex.controller.chat;

import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatRoomController {

    private final ChatService chatService;

    // 생성자 주입
    public ChatRoomController(ChatService chatService) {
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

    @DeleteMapping("/chat/delete/{roomId}")
    public ResponseEntity<?> deleteChatRoom(@PathVariable Long roomId) {
        try {
            chatService.deleteChatRoom(roomId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류 발생");
        }
    }
}
