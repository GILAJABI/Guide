package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.post.Carrot;
import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.repository.member.MemberRepository;
import com.guide.ex.repository.post.PostRepository;
import com.guide.ex.repository.search.AllPostSearchImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final AllPostSearchImpl allPostSearchImpl;
    private final MemberRepository memberRepository;


    @Override
    public boolean commonTask(PostDTO postDTO) {
        Member member =  memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id : " + postDTO.getMemberId()));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setMember(member);

        // 게시글 작성 시 postType으로 게시판 나눠 -> [회원 ID가 not null -> 해당 회원ID가 is_Ban == false이어야 게시글 작성 가능]
        // 게시글 수정/삭제 시 postType으로 게시판 나눠 -> 회원 ID가 postId가 포함되있어야됨 -> is_Ban == false여야 됨
        // memberId가 존재할 경우, member.isBan == false, PostId가


        if(!member.isBan()) {
            postRepository.save(post);
            return true;
        }
        return false;

    }
}