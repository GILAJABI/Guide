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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Value("${com.guide.upload.path}")
    private String uploadPath;

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CarrotRepository carrotRepository;
    private final ReviewRepository reviewRepository;
    private final JoinRepository joinRepository;
    private final AllPostSearchImpl allPostSearch;
    private final ImageRepository imageRepository;

    @Override
    public void carrotRegister(CarrotDTO carrotDTO, MultipartFile file, HttpSession session) {

        Long memberId = (Long) session.getAttribute("member_id");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));


        Carrot carrot = modelMapper.map(carrotDTO, Carrot.class);
        carrot.setMember(member);
        carrotRepository.save(carrot);

        ImageDTO imageDTO = new ImageDTO();
        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        imageDTO.setFileName(originalName);
        imageDTO.setUuid(uuid);
        imageDTO.setPostId(carrot.getId());
        PostImage postImage = modelMapper.map(imageDTO, PostImage.class);
        postImage.setPost(carrot);

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            imageRepository.save(postImage);
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }
    }

    @Override
    public void joinRegister(JoinDTO joinDTO, MultipartFile file, HttpSession session) {

        Long memberId = (Long) session.getAttribute("member_id");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        Join join = modelMapper.map(joinDTO, Join.class);
        join.setMember(member);
        joinRepository.save(join);

        ImageDTO imageDTO = new ImageDTO();
        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        imageDTO.setFileName(originalName);
        imageDTO.setUuid(uuid);
        imageDTO.setPostId(join.getId());
        PostImage postImage = modelMapper.map(imageDTO, PostImage.class);
        postImage.setPost(join);

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            imageRepository.save(postImage);
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }
    }

    @Override
    public void reviewRegister(ReviewDTO reviewDTO, MultipartFile file, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));


        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setMember(member);
        reviewRepository.save(review); // Review 저장

        ImageDTO imageDTO = new ImageDTO();
        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        imageDTO.setFileName(originalName);
        imageDTO.setUuid(uuid);
        imageDTO.setPostId(review.getId());
        PostImage postImage = modelMapper.map(imageDTO, PostImage.class);
        postImage.setPost(review);

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            imageRepository.save(postImage);
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }

    }

    @Override
    public void carrotModify(PostDTO postDTO, CarrotDTO carrotDTO, ImageDTO imageDTO) {
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
    public void reviewModify(PostDTO postDTO, ReviewDTO reviewDTO, ImageDTO imageDTO) {
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
    public void joinModify(PostDTO postDTO, JoinDTO joinDTO, ImageDTO imageDTO) {
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

    @Override
    public void postDetailRead(Long postId, String postType) {  // 게시글 상세 검색(Service -> Repository)
        // 데이터베이스에서 Post 객체를 검색
        Post post = allPostSearch.searchOne(postId, postType);
        if (post == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId);
        }
    }

//    @Override
//    public Page<CarrotDTO> postTypeReadAll(String postType, int size, int page) { // 게시판 목록, 메인 -> 각 게시판(Service -> Repository)
//        Page<Carrot> postPage = allPostSearch.searchPostPaging(size, page);
//        return postPage.map(post -> modelMapper.map(post, PostDTO.class));
//
//        return  null;
//
//    }

    @Override
    public List<PostDTO> postSelectAll(String searchValue, String postType) {
        if (searchValue == null || searchValue.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 값이 제공되지 않았습니다.");
        }
        if (postType == null || postType.trim().isEmpty()) {
            throw new IllegalArgumentException("게시판 유형이 제공되지 않았습니다.");
        }

        List<String> validPostTypes = Arrays.asList("Review", "Carrot", "Join");
        if (!validPostTypes.contains(postType)) {
            throw new IllegalArgumentException("유효하지 않은 게시판 유형입니다: " + postType);
        }
        List<Post> posts = allPostSearch.searchPostContaining(searchValue, postType);
        // 엔티티를 DTO로 변환
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CarrotDTO> carrotTypeReadAll(int size, int page) {
        Page<Carrot> postPage = allPostSearch.searchCarrotPaging(size, page);
        return postPage.map(post -> modelMapper.map(post, CarrotDTO.class));
    }

    @Override
    public Page<ReviewDTO> reviewTypeReadAll(String searchValue, String postType, Pageable pageable) {
        return null;
    }

    @Override
    public Page<JoinDTO> joinTypeReadAll(String searchValue, String postType, Pageable pageable) {
        return null;
    }


}