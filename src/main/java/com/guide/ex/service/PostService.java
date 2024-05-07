package com.guide.ex.service;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.JoinDTO;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.dto.post.ReviewDTO;

public interface PostService {

//    boolean confirm(MemberDTO memberDTO);
    void carrotRegister(PostDTO postDTO, CarrotDTO carrotDTO);
    void reviewRegister(PostDTO postDTO, ReviewDTO reviewDTO);
    void joinRegister(PostDTO postDTO, JoinDTO joinDTO);

}
