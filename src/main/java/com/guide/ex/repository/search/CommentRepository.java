package com.guide.ex.repository.search;

import com.guide.ex.domain.post.Comment;
import com.guide.ex.dto.post.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    Page<Comment> findByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.member.id = :memberId")
    Page<Comment> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.member.id = :memberId")
    Page<Comment> findByPostIdAndMemberId(@Param("postId") Long postId, @Param("memberId") Long memberId, Pageable pageable);

}
