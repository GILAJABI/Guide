package com.guide.ex.repository.chat;

import com.guide.ex.domain.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    @Query(value = "SELECT NOW()", nativeQuery = true)
//    String getCurrentTime();
//
//    void getTime();
}
