package com.guide.ex.repository.post;

import com.guide.ex.domain.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface ImageRepository extends JpaRepository<PostImage, Long> {

}
