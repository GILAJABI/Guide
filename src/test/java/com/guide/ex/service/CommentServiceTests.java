package com.guide.ex.service;

import com.guide.ex.domain.post.Comment;
import com.guide.ex.dto.post.CommentDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CommentServiceTests {
    @Autowired
    private CommentService commentService;

    @Test
    public void testRegister() {
        CommentDTO commentDTO = CommentDTO.builder()
                .commentContent("CommentDTO Text")
                .postId(4L)
                .memberId(3L)
                .build();

        log.info(commentService.register(commentDTO));
    }
}
