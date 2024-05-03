package com.guide.ex.repository.search;

import com.guide.ex.domain.Post;
import com.guide.ex.domain.QPost;
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

    public AllPostSearchImpl(EntityManager entityManager) {
        super(Post.class);
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Page<Post> AllPostSearch(String[] types, String keyword, Pageable pageable) {
        return null;
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
}
