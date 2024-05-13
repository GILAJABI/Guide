package com.guide.ex.service;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.domain.member.Member;
import com.guide.ex.dto.chat.ChatRoomDTO;
import com.guide.ex.repository.chat.ChatRoomRepository;
import com.guide.ex.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberRepository memberRepository; // MemberRepository 주입

    @Override
    public Long createChatRoom(ChatRoomDTO chatRoomDTO) {
        // DTO에서 senderId와 receiverId를 이용해 실제 Member 엔티티 조회
        Member sender = memberRepository.findById(chatRoomDTO.getSenderId()).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Member receiver = memberRepository.findById(chatRoomDTO.getReceiverId()).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // ChatRoom 객체 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .chatMessages(new ArrayList<>()) // 채팅 메시지 리스트 초기화
                .build();

        // 채팅방 저장
        chatRoom = chatRoomRepository.save(chatRoom);

        // 생성된 채팅방 ID 반환
        return chatRoom.getRoomId();
    }
}
