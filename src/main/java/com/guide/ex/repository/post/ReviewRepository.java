package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}