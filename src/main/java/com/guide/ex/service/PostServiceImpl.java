package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.post.*;
import com.guide.ex.dto.post.*;
import com.guide.ex.repository.member.MemberRepository;
import com.guide.ex.repository.post.*;
import com.guide.ex.repository.search.AllPostSearchImpl;
import com.guide.ex.repository.search.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
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
    private final CommentRepository commentRepository;


    // 거래 게시판 글 등록
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

    // 거래 게시글 수정
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


    // 게시글 검색(Service -> Repository)
    @Override
    public Page<PostDTO> postSelectAll(String searchValue, String postType, Pageable pageable) {
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

        Page<Post> postPage = allPostSearch.searchPostContaining(searchValue, postType, pageable);

        return postPage.map(post -> {
            PostDTO postDTO = createPostDTO(post, postType);
            List<ImageDTO> imageDTOs = convertImagesToDTOs(post.getPostImages());

            postDTO.setImageDTOs(imageDTOs);
            return postDTO;
        });
    }

    private PostDTO createPostDTO(Post post, String postType) {
        switch (postType) {
            case "Review":
                return modelMapper.map(post, ReviewDTO.class);
            case "Carrot":
                return modelMapper.map(post, CarrotDTO.class);
            case "Join":
                return modelMapper.map(post, JoinDTO.class);
            default:
                return modelMapper.map(post, PostDTO.class);
        }
    }

    private List<ImageDTO> convertImagesToDTOs(List<PostImage> images) {
        List<ImageDTO> imageDTOs = images.stream()
                .map(image -> {
                    ImageDTO imgDTO = modelMapper.map(image, ImageDTO.class);
                    log.info("ImageDTO: " + imgDTO);  // Log each ImageDTO
                    return imgDTO;
                })
                .collect(Collectors.toList());

        if (imageDTOs.isEmpty() || imageDTOs.contains(null)) {
            log.warn("No images found or null images present");
        } else {
            log.info("Number of images: " + imageDTOs.size());
        }

        return imageDTOs;
    }


    // 거래 게시판 글 모두 출력(Service -> Repository)
    @Override
    public Page<CarrotDTO> carrotTypeReadAll(int size, int page, Sort sort) {
        Page<Carrot> postPage = allPostSearch.searchCarrotPaging(size, page, sort);

        return postPage.map(carrot -> {
            CarrotDTO carrotDTO = modelMapper.map(carrot, CarrotDTO.class);
            List<ImageDTO> imageDTOs = carrot.getPostImages()
                    .stream()
                    .map(image -> {
                        ImageDTO imgDTO = modelMapper.map(image, ImageDTO.class);
                        log.info("ImageDTO: " + imgDTO);  // Log each ImageDTO
                        return imgDTO;
                    })
                    .collect(Collectors.toList());

            // Check if imageDTOs list is empty or contains null elements
            if (imageDTOs.isEmpty() || imageDTOs.contains(null)) {
                log.warn("No images found for Carrot ID " + carrot.getId());
            } else {
                log.info("Number of images for Carrot ID " + carrot.getId() + ": " + imageDTOs.size());
            }

            carrotDTO.setImageDTOs(imageDTOs);
            return carrotDTO;
        });
    }


    @Override
    public Page<ReviewDTO> reviewTypeReadAll(int size, int page, Sort sort) {
        Page<Review> postPage = allPostSearch.searchReviewPaging(size, page, sort);

        return postPage.map(review -> {
            ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
            List<ImageDTO> imageDTOs = review.getPostImages()
                    .stream()
                    .map(image -> {
                        ImageDTO imgDTO = modelMapper.map(image, ImageDTO.class);
                        log.info("ImageDTO: " + imgDTO);  // Log each ImageDTO
                        return imgDTO;
                    })
                    .collect(Collectors.toList());

            // Check if imageDTOs list is empty or contains null elements
            if (imageDTOs.isEmpty() || imageDTOs.contains(null)) {
                log.warn("No images found for Carrot ID " + review.getId());
            } else {
                log.info("Number of images for Carrot ID " + review.getId() + ": " + imageDTOs.size());
            }

            reviewDTO.setImageDTOs(imageDTOs);
            return reviewDTO;
        });
    }

    @Override
    public Page<JoinDTO> joinTypeReadAll(int size, int page, Sort sort) {
        Page<Join> postPage = allPostSearch.searchJoinPaging(size, page, sort);

        return postPage.map(join -> {
            JoinDTO joinDTO = modelMapper.map(join, JoinDTO.class);
            List<ImageDTO> imageDTOs = join.getPostImages()
                    .stream()
                    .map(image -> {
                        ImageDTO imgDTO = modelMapper.map(image, ImageDTO.class);
                        log.info("ImageDTO: " + imgDTO);  // Log each ImageDTO
                        return imgDTO;
                    })
                    .collect(Collectors.toList());

            // Check if imageDTOs list is empty or contains null elements
            if (imageDTOs.isEmpty() || imageDTOs.contains(null)) {
                log.warn("No images found for Carrot ID " + join.getId());
            } else {
                log.info("Number of images for Carrot ID " + join.getId() + ": " + imageDTOs.size());
            }

            joinDTO.setImageDTOs(imageDTOs);
            return joinDTO;
        });
    }

    // 게시글 삭제(Service -> Repository) + (회원 ID && postID) 일치할 경우만
    @Override
    public boolean deletePost(Long postId, Long memberId) {
        return allPostSearch.deleteOne(postId, memberId);
    }

    @Override
    public String findPostTypeByPostId(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        Post post = result.orElseThrow(() -> new NoSuchElementException("해당하는 게시물을 찾을 수 없습니다."));
        return post.getPostType();
    }

    @Override
    public void updatePostCommentCount(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        Post post = result.orElseThrow(() -> new NoSuchElementException("해당하는 게시물을 찾을 수 없습니다."));
        int commentCount = commentRepository.countByPost(post);
        post.setCommentCount(commentCount);
        postRepository.save(post);
    }

    @Override
    public CarrotDTO postCarrotRead(Long postId) {
        // 데이터베이스에서 Post 객체를 검색
        Post post = allPostSearch.searchOne(postId);
        if (post == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId);
        }

        CarrotDTO carrotDTO = modelMapper.map(post, CarrotDTO.class);
        List<ImageDTO> imageDTOS = post.getPostImages().stream()
                .map(image -> new ImageDTO(image.getImageId(), image.getUuid(), image.getFileName()))
                .collect(Collectors.toList());
        carrotDTO.setImageDTOs(imageDTOS);
        carrotDTO.setMemberId(post.getMember().getMemberId());
        carrotDTO.setMemberName(post.getMember().getName());
        return carrotDTO;
    }


    @Override
    public ReviewDTO postReadReview(Long postId) {
        // 데이터베이스에서 Post 객체를 검색
        Post post = allPostSearch.searchOne(postId);
        if (post == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId);
        }

        ReviewDTO reviewDTO = modelMapper.map(post, ReviewDTO.class);
        List<ImageDTO> imageDTOS = post.getPostImages().stream()
                .map(image -> new ImageDTO(image.getImageId(), image.getUuid(), image.getFileName()))
                .collect(Collectors.toList());
        reviewDTO.setImageDTOs(imageDTOS);
        reviewDTO.setMemberId(post.getMember().getMemberId());
        reviewDTO.setMemberName(post.getMember().getName());
        return reviewDTO;
    }

    @Override
    public JoinDTO postReadJoin(Long postId) {
        // 데이터베이스에서 Post 객체를 검색
        Post post = allPostSearch.searchOne(postId);
        if (post == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId);
        }

        JoinDTO joinDTO = modelMapper.map(post, JoinDTO.class);
        List<ImageDTO> imageDTOS = post.getPostImages().stream()
                .map(image -> new ImageDTO(image.getImageId(), image.getUuid(), image.getFileName()))
                .collect(Collectors.toList());
        joinDTO.setImageDTOs(imageDTOS);
        joinDTO.setMemberId(post.getMember().getMemberId());
        joinDTO.setMemberName(post.getMember().getName());
        return joinDTO;
    }

    @Override
    public String getPostType(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        Post post = result.orElse(null);
        assert post != null;
        log.info("!!!!!@!!!!"+post.toString());
        return post.getPostType();
    }
}