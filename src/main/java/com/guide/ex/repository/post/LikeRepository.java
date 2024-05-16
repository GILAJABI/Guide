package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
