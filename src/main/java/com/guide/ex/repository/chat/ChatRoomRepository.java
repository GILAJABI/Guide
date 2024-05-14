package com.guide.ex.repository.chat;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.domain.member.Member;
import com.guide.ex.dto.chat.ChatRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findBySender(Member member);
}
