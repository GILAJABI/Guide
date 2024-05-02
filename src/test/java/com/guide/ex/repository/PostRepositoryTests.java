package com.guide.ex.repository;

import com.guide.ex.domain.*;
import com.guide.ex.domain.Carrot;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@Log4j2
public class PostRepositoryTests {

    @Autowired
    private CommentRepository testComment;

    @Autowired
    private LikeRepository testLike;

    @Autowired
    private JoinRepository postJoin;

    @Autowired
    private CarrotRepository testCarrot;

//    @Test
//    public void carrotSearch1() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("post_id").descending());
//        testCarrot.findAll(pageable).forEach(System.out::println);
//    }


//    @Test
//    public void carrotSearchWithQuerydsl() {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//        QCarrot qCarrot = new QCarrot("carrot");
//
//        // Paging 및 정렬 설정
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("postId").descending());
//
//        // Querydsl을 사용하여 검색 쿼리 생성
//        Page<Carrot> result = testCarrot.findAll(qCarrot.post.title.contains("Title"), pageable);
//
//        // 검색 결과 출력
//        result.getContent().forEach(System.out::println);
//    }


    @Test
    public void testInsert() {
        log.info("------------test testInsert-------------");

        Carrot carrot = Carrot.builder()
                .title("Title입니다.")
                .content("내용입니다아아아아아앙")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now().plusDays(5))
                .locationX(BigDecimal.valueOf(23.1234567))
                .locationY(BigDecimal.valueOf(33.2268567))
                .expense(15000)
                .grade(25000L)
                .build();

        Carrot carrot1 = testCarrot.save(carrot);

        assertNotNull(carrot1);
    }

    @Test
    public void testSelect() {
        Long postId = 20L;

        Optional<Carrot> result = testCarrot.findById(postId);

        Carrot carrot = result.get();
        log.info(carrot);
    }

    @Test
    public void testUpdate() {
        Long postId = 20L;
        Optional<Carrot> optional = testCarrot.findById(postId);
        if (optional.isPresent()) {
            Carrot carrot = optional.get();
            log.info("Found post: {}", carrot);
            carrot.change("update..title입니다", "내용수정함");
            carrot = testCarrot.save(carrot);
        }
    }

    @Test
    public void testDelete() {
        Long postId = 7L;
        Optional<Carrot> optional = testCarrot.findById(postId);
        if (optional.isPresent()) {
            Carrot carrot = optional.get();
            log.info("Found post: {}", carrot);
            testCarrot.delete(carrot);
        }
    }

    //---------------------------Comment-------------------
    @Test
    public void testInsertComment() {
        Comment comment = Comment.builder()
                .content("ㅋㅋㅋㅋ집가고싶다.")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();
        Comment com = testComment.save(comment);

        log.info(com);
    }

    @Test
    public void testCommentUpdate() {

        Long bno = 1L;

        Optional<Comment> result = testComment.findById(bno);

        Comment comment = result.orElseThrow();

        comment.change("update..title 100");

        testComment.save(comment);

    }

    @Test
    public void testCommentDelete() {
        Long bno = 2L;

        testComment.deleteById(bno);
    }


    //----------------like-------------------
    @Test
    public void testSaveLike() {
        // 좋아요 엔티티 생성
        Like like = Like.builder()
                .registerDate(LocalDateTime.now())
                // 다른 필요한 필드 설정
                .build();

        // 좋아요 저장
        Like savedLike = testLike.save(like);
        log.info("Saved Like: {}", savedLike);
    }

    //-----------------------join
    @Test
    public void testJoinInsert() {
        log.info("------------test testInsert-------------");

        Join join = Join.builder()
                .title("Title입니다.")
                .content("내용입니다아아아아아앙")
                .registerDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now().plusDays(5))
                .locationX(BigDecimal.valueOf(23.1234567))
                .locationY(BigDecimal.valueOf(33.2268567))
                .expense(15000)
                .numPeople(35000)
                .startTravelDate(LocalDateTime.now())
                .endTravelDate(LocalDateTime.now().plusDays(5))
                .build();

        Join join1 = postJoin.save(join);

        log.info(join1.getPostId());

        assertNotNull(join1);
    }

    @Test
    public void testJoinDelete() {
        Long bno = 14L;

        postJoin.deleteById(bno);
    }
}
