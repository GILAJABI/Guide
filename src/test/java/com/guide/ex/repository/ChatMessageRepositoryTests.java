package com.guide.ex.repository;

import com.guide.ex.domain.ChatMessage;
import com.guide.ex.domain.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class ChatMessageRepositoryTests {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    public void testTime(){
        log.info("------------test time-------------");
        chatMessageRepository.getTime();
    }

    @Test
    public void testInsertChatMessage() {

        LocalDateTime now = LocalDateTime.now();

        ChatRoom chatRoom = ChatRoom.builder().roomId(1L).build();

        ChatMessage chatMessage = ChatMessage.builder()
                .chatMsg("hello")
                .chatRoom(chatRoom)
                .registDate(now)
                .build();

        chatMessageRepository.save(chatMessage);
    }

    @Test
    public void testSelectChatMessage(){
        Long messageId = 1L;

        Optional<ChatMessage> result = chatMessageRepository.findById(messageId);
    }

    @Test
    public void testUpdate(){
        Long messageId = 2L;

        Optional<ChatMessage> result = chatMessageRepository.findById(messageId);

        ChatMessage chatMessage = result.orElseThrow();

        chatMessage.change("update...regist_date 2024-05-01 09:32:32.049724-----", "update room_id 25------");

        /* save: pk가 테이블에 존재하지 않으면 => 삽입 => insert문
                 pk 기존 테이블에 존재하면 => 수정 => update문
        * */
        chatMessageRepository.save(chatMessage);
    }
    @Test
    public void testDelete(){
        Long messageId = 3L;

        chatMessageRepository.deleteById(messageId);
    }
}
