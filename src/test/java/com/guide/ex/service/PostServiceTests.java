package com.guide.ex.service;

import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @Test
    public void testCarrotRegister() {
        // Carrot 객체 생성
        PostDTO.Carrot carrot = PostDTO.Carrot.builder()
                .title("성공이니?.")
                .content("아 나 이 스 ~?")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now().plusDays(5))
                .locationX(BigDecimal.valueOf(23.1234567))
                .locationY(BigDecimal.valueOf(33.2268567))
                .price(152500)
                .status(true)
                .build();

        // PostDTO 객체 생성 및 Carrot 설정
        PostDTO postDTO = PostDTO.builder()
                .carrot(carrot)
                .build();

        postService.register("Carrot", postDTO);
    }

    @Test
    public void testCarrotDelete() {
        String postType = "Carrot";
        Long postId = 43L;

        postService.remove(postType, postId);
    }
}
