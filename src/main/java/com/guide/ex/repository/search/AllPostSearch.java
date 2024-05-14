package com.guide.ex.repository.search;

import com.guide.ex.domain.post.Carrot;
import com.guide.ex.domain.post.Join;
import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AllPostSearch {

    List<Post> searchPost(String postType);

    List<Post> searchPostContaining(String searchValue, String postType);

    Page<Carrot> searchCarrotPaging(int size, int page, Sort sort);
    Page<Review> searchReviewPaging(int size, int page, Sort sort);
    Page<Join> searchJoinPaging(int size, int page, Sort sort);

    Post searchOne(Long postId);
    void updateViews(Long postId);

    boolean deleteOne(Long postId, Long memberId);
}
