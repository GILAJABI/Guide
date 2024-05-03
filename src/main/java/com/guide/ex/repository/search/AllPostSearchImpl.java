package com.guide.ex.repository.search;

import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
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

        JPQLQuery<Post> query = from(post);

        query.where(post.postType.contains(postType));

        List<Post> posts = query.fetch();

        return queryFactory.selectFrom(post)
                .where(post.postType.contains(postType))
                .fetch();
    }

    @Override
    public Page<Post> searchPostPaging(String postType, Pageable pageable) {
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

    public List<Post> searchPostContaining(String searchValue) {
        QPost post = QPost.post;
        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(post.title.contains(searchValue));

        booleanBuilder.or(post.content.contains(searchValue));

        query.select(post).from(post).where(booleanBuilder);

        return query.fetch();
    }

//    private BooleanExpression eqMemberId(Long memberId) {
//        if(memberId != null) {
//            return null;
//        }
//        return post.memberId.eq(String.valueOf(memberId));
//    }

//    private BooleanExpression eqPostContent(String postContent) {
//        if(StringUtils.isEmpty(postContent)) {
//            return null;
//        }
//        return post.content.containsIgnoreCase(postContent);
//    }
//
//    private BooleanExpression eqTitle(String postTitle) {
//        if(StringUtils.isEmpty(postTitle)) {
//            return null;
//        }
//        return post.title.containsIgnoreCase(postTitle);
//    }
}
