package com.guide.ex.repository.search;

import com.guide.ex.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearch {
    Page<Post> search(Pageable pageable);
}
