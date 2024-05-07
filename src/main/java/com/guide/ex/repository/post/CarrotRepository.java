package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Carrot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrotRepository extends JpaRepository<Carrot, Long> {
}
