package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {
    void carrotRegister(CarrotDTO carrotDTO, MultipartFile file, HttpSession session);
    void joinRegister(JoinDTO joinDTO, MultipartFile file, HttpSession session);
    void reviewRegister(ReviewDTO reviewDTO, MultipartFile file, HttpSession session);
    void carrotModify(PostDTO postDTO, CarrotDTO carrotDTO, ImageDTO imageDTO);
    void reviewModify(PostDTO postDTO, ReviewDTO reviewDTO, ImageDTO imageDTO);
    void joinModify(PostDTO postDTO, JoinDTO joinDTO, ImageDTO imageDTO);
    void postDetailRead(Long memberId, String postType);
    Page<PostDTO> postTypeReadAll(String postType,int size, int page);
    List<PostDTO> postSelectAll(String searchValue, String postType);
}
