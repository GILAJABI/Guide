package com.guide.ex.repository.search;

import com.guide.ex.domain.member.QMember;
import com.guide.ex.domain.post.*;
import com.guide.ex.dto.post.PostDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository("allPostSearchImpl")
@Log4j2
//@Repository
public class AllPostSearchImpl extends QuerydslRepositorySupport implements AllPostSearch {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public AllPostSearchImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> searchPost(String postType) {
        QPost post = QPost.post;

        return queryFactory.selectFrom(post)
                .where(post.postType.contains(postType))
                .fetch();
    }


//    @Override
//    public Page<Post> searchPostPaging(String postType, int size, int page) {    // 게시판 유형에 따른 모든 게시글 검색
//        QPost post = QPost.post;
//
//        Pageable pageable = PageRequest.of(page - 1, size);
//
//        JPAQuery<Post> query = new JPAQuery<>(entityManager);
//
//        query.from(post)
//                .where(post.postType.contains(postType));
//
//        long total = query.fetchCount();
//
//        query.offset(pageable.getOffset())
//                .limit(pageable.getPageSize());
//
//        List<Post> posts = query.fetch();
//
//        return new PageImpl<>(posts, pageable, total);
//    }

    @Override
    public Page<Carrot> searchCarrotPaging(int size, int page) {    // 당근 게시판 모든 게시글 검색
        QCarrot carrot = QCarrot.carrot; // QCarrot 인스턴스 생성
        QPostImage postImage = QPostImage.postImage; // PostImage의 QueryDSL 메타모델
        QMember member = QMember.member; // Member의 QueryDSL 메타모델

        // 페이지 요청과 함께 sort를 사용하여 정렬 설정
        Pageable pageable = PageRequest.of(page - 1, size);

        JPAQuery<Carrot> query = new JPAQuery<>(entityManager);
        query.from(carrot)
                .join(carrot.member, member).fetchJoin() // Member와 페치 조인
                .join(carrot.postImages, postImage).fetchJoin() // PostImage와 페치 조인
                .where(carrot.isDeleted.eq(false).and(carrot.postType.eq("Carrot"))) // 삭제되지 않은 Carrot 게시물 검색
                .orderBy(carrot.registerDate.desc()) // 여러 조건으로 정렬

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount(); // 전체 아이템 수 가져오기
        List<Carrot> carrots = query.fetch(); // 페이지 정보 적용하여 데이터 가져오기

        log.info("Total items: {}, Query: {}", total, query);

        return new PageImpl<>(carrots, pageable, total);
    }


    @Override
    public Page<Review> searchReviewPaging(int size, int page) {
        QReview review = QReview.review; // QCarrot 인스턴스 생성
        QPostImage postImage = QPostImage.postImage; // PostImage의 QueryDSL 메타모델
        QMember member = QMember.member; // Member의 QueryDSL 메타모델

        // 페이지 요청과 함께 registerDate를 기준으로 내림차순 정렬 설정
        Pageable pageable = PageRequest.of(page - 1, size);

        JPAQuery<Review> query = new JPAQuery<>(entityManager);
        query.from(review)
                .join(review.member, member).fetchJoin() // Member와 페치 조인
                .join(review.postImages, postImage).fetchJoin() // PostImage와 페치 조인
                .where(review.isDeleted.eq(false)) // 삭제되지 않은 Carrot 게시물 검색
                .where(review.postType.eq("Review")) // Review 타입만 검색
                .orderBy(review.registerDate.desc()) // registerDate 기준으로 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount(); // 전체 아이템 수 가져오기
        List<Review> reviews = query.fetch(); // 페이지 정보 적용하여 데이터 가져오기

        log.info("Total items: {}, Query: {}", total, query);

        return new PageImpl<>(reviews, pageable, total);
    }

