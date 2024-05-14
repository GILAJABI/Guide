package com.guide.ex.repository.search;

import com.guide.ex.domain.post.Carrot;
import com.guide.ex.domain.post.Join;
import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AllPostSearch {

    List<Post> searchPost(String postType);

//    Page<Post> searchPostPaging(String postType,int size, int page);

    List<Post> searchPostContaining(String searchValue, String postType);

    Page<Carrot> searchCarrotPaging(int size, int page);
    Page<Review> searchReviewPaging(int size, int page);
    Page<Join> searchJoinPaging(int size, int page);

    Post searchOne(Long postId);
    void updateViews(Long postId);

    boolean deleteOne(Long postId, Long memberId);
}
