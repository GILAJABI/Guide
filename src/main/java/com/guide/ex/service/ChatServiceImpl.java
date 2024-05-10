package com.guide.ex.service;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.repository.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public Long createChatRoom(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomNumber(chatRoomDTO.getRoomNumber())
                .build();

        // 채팅방 저장
        chatRoom = chatRoomRepository.save(chatRoom);

        // 생성된 채팅방 ID 반환
        return Long.valueOf(chatRoom.getRoomId());
    }
}
