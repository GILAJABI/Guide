package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Carrot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarrotRepository extends JpaRepository<Carrot, Long> {
    @Query("select c from Carrot c where c.post.postId = :postId")
    Carrot findByPostId(Long postId);
}
