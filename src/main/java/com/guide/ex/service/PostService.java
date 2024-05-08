package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.JoinDTO;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.dto.post.ReviewDTO;

import java.util.List;

public interface PostService {

//    boolean confirm(MemberDTO memberDTO);
    void carrotRegister(PostDTO postDTO, CarrotDTO carrotDTO);
    void reviewRegister(PostDTO postDTO, ReviewDTO reviewDTO);
    void joinRegister(PostDTO postDTO, JoinDTO joinDTO);
    void carrotModify(PostDTO postDTO, CarrotDTO carrotDTO);
    void reviewModify(PostDTO postDTO, ReviewDTO reviewDTO);
    void joinModify(PostDTO postDTO, JoinDTO joinDTO);

}
