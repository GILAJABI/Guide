package com.guide.ex.repository;

import com.guide.ex.domain.ChatMessage;
import com.guide.ex.domain.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class ChatRoomRepositoryTests {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

//    @Test
//    public void testTime(){
//        log.info("------------test time-------------");
//        chatRoomRepository.getTime();
//    }

    @Test
    public void testInsertChatRoom() {

        LocalDateTime now = LocalDateTime.now();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomNumber(2L)
                .createdAt(now)
                .build();

        chatRoomRepository.save(chatRoom);
    }

    @Test
    public void testSelectChatRoom(){
        Long roomId = 1L;

        Optional<ChatRoom> result = chatRoomRepository.findById(roomId);
    }

    @Test
    public void testUpdate(){
        Long roomId = 1L;

        Optional<ChatRoom> result = chatRoomRepository.findById(roomId);

        ChatRoom chatRoom = result.orElseThrow();

        chatRoom.change("update...11-----", "update room_id 25------");

        /* save: pk가 테이블에 존재하지 않으면 => 삽입 => insert문
                 pk 기존 테이블에 존재하면 => 수정 => update문
        * */
        chatRoomRepository.save(chatRoom);
   }
    @Test
    public void testDelete(){
        Long roomId = 1L;

        chatRoomRepository.deleteById(roomId);
    }
}
