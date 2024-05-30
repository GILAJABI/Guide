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
import java.time.LocalDateTime;
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

    // 세션 ID 확인
    public Member loginCheck(HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
    }

    // 거래 게시판 글 등록
    @Override
    public void carrotRegister(CarrotDTO carrotDTO, MultipartFile file, HttpSession session) {

        Member member = loginCheck(session);

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

        Member member = loginCheck(session);

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

        Member member = loginCheck(session);
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
    public void carrotModify(CarrotDTO carrotDTO, MultipartFile file, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");

        // Carrot 엔티티 찾기
        Carrot carrot = carrotRepository.findById(carrotDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Carrot not found with id: " + carrotDTO.getPostId()));

        // 게시글 소유자 확인
        if (!carrot.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("Unauthorized attempt to modify a post not owned by memberId: " + memberId);
        }

        // Carrot 엔티티 업데이트
        carrot.change(
                carrotDTO.getTitle(),
                carrotDTO.getContent(),
                carrotDTO.getPrice()
        );
        carrot.changeLocation(carrotDTO.getLocationX(), carrotDTO.getLocationY());

        // 엔티티의 수정 시간 현재 시간으로 업데이트
        LocalDateTime now = LocalDateTime.now();
        carrot.changeDate(now);
        log.info("Updated Carrot Post ID: {}, Modification Date: {}, by Member ID: {}", carrot.getPostId(), now, memberId);


        // 기존 이미지 삭제 처리
        if (carrot.getPostImages() != null && !carrot.getPostImages().isEmpty()) {
            for (PostImage postImage : carrot.getPostImages()) {
                imageRepository.delete(postImage);
            }
            carrot.getPostImages().clear();
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setFileName(originalName);
        imageDTO.setUuid(uuid);
        imageDTO.setPostId(carrot.getId());

        PostImage postImage = modelMapper.map(imageDTO, PostImage.class);
        postImage.setPost(carrot);

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            imageRepository.save(postImage);
            carrot.getPostImages().add(postImage); // 새로운 이미지 추가
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }


        carrotRepository.save(carrot);
        log.info("Updated Carrot Modification Date: {}", carrot.getModifyDate());
        log.info("Updated Carrot Post ID: {}, by Member ID: {}, by locationX : {}, by locationY : {}", carrot.getPostId(), memberId, carrotDTO.getLocationX(), carrotDTO.getLocationY());
    }


    @Override
    public void reviewModify(ReviewDTO reviewDTO, MultipartFile file, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");
        if (memberId == null) {
            throw new RuntimeException("Member ID not found in session");
        }

        Review review = reviewRepository.findById(reviewDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Carrot not found with id: " + reviewDTO.getPostId()));

        // 게시글 소유자 확인
        if (!review.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("Unauthorized attempt to modify a post not owned by memberId: " + memberId);
        }

        log.info("Before reviewDTO Start : " + reviewDTO.getStartTravelDate());
        log.info("Before reviewDTO End : " + reviewDTO.getEndTravelDate());
        review.change(
                reviewDTO.getTitle(),
                reviewDTO.getContent(),
                reviewDTO.getExpense(),
                reviewDTO.getGrade(),
                reviewDTO.getStartTravelDate(),
                reviewDTO.getEndTravelDate()
        );
        review.changeLocation(reviewDTO.getLocationX(), reviewDTO.getLocationY());

        // 엔티티의 수정 시간 현재 시간으로 업데이트
        LocalDateTime now = LocalDateTime.now();
        review.changeDate(now);
        log.info("Updated Review Post ID: {}, Modification Date: {}, by Member ID: {}", review.getPostId(), now, memberId);

        // 기존 이미지 삭제 처리
        if (review.getPostImages() != null && !review.getPostImages().isEmpty()) {
            for (PostImage postImage : review.getPostImages()) {
                imageRepository.delete(postImage);
            }
            review.getPostImages().clear();
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setFileName(originalName);
        imageDTO.setUuid(uuid);
        imageDTO.setPostId(review.getId());

        PostImage postImage = modelMapper.map(imageDTO, PostImage.class);
        postImage.setPost(review);

        reviewDTO.setStartTravelDate(reviewDTO.getStartTravelDate());
        reviewDTO.setEndTravelDate(reviewDTO.getEndTravelDate());
        log.info("After reviewDTO Start : " + reviewDTO.getStartTravelDate());
        log.info("After reviewDTO End : " + reviewDTO.getEndTravelDate());

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            imageRepository.save(postImage);
            review.getPostImages().add(postImage); // 새로운 이미지 추가
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }


        reviewRepository.save(review);
        log.info("Updated Review Modification Date: {}", review.getModifyDate());
        log.info("Updated Join Post ID: {}, by StartDate: {}, by EndDate: {}\"", review.getPostId(), reviewDTO.getStartTravelDate(), reviewDTO.getEndTravelDate());
    }

    @Override
    public void joinModify(JoinDTO joinDTO, MultipartFile file, HttpSession session) {
        Long memberId = (Long) session.getAttribute("member_id");
        if (memberId == null) {
            throw new RuntimeException("Member ID not found in session");
        }

        Join join = joinRepository.findById(joinDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Join not found with id: " + joinDTO.getPostId()));

        log.info("Before Join Post ID: {}, by StartDate: {}, by EndDate: {}\"", join.getPostId(), joinDTO.getStartTravelDate(), joinDTO.getEndTravelDate());
        log.info("Get LocationX {} || LocationY{}", joinDTO.getLocationX(), joinDTO.getLocationY());
        // 게시글 소유자 확인
        if (!join.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("Unauthorized attempt to modify a post not owned by memberId: " + memberId);
        }

        join.change(
                joinDTO.getTitle(),
                joinDTO.getContent(),
                joinDTO.getExpense(),
                joinDTO.getNumPeople(),
                joinDTO.getStartTravelDate(),
                joinDTO.getEndTravelDate()
        );
        joinRepository.save(join);

        // 엔티티의 수정 시간 현재 시간으로 업데이트
        LocalDateTime now = LocalDateTime.now();
        join.changeDate(now);

        join.changeLocation(joinDTO.getLocationX(), joinDTO.getLocationY());

        // 기존 이미지 삭제 처리
        if (join.getPostImages() != null && !join.getPostImages().isEmpty()) {
            for (PostImage postImage : join.getPostImages()) {
                imageRepository.delete(postImage);
            }
            join.getPostImages().clear();
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setFileName(originalName);
        imageDTO.setUuid(uuid);
        imageDTO.setPostId(join.getId());

        PostImage postImage = modelMapper.map(imageDTO, PostImage.class);
        postImage.setPost(join);

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            imageRepository.save(postImage);
            join.getPostImages().add(postImage); // 새로운 이미지 추가
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }


        joinRepository.save(join);
        log.info("Updated Join Modification Date: {}", join.getModifyDate());
        // 현재 시작, 종료날짜에 대한 값이 갱신이 되지않음.
        log.info("Updated Join Post ID: {}, by Member ID: {}, by StartDate: {}, by EndDate: {}\"", join.getPostId(), join.getPostId(), joinDTO.getStartTravelDate(), joinDTO.getEndTravelDate());
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
        reviewDTO.getStartTravelDate();
        reviewDTO.getEndTravelDate();
        log.info("reviewDTO Start : " + reviewDTO.getStartTravelDate());
        log.info("reviewDTO End : " + reviewDTO.getEndTravelDate());
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
        log.info("joinDTO Start : " + joinDTO.getStartTravelDate());
        log.info("joinDTO End : " + joinDTO.getEndTravelDate());
        return joinDTO;
    }

    @Override
    public String getPostType(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        Post post = result.orElse(null);
        assert post != null;
        log.info("!!!!!@!!!!" + post.toString());
        return post.getPostType();
    }
}