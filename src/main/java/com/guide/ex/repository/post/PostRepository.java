package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Post;
import com.guide.ex.repository.search.AllPostSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, AllPostSearch {
}
