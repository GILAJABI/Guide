package com.guide.ex.repository.chat;

import com.guide.ex.domain.chat.ChatBlocked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBlockedRepository extends JpaRepository<ChatBlocked, Long> {
//    @Query(value = "SELECT NOW()", nativeQuery = true)
//    String getCurrentTime();
//
//    void getTime();
}
