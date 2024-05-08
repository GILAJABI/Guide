package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.post.*;
import com.guide.ex.dto.post.*;
import com.guide.ex.repository.member.MemberRepository;
import com.guide.ex.repository.post.*;
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
    private final AllPostSearchImpl allPostSearch;
    private final ImageRepository imageRepository;


    @Override
    public void carrotRegister(PostDTO postDTO, CarrotDTO carrotDTO, ImageDTO imageDTO) {
        Member member = memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + postDTO.getMemberId()));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setMember(member);
        postRepository.save(post); // Post 저장
        log.info("Saved Carrot Post ID: {}", post.getId()); // 저장된 Post의 ID 로깅


        carrotDTO.setPostId(post.getId()); // 저장된 Post의 ID를 가져와서 설정
        Carrot carrot = modelMapper.map(carrotDTO, Carrot.class);
        carrotRepository.save(carrot); // Carrot 저장

        PostImage postImage = PostImage.builder()
                .fileName(imageDTO.getFileName())
                .ord(imageDTO.getOrd())
                .uuid(imageDTO.getUuid())
                .post(post) // 연결된 Post 객체
                .build();

        imageRepository.save(postImage);
        log.info("Saved Image ID: {}", postImage.getImageId());
        log.info("Image uploaded with UUID: {} for Post ID: {}", imageDTO.getUuid(), postDTO.getPostId());
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

    @Override
    public void carrotModify(PostDTO postDTO, CarrotDTO carrotDTO) {
        // Post 엔티티 찾기
        Post post = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id : " + postDTO.getPostId()));

        // 게시글 소유자 확인
        if (!post.getMember().getMemberId().equals(postDTO.getMemberId())) {
            throw new RuntimeException("Unauthorized attempt to modify a post not owned by memberId: " + postDTO.getMemberId());
        }

        post.change(
                postDTO.getTitle(),
                postDTO.getContent()
        );

        post.changeDate(
                postDTO.getModifyDate()
        );

        // 변경사항 저장
        postRepository.save(post);

        // Carrot 엔티티 찾기
        Carrot carrot = carrotRepository.findById(carrotDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Carrot post not found with id : " + carrotDTO.getPostId()));

        // Carrot 엔티티 업데이트
        carrot.change(carrotDTO.getPrice());

        // 변경사항 저장
        carrotRepository.save(carrot);

        log.info("Updated Post ID: {}, Carrot Post ID: {}, by Member ID: {}", post.getPostId(), carrot.getPostId(), postDTO.getMemberId());
    }

    @Override
    public void reviewModify(PostDTO postDTO, ReviewDTO reviewDTO) {
        // Post 엔티티 찾기
        Post post = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id : " + postDTO.getPostId()));

        // 게시글 소유자 확인
        if (!post.getMember().getMemberId().equals(postDTO.getMemberId())) {
            throw new RuntimeException("Unauthorized attempt to modify a post not owned by memberId: " + postDTO.getMemberId());
        }
        post.change(
                postDTO.getTitle(),
                postDTO.getContent()
        );
        post.changeDate(
                postDTO.getModifyDate()
        );

        // 변경사항 저장
        postRepository.save(post);

        // Carrot 엔티티 찾기
        Review review = reviewRepository.findById(reviewDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Carrot post not found with id : " + reviewDTO.getPostId()));

        // Carrot 엔티티 업데이트
        review.change(reviewDTO.getExpense(),
                reviewDTO.getGrade(),
                reviewDTO.getStartTravelDate(),
                reviewDTO.getEndTravelDate());

        // 변경사항 저장
        reviewRepository.save(review);

        log.info("Updated Post ID: {}, Review Post ID: {}, by Member ID: {}", post.getPostId(), review.getPostId(), postDTO.getMemberId());
    }

    @Override
    public void joinModify(PostDTO postDTO, JoinDTO joinDTO) {
        // Post 엔티티 찾기
        Post post = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id : " + postDTO.getPostId()));

        // 게시글 소유자 확인
        if (!post.getMember().getMemberId().equals(postDTO.getMemberId())) {
            throw new RuntimeException("Unauthorized attempt to modify a post not owned by memberId: " + postDTO.getMemberId());
        }

        post.change(
                postDTO.getTitle(),
                postDTO.getContent()
        );

        post.changeDate(
                postDTO.getModifyDate()
        );

        // 변경사항 저장
        postRepository.save(post);

        // Carrot 엔티티 찾기
        Join join = joinRepository.findById(joinDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Carrot post not found with id : " + joinDTO.getPostId()));

        // Carrot 엔티티 업데이트
        join.change(
                joinDTO.getExpense(),
                joinDTO.getNumPeople(),
                joinDTO.getStartTravelDate(),
                joinDTO.getEndTravelDate());

        // 변경사항 저장
        joinRepository.save(join);

        log.info("Updated Post ID: {}, Review Post ID: {}, by Member ID: {}", post.getPostId(), join.getPostId(), postDTO.getMemberId());
    }
}