package com.guide.ex.service;

import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.PostDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@SpringBootTest
@Log4j2
@Transactional   // 롤백을 적용함으로써 보다 각 테스트 별 독립적인 실행을 보장
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @Test
    public void testCarrotRegister() {
        PostDTO postDTO = PostDTO.builder()
                .memberId(13L)
                .title("너무 졸린데.") // 유효한 타이틀
                .content("성공좀되라 제발")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now().plusDays(25))
                .locationX(BigDecimal.valueOf(85.143867))
                .locationY(BigDecimal.valueOf(64.546438))
                .postType("Carrot")
                .build(); // postId는 여기서 설정하지 않습니다.

        CarrotDTO carrotDTO = CarrotDTO.builder()
                .price(642000)
                .build(); // postId는 여기서 설정하지 않습니다.

        log.info("PostDTO: {}", postDTO);
        postService.carrotRegister(postDTO, carrotDTO);
    }


}
