package com.guide.ex.repository;

import com.guide.ex.domain.Join;
import com.guide.ex.domain.Post;
import com.guide.ex.repository.search.AllPostSearch;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@Log4j2
public class QueryDslTests {

    @Autowired
    private AllPostSearch allPostSearch;

    @Test
    public void testSearchPost() {
        String postType = "Join";

        List<Post> posts = allPostSearch.searchPost(postType);

        assertNotNull(posts);

        for (Post post : posts) {
            if (post instanceof Join) {
                Join join = (Join) post;
                log.info("Join 엔티티의 정보:");
                log.info("post_id: " + join.getPostId());
                log.info("comment_count: " + join.getCommentCount());
                log.info("content: " + join.getContent());
                // 나머지 필드들도 필요한 대로 출력
                log.info("numPeople: " + join.getNumPeople());
                log.info("expense: " + join.getExpense());
                log.info("startTravelDate: " + join.getStartTravelDate());
                log.info("endTravelDate: " + join.getEndTravelDate());
                log.info("-----------------------------------------");
                // 이하 필요한 필드들을 추가로 출력
            }
        }
    }
    @Test
    public void testSearchPostPaging() {
        String postType = "Carrot";
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Post> postPage = allPostSearch.searchPostPaging(postType, pageRequest);

        assertNotNull(postPage);
        assertNotNull(postPage.getContent());

        // 출력 테스트
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
}
