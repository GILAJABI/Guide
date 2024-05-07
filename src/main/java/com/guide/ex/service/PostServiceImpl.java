package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.post.Carrot;
import com.guide.ex.domain.post.Join;
import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.Review;
import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.JoinDTO;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.dto.post.ReviewDTO;
import com.guide.ex.repository.member.MemberRepository;
import com.guide.ex.repository.post.CarrotRepository;
import com.guide.ex.repository.post.JoinRepository;
import com.guide.ex.repository.post.PostRepository;
import com.guide.ex.repository.post.ReviewRepository;
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
    private final MemberRepository memberRepository;
    private final CarrotRepository carrotRepository;
    private final ReviewRepository reviewRepository;
    private final JoinRepository joinRepository;


//    @Override
//    public boolean confirm(MemberDTO memberDTO) {
//        if(!memberDTO.isBan()) {
//            return true;
//        }
//        return false;
//
//        // 게시글 작성 시 postType으로 게시판 나눠 -> [회원 ID가 not null -> 해당 회원ID가 is_Ban == false이어야 게시글 작성 가능]
//        // 게시글 수정/삭제 시 postType으로 게시판 나눠 -> 회원 ID가 postId가 포함되있어야됨 -> is_Ban == false여야 됨
//        // memberId가 존재할 경우, member.isBan == false, PostId가
//    }

    @Override
    public void carrotRegister(PostDTO postDTO, CarrotDTO carrotDTO) {
        Member member = memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + postDTO.getMemberId()));
        Post post = modelMapper.map(postDTO, Post.class);
        post.setMember(member);
        postRepository.save(post); // Post 저장
        log.info("Saved Carrot Post ID: {}", post.getId()); // 저장된 Post의 ID 로깅


        carrotDTO.setPostId(post.getId()); // 저장된 Post의 ID를 가져와서 설정
        Carrot carrot = modelMapper.map(carrotDTO, Carrot.class);
        carrotRepository.save(carrot); // Carrot 저장
    }

    @Override
    public void reviewRegister(PostDTO postDTO, ReviewDTO reviewDTO) {
        Member member =  memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id : " + postDTO.getMemberId()));
        Post post = modelMapper.map(postDTO, Post.class);
        post.setMember(member);
        postRepository.save(post); // Post 저장
        log.info("Saved Review Post ID: {}", post.getId()); // 저장된 Post의 ID 로깅

        reviewDTO.setPostId(post.getId()); // 저장된 Post의 ID를 가져와서 설정
        Review review = modelMapper.map(reviewDTO, Review.class);
        reviewRepository.save(review); // Carrot 저장
    }

    @Override
    public void joinRegister(PostDTO postDTO, JoinDTO joinDTO) {

        Member member =  memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id : " + postDTO.getMemberId()));
        Post post = modelMapper.map(postDTO, Post.class);
        post.setMember(member);
        postRepository.save(post); // Post 저장
        log.info("Saved Join Review Post ID: {}", post.getId()); // 저장된 Post의 ID 로깅

        joinDTO.setPostId(post.getId()); // 저장된 Post의 ID를 가져와서 설정
        Join join = modelMapper.map(joinDTO, Join.class);
        joinRepository.save(join); // Carrot 저장
    }

}