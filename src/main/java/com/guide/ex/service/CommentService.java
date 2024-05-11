package com.guide.ex.service;

import com.guide.ex.dto.PageRequestDTO;
import com.guide.ex.dto.PageResponseDTO;
import com.guide.ex.dto.post.CommentDTO;

import java.util.List;

public interface CommentService {

    Long register(CommentDTO commentDTO);

    CommentDTO read(Long commentId);

    void modify(CommentDTO commentDTO);

    void remove(Long commentId);

    PageResponseDTO<CommentDTO> getListOfPost(Long postId, PageRequestDTO pageRequestDTO);
}
