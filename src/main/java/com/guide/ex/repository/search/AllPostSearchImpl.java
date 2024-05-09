package com.guide.ex.repository.search;

import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<Post> searchPostPaging(String postType, Pageable pageable) {    // 게시판 유형에 따른 모든 게시글 검색
        QPost post = QPost.post;

        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        query.from(post)
                .where(post.postType.contains(postType));

        long total = query.fetchCount();

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Post> posts = query.fetch();

        return new PageImpl<>(posts, pageable, total);
    }

    public List<Post> searchPostContaining(String searchValue, String postType) {    // 사용자가 입력한 제목 or 내용 검색 + 페이징 처리 + 특정 게시판 유형 필터
        QPost post = QPost.post;
        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 게시판 유형에 맞는 게시글만 필터링
        booleanBuilder.and(post.postType.eq(postType));
        if(searchValue != null && !searchValue.isEmpty()) {
            log.info("존재하지 않는 게시글입니다.");
        }

        // 제목이나 내용에 검색어를 포함하는 게시글 검색
        booleanBuilder.andAnyOf(
                post.title.contains(searchValue),
                post.content.contains(searchValue)
        );

        query.select(post).from(post).where(booleanBuilder);

        return query.fetch();
    }


//    public void searchReviewOne(Long postId) {
//        // 조회수 증가 -> 게시글 조회
//        updateViews(postId);
//        Post post = findPostById(postId, "Review");
//
//        // 변경된 게시글 정보를 다시 조회합니다.
//    }
//    public void searchCarrotOne(Long postId) {
//        // 조회수 증가 -> 게시글 조회
//        updateViews(postId);
//        Post post = findPostById(postId, "Carrot");
//
//        // 변경된 게시글 정보를 다시 조회합니다.
//    }
//    public void searchJoinOne(Long postId) {
//        // 조회수 증가 -> 게시글 조회
//        updateViews(postId);
//        Post post = findPostById(postId, "Join");
//
//        // 변경된 게시글 정보를 다시 조회합니다.
//    }

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



