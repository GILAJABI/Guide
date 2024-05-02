package com.guide.ex.repository;

import com.guide.ex.domain.ChatBlocked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatBlockedRepository extends JpaRepository<ChatBlocked, Long> {
    @Query(value = "select now()", nativeQuery = true)
    String getTime();
}
