package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.*;

import java.util.List;

public interface PostService {

    void carrotRegister(PostDTO postDTO, CarrotDTO carrotDTO, ImageDTO imageDTO);
    void reviewRegister(PostDTO postDTO, ReviewDTO reviewDTO);
    void joinRegister(PostDTO postDTO, JoinDTO joinDTO);
    void carrotModify(PostDTO postDTO, CarrotDTO carrotDTO);
    void reviewModify(PostDTO postDTO, ReviewDTO reviewDTO);
    void joinModify(PostDTO postDTO, JoinDTO joinDTO);

}
