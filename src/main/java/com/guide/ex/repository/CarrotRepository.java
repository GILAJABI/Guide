package com.guide.ex.repository;

import com.guide.ex.domain.Carrot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarrotRepository extends JpaRepository<Carrot, Long> {
    @Query("select c from Carrot c where c.post.postId = :postId")
    Carrot findByPostId(Long postId);
}
