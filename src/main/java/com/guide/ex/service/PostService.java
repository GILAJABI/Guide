package com.guide.ex.service;

import com.guide.ex.dto.post.PostDTO;

public interface PostService {

    void register(String postType, PostDTO postDTO);

    void remove(String postType, Long postId);
}
