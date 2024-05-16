package com.guide.ex.repository.chat;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.domain.member.Member;
import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.repository.chat.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

//    @Test
//    public void testInsertChatRoom() {
//
//        LocalDateTime now = LocalDateTime.now();
//
//        ChatRoom chatRoom = ChatRoom.builder()
//                .roomNumber(2L)
//                .createdAt(now)
//                .build();
//
//        chatRoomRepository.save(chatRoom);
//    }
//
//    @Test
//    public void testSelectChatRoom(){
//        Long roomId = 1L;
//
//        Optional<ChatRoom> result = chatRoomRepository.findById(roomId);
//    }
//
//    @Test
//    public void testUpdate(){
//        Long roomId = 1L;
//
//        Optional<ChatRoom> result = chatRoomRepository.findById(roomId);
//
//        ChatRoom chatRoom = result.orElseThrow();
//
//        chatRoom.change("update...11-----", "update room_id 25------");
//
//        /* save: pk가 테이블에 존재하지 않으면 => 삽입 => insert문
//                 pk 기존 테이블에 존재하면 => 수정 => update문
//        * */
//        chatRoomRepository.save(chatRoom);
//   }
    @Test
    public void testDelete(){
        Long roomId = 1L;

        chatRoomRepository.deleteById(roomId);
    }

    @Test
    public void testFindAll() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        log.info("==============================================================");
        log.info(chatRooms.toString());
        log.info("==============================================================");
    }

    @Test
    public void testFindRoom() {
        Member sender = Member.builder().memberId(7L).build();
        Member receiver = Member.builder().memberId(9L).build();

        Optional<ChatRoom> chatRoom = chatRoomRepository.findRoomBySenderAndReceiver(sender, receiver);

        Optional<ChatRoomDTO> chatRoomDTO = chatRoom.map(room -> {
            return ChatRoomDTO.builder()
                    .roomId(room.getRoomId())
                    .senderId(room.getSender().getMemberId())
                    .receiverId(room.getReceiver().getMemberId())
                    .sender(MemberDTO.builder()
                            .memberId(room.getSender().getMemberId())
                            // 다른 필요한 필드들도 여기에 추가하세요
                            .build())
                    .receiver(MemberDTO.builder()
                            .memberId(room.getReceiver().getMemberId())
                            // 다른 필요한 필드들도 여기에 추가하세요
                            .build())
                    .build();
        });

        log.info("----------------------------------------------");
        log.info(chatRoomDTO.toString());
        log.info("----------------------------------------------");
    }


}
