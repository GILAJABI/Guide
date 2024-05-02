package com.guide.ex.repository;

import com.guide.ex.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    @Query(value = "SELECT NOW()", nativeQuery = true)
//    String getCurrentTime();
//
//    void getTime();
}
