package com.guide.ex.repository.chat;

import com.guide.ex.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    @Query(value = "SELECT NOW()", nativeQuery = true)
//    String getCurrentTime();
//
//    void getTime();
}
