package com.guide.ex.repository.post;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.post.Post;
import com.guide.ex.repository.search.AllPostSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, AllPostSearch {
    Optional<List<Post>> findAllByMember(Member member);
    Page<Post> findAllByMember(Member member, Pageable pageable);
    List<Post> findTop5ByMemberOrderByPostIdDesc(Member member);
}
