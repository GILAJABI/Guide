package com.guide.ex.repository;

import com.guide.ex.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImageRepository<S extends PostImage> extends JpaRepository<S, Long> {
    // 추가적으로 정의해야 할 메서드가 있다면 여기에 추가
}
