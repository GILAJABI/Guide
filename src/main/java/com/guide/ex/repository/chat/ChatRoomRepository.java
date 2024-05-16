package com.guide.ex.repository.chat;

import com.guide.ex.domain.chat.ChatRoom;
import com.guide.ex.domain.member.Member;
import com.guide.ex.dto.chat.ChatRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findBySender(Member member);

    @Query("SELECT cr FROM ChatRoom cr " +
            "WHERE (cr.sender = :sender AND cr.receiver = :receiver) " +
            "OR (cr.sender = :receiver AND cr.receiver = :sender)")
    Optional<ChatRoom> findRoomBySenderAndReceiver(@Param("sender") Member sender, @Param("receiver") Member receiver);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.sender = :member OR cr.receiver = :member")
    List<ChatRoom> findBySenderOrReceiver(@Param("member") Member member);

}
