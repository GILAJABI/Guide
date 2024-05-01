package com.guide.ex.repository;

import com.guide.ex.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "select now()", nativeQuery = true)
    String getTime();
}
