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
    public void testSearchPostOne() {       // 게시글 상세 검색
//        allPostSearch.searchOne(60L,"Review");
        postService.postDetailRead(60L,"Review");
    }
    @Test
    public void testSearchPostTypeAll() {   // 게시판 유형에 따른 페이징 처리(메인 -> 각 게시판 진입 시)
        Page<CarrotDTO> postPage = postService.carrotTypeReadAll(6, 5);
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
        List<PostDTO> postPage = postService.postSelectAll("타조", "Review");
        assertNotNull(postPage);    // postPage 객체가 null이 아닌지 확인
        assertFalse(postPage.isEmpty());    // postPage 객체가 비어있는지 확인, isEmpty() = false 반환
        postPage.forEach(postDTO ->
                log.info("postDTO: {}", postDTO));
    }

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
