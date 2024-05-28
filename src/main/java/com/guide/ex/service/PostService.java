package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.post.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {
    void carrotRegister(CarrotDTO carrotDTO, MultipartFile file, HttpSession session);
    void joinRegister(JoinDTO joinDTO, MultipartFile file, HttpSession session);
    void reviewRegister(ReviewDTO reviewDTO, MultipartFile file, HttpSession session);
    void carrotModify(CarrotDTO carrotDTO,  MultipartFile file, HttpSession session);
    void reviewModify(ReviewDTO reviewDTO,  MultipartFile file, HttpSession session);
    void joinModify(JoinDTO joinDTO, MultipartFile file, HttpSession session);

    Page<PostDTO> postSelectAll(String searchValue, String postType, Pageable pageable);

    Page<CarrotDTO> carrotTypeReadAll(int size, int page, Sort sort);
    Page<ReviewDTO> reviewTypeReadAll(int size, int page, Sort sort);
    Page<JoinDTO> joinTypeReadAll(int size, int page, Sort sort);

    boolean deletePost(Long postId, Long memberId);
    String findPostTypeByPostId(Long postId);

    void updatePostCommentCount(Long postId);

    CarrotDTO postCarrotRead(Long postId);
    ReviewDTO postReadReview(Long postId);
    JoinDTO postReadJoin(Long postId);

    String getPostType(Long postId);
}
