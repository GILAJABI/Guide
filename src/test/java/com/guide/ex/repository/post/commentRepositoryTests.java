package com.guide.ex.repository.post;


import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.post.Comment;
import com.guide.ex.domain.post.Post;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class commentRepositoryTests {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testInsert() {

        // 실제 DB에 있는 postId
        Long memberId = 2L;
        Long postId = 1L;

        Member member = Member.builder().memberId(memberId).build();
        Post post = Post.builder().postId(postId).build();

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content("댓글 insert 테스트")
                .build();
        commentRepository.save(comment);
    }
}
