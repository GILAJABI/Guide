package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.post.*;
import com.guide.ex.repository.search.AllPostSearch;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@Log4j2
@Transactional   // 롤백을 적용함으로써 보다 각 테스트 별 독립적인 실행을 보장
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @Qualifier("allPostSearchImpl")
    @Autowired
    private AllPostSearch allPostSearch;

    @Test
    public void testCarrotRegister() {      // 당근 게시글 작성
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

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid("c://https://www.naver.com")
                .ord(2)
                .fileName("NAVER.html")
                .build();

        log.info("PostDTO: {}", postDTO);
        log.info("ImageDTO: {}", imageDTO);
        log.info("CarrotDTO: {}", carrotDTO);
        postService.carrotRegister(postDTO, carrotDTO, imageDTO);
        log.info("AFTER PostDTO: {}", postDTO);
        log.info("ImageDTO: {}", imageDTO);
        log.info("CarrotDTO: {}", carrotDTO);
    }

    @Test
    public void testReviewRegister() {      // 리뷰 게시글 작성
        PostDTO postDTO = PostDTO.builder()
                .memberId(13L)
                .title("리뷰좀 달자.") // 유효한 타이틀
                .content("이건 느낌 왔다(제발)")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now().plusDays(12))
                .locationX(BigDecimal.valueOf(37.943972))
                .locationY(BigDecimal.valueOf(51.586388))
                .postType("Review")
                .build(); // postId는 여기서 설정하지 않습니다.

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .grade(3L)
                .startTravelDate(LocalDateTime.now())
                .endTravelDate(LocalDateTime.now().plusDays(15))
                .expense(750000)
                .build(); // postId는 여기서 설정하지 않습니다.

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid("c://https://www.Google.com")
                .ord(5)
                .fileName("Google.html")

                .build();


        log.info("PostDTO: {}", postDTO);
        postService.reviewRegister(postDTO, reviewDTO, imageDTO);
    }


    @Test
    public void testJoinRegister() {        // // 모집 게시글 작성
        PostDTO postDTO = PostDTO.builder()
                .memberId(13L)
                .title("새벽 4시 40분.") // 유효한 타이틀
                .content("안졸린거 같기도(아닌가)")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now().plusDays(25))
                .locationX(BigDecimal.valueOf(75.732128))
                .locationY(BigDecimal.valueOf(68.759421))
                .postType("Join")
                .build(); // postId는 여기서 설정하지 않습니다.

        JoinDTO joinDTO = JoinDTO.builder()
                .startTravelDate(LocalDateTime.now())
                .endTravelDate(LocalDateTime.now().plusDays(15))
                .expense(1500000)
                .numPeople(3)
                .build();

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid("c://https://www.nate.com")
                .ord(1)
                .fileName("Nate.html")
                .build();

        log.info("PostDTO: {}", postDTO);
        postService.joinRegister(postDTO, joinDTO, imageDTO);
    }

    @Test
    public void testCarrotModify() {        // 당근 게시물 수정
        PostDTO postDTO = PostDTO.builder()
                .memberId(1L)
                .postId(2L)
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        CarrotDTO carrotDTO = CarrotDTO.builder()
                .postId(2L)
                .price(812000)
                .build();

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid("c://https://www.nate.com")
                .ord(5)
                .fileName("StarBucks.html")
                .build();

        log.info("PostDTO: {}", postDTO);
        log.info("CarrotDTO: {}", carrotDTO);
        log.info("ImageDTO: {}", imageDTO);

        postService.carrotModify(postDTO, carrotDTO, imageDTO);
    }

    @Test
    public void testReviewModify() {        // 리뷰 게시판 수정
        PostDTO postDTO = PostDTO.builder()
                .memberId(11L)
                .postId(27L)
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .postId(27L)
                .expense(50000)
                .grade(5L)
                .startTravelDate(LocalDateTime.now())
                .endTravelDate(LocalDateTime.now().plusDays(30))
                .build();

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid("c://https://www.bitcamp.com")
                .ord(5)
                .fileName("BitCamp.html")
                .build();
        log.info("PostDTO: {}", postDTO);
        log.info("ReviewDTO: {}", reviewDTO);
        log.info("ImageDTO: {}", imageDTO);

        postService.reviewModify(postDTO, reviewDTO, imageDTO);
    }

    @Test
    public void testJoinModify() {      // 모집 게시판 수정
        PostDTO postDTO = PostDTO.builder()
                .memberId(3L)
                .postId(26L)
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        JoinDTO joinDTO = JoinDTO.builder()
                .postId(26L)
                .expense(702000)
                .numPeople(2)
                .startTravelDate(LocalDateTime.now())
                .endTravelDate(LocalDateTime.now().plusDays(10))
                .build();

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid("c://https://www.BixBox.com")
                .ord(5)
                .fileName("BixBox.html")
                .build();

        postService.joinModify(postDTO, joinDTO, imageDTO);
    }

// -------------------------------------------------------------------------
    @Test
    public void testSearchPostOne() {       // 게시글 상세 검색
//        allPostSearch.searchOne(60L,"Review");
        postService.PostDetailRead(60L,"Review");
    }
    @Test
    public void testSearchPostTypeAll() {   // 게시판 유형에 따른 페이징 처리(메인 -> 각 게시판 진입 시)
        Page<PostDTO> postPage = postService.PostTypeReadAll("Carrot", 6, 5);
        log.info("postPage: {}", postPage);
        log.info("postPage.getTotalElements(): {}", postPage.getTotalElements());
        log.info("postPage.getTotalPages(): {}", postPage.getTotalPages());
        log.info("postPage.getNumber(): {}", postPage.getNumber());
        log.info("postPage.getSize(): {}", postPage.getSize());
        postPage.getContent().forEach(post -> {
            System.out.println("POST ID: " + post.getPostId());
            // 필요한 다른 속성들도 출력
        });
    }
    @Test
    public void testSelectAll() {
        List<PostDTO> postPage = postService.PostSelectAll("타조", "Review");
        assertNotNull(postPage);    // postPage 객체가 null이 아닌지 확인
        assertFalse(postPage.isEmpty());    // postPage 객체가 비어있는지 확인, isEmpty() = false 반환
        postPage.forEach(postDTO ->
                log.info("postDTO: {}", postDTO));
    }
//    -----------------------위의 까지 3개의 테스트 코드가 Service -> Repository---------------------------------



    @Test
    public void testSearchPostAll() {
        Page<Post> postPage = allPostSearch.searchPostPaging("Carrot", 4,6);

        System.out.println("페이징 결과:");
        System.out.println("전체 항목 수: " + postPage.getTotalElements());
        System.out.println("페이지당 항목 수: " + postPage.getSize());
        System.out.println("전체 페이지 수: " + postPage.getTotalPages());
        System.out.println("현재 페이지 번호: " + postPage.getNumber());
        System.out.println("페이징된 결과: ");
        postPage.getContent().forEach(post -> {
            System.out.println("POST ID: " + post.getPostId());
            // 필요한 다른 속성들도 출력
        });
    }

    @Test
    void testSearchPostContaining() {
        // Given
        String searchValue = "승규";

        // When
        List<Post> posts = allPostSearch.searchPostContaining(searchValue, "Review");

        // Then
        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        for (Post post : posts) {
            log.info("Post title: " + post.getTitle());
            log.info("Post content: " + post.getContent());
            log.info("Post id: " + post.getPostId());
            log.info("-----------------------------------");
//            log.info(post.getTitle().contains(postTitle) || post.getContent().contains(postContent));
        }
    }
}
