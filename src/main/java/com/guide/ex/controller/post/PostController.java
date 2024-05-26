package com.guide.ex.controller.post;

import com.guide.ex.domain.post.Carrot;
import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.PageRequestDTO;
import com.guide.ex.dto.PageResponseDTO;
import com.guide.ex.dto.post.*;
import com.guide.ex.service.CommentService;
import com.guide.ex.service.MemberService;
import com.guide.ex.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Log4j2
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, MemberService memberService) {
        this.postService = postService;
        this.commentService = commentService;
        this.memberService = memberService;
    }

    // 게시글 작성
    @GetMapping({"/carrotWrite", "/reviewWrite", "/joinWrite"})
    public String writePost(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }

        // 요청 URL을 가져옵니다.
        String requestURI = request.getRequestURI();

        // URL에 따라 적절한 뷰 이름을 반환합니다.
        if (requestURI.contains("carrotWrite")) {
            return "post/carrotWrite";
        } else if (requestURI.contains("reviewWrite")) {
            return "post/reviewWrite";
        } else if (requestURI.contains("joinWrite")) {
            return "post/joinWrite";
        }

        // 기본적으로는 메인 페이지로 리다이렉트
        return "redirect:/";
    }

    @PostMapping("/carrotWrite")
    public String carrotWriteInput(HttpSession session, CarrotDTO carrotDTO, @RequestParam("file") MultipartFile file) {
        postService.carrotRegister(carrotDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/carrotMain";
    }

    @PostMapping("/reviewWrite")
    public String reviewWriteInput(HttpSession session, ReviewDTO reviewDTO, @RequestParam("file") MultipartFile file) {
        postService.reviewRegister(reviewDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/reviewMain";
    }

    @PostMapping("/joinWrite")
    public String joinWriteInput(HttpSession session, JoinDTO joinDTO, @RequestParam("file") MultipartFile file) {
        postService.joinRegister(joinDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/joinMain";
    }

    // 게시글 상세검색
    @GetMapping({"/carrotMain/search", "/reviewMain/search", "/joinMain/search"})
    public String searchPost(@RequestParam("searchValue") String searchValue,
                             @RequestParam("postType") String postType,
                             @RequestParam(defaultValue = "registerDate") String sort,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size,
                             HttpServletRequest request, Model model) {

        Sort sorting = Sort.by(Sort.Direction.DESC, sort);
        Pageable pageable = PageRequest.of(page - 1, size, sorting);
        Page<PostDTO> posts = postService.postSelectAll(searchValue, postType, pageable);

        String requestURI = request.getRequestURI();

        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("postType", postType);

        // URL에 따라 적절한 뷰 이름을 반환합니다.
        if (requestURI.contains("carrotMain")) {
            return "post/carrotSearch";
        } else if (requestURI.contains("reviewMain")) {
            return "post/reviewSearch";
        } else if (requestURI.contains("joinMain")) {
            return "post/joinSearch";
        }

        return "redirect:/";
    }

    @GetMapping("/carrotModify/{postId}")
    public String showModificationForm(@PathVariable Long postId, Model model) {
        CarrotDTO post = postService.postCarrotRead(postId);
        model.addAttribute("post", post);
        return "post/carrotModify";  // 뷰 이름을 반환
    }

    @PostMapping("/carrotModify")
    public String carrotModify(HttpSession session, CarrotDTO carrotDTO, @RequestParam("file") MultipartFile file) {
        postService.carrotRegister(carrotDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/carrotMain";
    }



    // 게시글 삭제
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId, @RequestParam Long memberId, RedirectAttributes redirectAttributes) {
        try {
            if (postService.deletePost(postId, memberId)) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 삭제되었습니다.");
                return "redirect:/post/carrotMain";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패하였습니다.");
                return "redirect:/post/carrotMain";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류 발생: " + e.getMessage());
            return "redirect:/error-page";
        }
    }

    // 메인
    @GetMapping("/carrotMain")
    public String carrotMain(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size,
                             @RequestParam(defaultValue = "registerDate") String sort,
                             Model model) {
        Page<CarrotDTO> posts = postService.carrotTypeReadAll(size, page, Sort.by(Sort.Direction.DESC, sort));
        log.info("Carrot posts fetched: Total elements={}, Total pages={}, Current page index={}, Content !!{}",
                posts.getTotalElements(), posts.getTotalPages(), posts.getNumber(), posts.getContent());

        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        return "post/carrotMain";  // View name for Thymeleaf template
    }

    // 게시글 상세보기
    @GetMapping({"/carrotDetail/{postId}", "/reviewDetail/{postId}", "/joinDetail/{postId}"})
    public String detailRead(@PathVariable Long postId, Model model) {
        // postId를 사용하여 게시글의 유형을 가져옵니다.
        String type = postService.getPostType(postId);
        System.out.println("Typesss : " + type);

        // 유형에 따라 다른 서비스 메서드를 호출하여 모델에 추가합니다.
        switch (type) {
            case "Carrot":
                model.addAttribute("post", postService.postCarrotRead(postId));
                return "post/carrotDetail"; // 템플릿 경로 반환
            case "Review":
                model.addAttribute("post", postService.postReadReview(postId));
                return "post/reviewDetail"; // 템플릿 경로 반환
            case "Join":
                model.addAttribute("post", postService.postReadJoin(postId));
                return "post/joinDetail"; // 템플릿 경로 반환
            default:
                throw new IllegalArgumentException("Invalid post type: " + type);
        }
    }

    // 조회순으로 게시글 확인
    @GetMapping("/carrotMain/view")
    public String carrotView(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size,
                             Model model) {
        return carrotMain(page, size, "views", model);
    }

    // 댓글 갯수로 게시글 확인
    @GetMapping("/carrotMain/comments")
    public String carrotCommentCount(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "6") int size, Model model) {
        return carrotMain(page, size, "comments", model);
    }

    @GetMapping("/reviewMain")
    public String reviewMain(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size,
                             @RequestParam(defaultValue = "registerDate") String sort,
                             Model model) {
        Page<ReviewDTO> posts = postService.reviewTypeReadAll(size, page, Sort.by(Sort.Direction.DESC, sort));
        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        return "post/reviewMain";
    }

    @GetMapping("/reviewMain/view")
    public String reviewView(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size, Model model) {
        return reviewMain(page, size, "views", model);
    }

    @GetMapping("/reviewMain/comments")
    public String reviewCommentCount(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "6") int size, Model model) {
        return reviewMain(page, size, "comments", model);
    }

    @GetMapping("/joinMain")
    public String joinMain(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "6") int size,
                           @RequestParam(defaultValue = "registerDate") String sort,
                           Model model) {
        Page<JoinDTO> posts = postService.joinTypeReadAll(size, page, Sort.by(Sort.Direction.DESC, sort));
        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        return "post/joinMain";
    }

    @GetMapping("/joinMain/view")
    public String joinView(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "6") int size, Model model) {
        return joinMain(page, size, "views", model);
    }

    @GetMapping("/joinMain/comments")
    public String joinCommentCount(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "6") int size, Model model) {
        return joinMain(page, size, "comments", model);
    }

    @PostMapping("/comment") // 요청 경로 수정
    public String addComment(HttpSession session,
                             @RequestParam("commentContent") String commentContent,
                             @RequestParam("postId") Long postId) {

        Long memberId = (Long) session.getAttribute("member_id");

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContent(commentContent);
        commentDTO.setPostId(postId);
        commentDTO.setMemberId(memberId);

        commentService.register(commentDTO);
        memberService.updateCommentCount(memberId);
        postService.updatePostCommentCount(postId);

        String postType = postService.findPostTypeByPostId(postId);

        switch (postType) {
            case "Carrot":
                return "redirect:/post/carrotDetail/" + commentDTO.getPostId();
            case "Join":
                return "redirect:/post/joinDetail/" + commentDTO.getPostId();
            case "Review":
                return "redirect:/post/reviewDetail/" + commentDTO.getPostId();
            default:
                return "redirect:/post/carrotMain";
        }
    }

    @GetMapping("/comment")
    @ResponseBody
    public PageResponseDTO<CommentDTO> getComments(@RequestParam("postId") Long postId, PageRequestDTO requestDTO) {
        PageResponseDTO<CommentDTO> comments = commentService.getListOfPost(postId, requestDTO);
        return comments;
    }

}