package com.guide.ex.service;

import com.guide.ex.dto.PageRequestDTO;
import com.guide.ex.dto.PageResponseDTO;
import com.guide.ex.dto.post.CommentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Long register(CommentDTO commentDTO);

    CommentDTO read(Long commentId);

    void modify(CommentDTO commentDTO);

    void remove(Long commentId);

    PageResponseDTO<CommentDTO> getListOfPost(Long postId, PageRequestDTO pageRequestDTO);

    PageResponseDTO<CommentDTO> getListOfMember(Long memberId, PageRequestDTO pageRequestDTO);

//    PageResponseDTO<CommentDTO> getListOfPostMember(Long postId, Long memberId, PageRequestDTO pageRequestDTO);
}
