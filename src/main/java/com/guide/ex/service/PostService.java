package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void carrotRegister(PostDTO postDTO, CarrotDTO carrotDTO, ImageDTO imageDTO);
    void reviewRegister(PostDTO postDTO, ReviewDTO reviewDTO, ImageDTO imageDTO);
    void joinRegister(PostDTO postDTO, JoinDTO joinDTO, ImageDTO imageDTO);
    void carrotModify(PostDTO postDTO, CarrotDTO carrotDTO, ImageDTO imageDTO);
    void reviewModify(PostDTO postDTO, ReviewDTO reviewDTO, ImageDTO imageDTO);
    void joinModify(PostDTO postDTO, JoinDTO joinDTO, ImageDTO imageDTO);

    PostDTO PostReadOne(Long memberId, String postType);
    Page<PostDTO> PostReadAll(String postType, int page, int size);

}
