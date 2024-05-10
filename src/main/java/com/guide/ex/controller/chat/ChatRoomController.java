package com.guide.ex.controller.chat;

import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat/create")
    public String createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) {
        // 채팅방 생성 로직 호출
        Long createdRoomId = chatService.createChatRoom(chatRoomDTO);

        // 생성된 채팅방 페이지로 리다이렉트
        return "redirect:/chat/chatRoom.html?roomId=" + createdRoomId;
    }
}
