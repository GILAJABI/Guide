package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Join;
import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.Review;
import com.guide.ex.repository.search.AllPostSearch;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@Log4j2
public class PostSearchTests {

//    @Autowired
//    @Qualifier("allPostSearchImpl") // AllPostSearch는 1개 이상의 빈을 사용하고 있어 우선순위를 지정
//    private AllPostSearch allPostSearch;
//
//    @Test
//    public void testSearchPost() {
//        String postType = "Join";
//
//        List<Post> posts = allPostSearch.searchPost(postType);
//
//        assertTrue("검색 결과가 비어 있습니다.", !posts.isEmpty());
//
//        assertNotNull(posts);
//
//        for (Post post : posts) {
//            if (post instanceof Join) {
//                Join join = (Join) post;
//                log.info("Join 엔티티의 정보:");
//                log.info("post_id: " + join.getPostId());
////                log.info("comment_count: " + join.getCommentCount());
//                log.info("content: " + join.getContent());
//                // 나머지 필드들도 필요한 대로 출력
//                log.info("numPeople: " + join.getNumPeople());
//                log.info("expense: " + join.getExpense());
//                log.info("startTravelDate: " + join.getStartTravelDate());
//                log.info("endTravelDate: " + join.getEndTravelDate());
//                log.info("-----------------------------------------");
//                // 이하 필요한 필드들을 추가로 출력
//            }
//        }
//    }
//    @Test
//    public void testSearchPostPaging() {
//        String postType = "Carrot";
//        int page = 0;
//        int size = 10;
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        Page<Post> postPage = allPostSearch.searchPostPaging(postType, size,page);
//
//        assertNotNull(postPage);
//        assertNotNull(postPage.getContent());
//
//        // 출력 테스트
//        System.out.println("페이징 결과:");
//        System.out.println("전체 항목 수: " + postPage.getTotalElements());
//        System.out.println("페이지당 항목 수: " + postPage.getSize());
//        System.out.println("전체 페이지 수: " + postPage.getTotalPages());
//        System.out.println("현재 페이지 번호: " + postPage.getNumber());
//        System.out.println("페이징된 결과: ");
//        postPage.getContent().forEach(post -> {
//            System.out.println("POST ID: " + post.getPostId());
//            // 필요한 다른 속성들도 출력
//        });
//    }
//
//    @Test
//    void testSearchPostContaining() {
//        // Given
//        String searchValue = "리뷰";
//
//        // When
//        List<Post> posts = allPostSearch.searchPostContaining("ㅁㄴㅇ", "Review");
//
//        // Then
//        assertNotNull(posts);
//        assertFalse(posts.isEmpty());
//        for (Post post : posts) {
//            log.info("Post title: " + post.getTitle());
//            log.info("Post content: " + post.getContent());
//            log.info("Post id: " + post.getPostId());
////            log.info(post.getTitle().contains(postTitle) || post.getContent().contains(postContent));
//        }
//    }

}
