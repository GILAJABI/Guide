package com.guide.ex.controller.chat;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public List<ChatRoom> getAllRooms() {
        return chatRoomRepository.findAll();
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam Long roomNumber) {
        return chatRoomRepository.save(new ChatRoom(null, null, roomNumber));
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    public ChatRoom getRoomById(@PathVariable Long roomId) {
        return chatRoomRepository.findById(roomId).orElse(null);
    }
}
