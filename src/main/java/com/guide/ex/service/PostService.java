package com.guide.ex.service;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.PostDTO;

public interface PostService {

    boolean commonTask(PostDTO postDTO);
    void carrotRegister(PostDTO postDTO);

}