    @Override
    public Page<Join> searchJoinPaging(int size, int page) {
        QJoin join = QJoin.join; // QCarrot 인스턴스 생성
        QPostImage postImage = QPostImage.postImage; // PostImage의 QueryDSL 메타모델
        QMember member = QMember.member; // Member의 QueryDSL 메타모델

        // 페이지 요청과 함께 registerDate를 기준으로 내림차순 정렬 설정
        Pageable pageable = PageRequest.of(page - 1, size);

        JPAQuery<Join> query = new JPAQuery<>(entityManager);
        query.from(join)
                .join(join.member, member).fetchJoin() // Member와 페치 조인
                .join(join.postImages, postImage).fetchJoin() // PostImage와 페치 조인
                .where(join.isDeleted.eq(false)) // 삭제되지 않은 Carrot 게시물 검색
                .where(join.postType.eq("Join")) // Review 타입만 검색
                .orderBy(join.registerDate.desc()) // registerDate 기준으로 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount(); // 전체 아이템 수 가져오기
        List<Join> joins = query.fetch(); // 페이지 정보 적용하여 데이터 가져오기

        log.info("Total items: {}, Query: {}", total, query);

        return new PageImpl<>(joins, pageable, total);
    }

    @Override
    public Page<Carrot> searchCarrotViewCount(int size, int page) {
        QCarrot carrot = QCarrot.carrot; // QCarrot 인스턴스 생성
        QPostImage postImage = QPostImage.postImage; // PostImage의 QueryDSL 메타모델
        QMember member = QMember.member; // Member의 QueryDSL 메타모델

        // 페이지 요청과 함께 sort를 사용하여 정렬 설정
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "views"));

        JPAQuery<Carrot> query = new JPAQuery<>(entityManager);
        query.from(carrot)
                .join(carrot.member, member).fetchJoin() // Member와 페치 조인
                .join(carrot.postImages, postImage).fetchJoin() // PostImage와 페치 조인
                .where(carrot.isDeleted.eq(false).and(carrot.postType.eq("Carrot"))) // 삭제되지 않은 Carrot 게시물 검색
                .orderBy(carrot.views.desc()) // 여러 조건으로 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount(); // 전체 아이템 수 가져오기
        List<Carrot> carrots = query.fetch(); // 페이지 정보 적용하여 데이터 가져오기

        log.info("Total items: {}, Query: {}", total, query);

        return new PageImpl<>(carrots, pageable, total);
    }


    @Override
    public List<Post> searchPostContaining(String searchValue, String postType) {    // 사용자가 입력한 제목 or 내용 검색 + 페이징 처리 + 특정 게시판 유형 필터
        QPost post = QPost.post;

        JPAQuery<Post> query = new JPAQuery<>(entityManager);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 게시판 유형에 맞는 게시글만 필터링
        booleanBuilder.and(post.postType.eq(postType));
        if (searchValue != null && !searchValue.trim().isEmpty()) {
            booleanBuilder.andAnyOf(    // 여러 조건 중 하나라도 참이면 전체 조건이 참
                    post.title.contains(searchValue),
                    post.content.contains(searchValue),
                    post.member.name.contains(searchValue)
            );
        }

        query.select(post).from(post).where(booleanBuilder);
        return query.fetch();
    }


    @Transactional
    @Override
    public Post searchOne(Long postId) {
        updateViews(postId);
        Post post = findPostById(postId);
        log.info("Found post: " + post);
        return post;
    }

    private Post findPostById(Long postId) {
        QPost qPost = QPost.post;
        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        Post result = query.select(qPost)
                .from(qPost)
                .where(qPost.postId.eq(postId))
                .fetchOne();
        if (result == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId);
        }
        log.info("Found post By Id: " + result);

        return result;
    }



    @Transactional
    public void updateViews(Long postId) {
        // 조회수 증가 로직
        QPost post = QPost.post;
        long executed = queryFactory.update(post)
                .set(post.views, post.views.add(1))
                .where(post.postId.eq(postId))
                .execute();
        log.info(executed + " views updated");
    }

    @Override
    public boolean deleteOne(Long postId, Long memberId) {
        // postId로 게시글을 조회하고 memberId로 소유권을 확인
        QPost qpost = QPost.post;
        Post post = new JPAQuery<>(entityManager)
                .select(qpost)
                .from(qpost)
                .where(qpost.postId.eq(postId)
                        .and(qpost.member.memberId.eq(memberId))) // 게시글의 memberId가 입력 memberId와 일치하는지 확인
                .fetchOne();

        // 게시글이 존재하고 사용자가 삭제 권한을 가지고 있는지 확인
        if (post != null) {
            entityManager.remove(post);
            entityManager.flush();
            log.info("게시글 삭제 완료!! : " + post);
            return true;  // 성공적으로 삭제됨
        }
        return false;  // 삭제 실패 (게시글이 존재하지 않거나 권한 없음)
    }
}