package com.guide.ex.repository.search;

import com.guide.ex.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AllPostSearch {

    List<Post> searchPost(String postType);

    Page<Post> searchPostPaging(String postType, Pageable pageable);

    List<Post> searchPostContaining(String searchValue, String postType);

//    void searchReviewOne(Long postId);
//    void searchCarrotOne(Long postId);
//    void searchJoinOne(Long postId);

    Post searchOne(Long postId, String postType);
    void updateViews(Long postId);
}
