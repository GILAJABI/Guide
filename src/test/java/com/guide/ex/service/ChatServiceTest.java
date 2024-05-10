package com.guide.ex.service;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.repository.chat.ChatRoomRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Log4j2
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void testCreateChatRoom() {

        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()

                .roomNumber(123L)
                .build();

        Long createdRoomId = chatService.createChatRoom(chatRoomDTO);
        ChatRoom test = chatRoomRepository.findById(createdRoomId).orElse(null);

        assertEquals(test.getRoomId(), createdRoomId);
    }
}