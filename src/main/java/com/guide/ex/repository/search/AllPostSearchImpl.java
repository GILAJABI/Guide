package com.guide.ex.repository.search;

import com.guide.ex.domain.member.QMember;
import com.guide.ex.domain.post.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @Override
    public Page<Post> searchPostPaging(String postType, int size, int page) {    // 게시판 유형에 따른 모든 게시글 검색
        QPost post = QPost.post;

        Pageable pageable = PageRequest.of(page - 1, size);

        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        query.from(post)
                .where(post.postType.contains(postType));

        long total = query.fetchCount();

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Post> posts = query.fetch();

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<Carrot> searchCarrotPaging(int size, int page) {    // 당근 게시판 모든 게시글 검색
        QCarrot carrot = QCarrot.carrot; // QCarrot 인스턴스 생성
        QPostImage postImage = QPostImage.postImage; // PostImage의 QueryDSL 메타모델
        QMember member = QMember.member; // Member의 QueryDSL 메타모델

        Pageable pageable = PageRequest.of(page - 1, size);

        JPAQuery<Carrot> query = new JPAQuery<>(entityManager);
        query.from(carrot)
                .join(carrot.member, member).fetchJoin() // Member와 페치 조인
                .join(carrot.postImages, postImage).fetchJoin() // PostImage와 페치 조인
                .where(carrot.isDeleted.eq(false)) // 삭제되지 않은 Carrot 게시물 검색
                .where(carrot.postType.eq("Carrot")); // Carrot 타입만 검색

        log.info("!!!!!!!!!!!!!!!!! : " + query.fetchCount());
        log.info("@@@@@@@@@@@@ : " + query.fetchFirst().getPostImages());
        long total = query.fetchCount(); // 전체 아이템 수 가져오기

        List<Carrot> carrots = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // 페이지 정보 적용하여 데이터 가져오기

        return new PageImpl<>(carrots, pageable, total);
    }

    public List<Post> searchPostContaining(String searchValue, String postType) {    // 사용자가 입력한 제목 or 내용 검색 + 페이징 처리 + 특정 게시판 유형 필터
        QPost post = QPost.post;
        JPAQuery<Post> query = new JPAQuery<>(entityManager);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 게시판 유형에 맞는 게시글만 필터링
        booleanBuilder.and(post.postType.eq(postType));
        if (searchValue != null && !searchValue.trim().isEmpty()) {
            booleanBuilder.andAnyOf(
                    post.title.contains(searchValue),
                    post.content.contains(searchValue)
            );
        }

        query.select(post).from(post).where(booleanBuilder);
        return query.fetch();
    }


    @Transactional
    @Override
    public Post searchOne(Long postId, String postType) {
        updateViews(postId);
        Post post = findPostById(postId, postType);
        log.info("Found post: " + post);
        return post;
    }

    private Post findPostById(Long postId, String postType) {
        QPost qPost = QPost.post;
        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        Post result = query.select(qPost)
                .from(qPost)
                .where(qPost.postId.eq(postId)
                        .and(qPost.postType.eq(postType)))
                .fetchOne();

        if (result == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId);
        }

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
    }

}



