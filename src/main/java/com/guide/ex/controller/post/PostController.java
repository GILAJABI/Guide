package com.guide.ex.controller.post;

import com.guide.ex.domain.post.Post;
import com.guide.ex.domain.post.Review;
import com.guide.ex.dto.PageRequestDTO;
import com.guide.ex.dto.PageResponseDTO;
import com.guide.ex.dto.post.*;
import com.guide.ex.repository.search.CommentRepository;
import com.guide.ex.service.CommentService;
import com.guide.ex.service.MemberService;
import com.guide.ex.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentRepository commentRepository;


    @GetMapping("/carrotMain")
    public String carrotMain(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size,
                             @RequestParam(defaultValue = "registerDate") String sort) {
        Page<CarrotDTO> posts = postService.carrotTypeReadAll(size, page, Sort.by(Sort.Direction.DESC, sort));
        log.info("Carrot posts fetched: Total elements={}, Total pages={}, Current page index={}, Content !!{}",
                posts.getTotalElements(), posts.getTotalPages(), posts.getNumber(), posts.getContent());

        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        return "post/carrotMain";  // View name for Thymeleaf template
    }

    @GetMapping("/carrotMain/view")
    public String carrotView(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size) {
        return carrotMain(model, page, size, "views");
    }


    @GetMapping("/reviewMain")
    public String reviewMain(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size,
                             @RequestParam(defaultValue = "registerDate") String sort) {
        Page<ReviewDTO> posts = postService.reviewTypeReadAll(size, page, Sort.by(Sort.Direction.DESC, sort));
        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        return "post/reviewMain";
    }
    @GetMapping("/reviewMain/view")
    public String reviewView(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "6") int size) {
        return reviewMain(model, page, size, "views");
    }

    @GetMapping("/joinMain")
    public String joinMain(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "6") int size,
                           @RequestParam(defaultValue = "registerDate") String sort) {
        Page<JoinDTO> posts = postService.joinTypeReadAll(size, page, Sort.by(Sort.Direction.DESC, sort));
        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort);
        return "post/joinMain";
    }

    @GetMapping("/joinMain/view")
    public String joinView(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "6") int size) {
        return joinMain(model, page, size, "views");
    }


    //    검색기능
    @PostMapping("/carrotMain/search")
    public String searchValue(@RequestParam("searchValue") String searchValue,
                              @RequestParam("postType") String postType,
                              Model model) {
        switch (postType) {
            case "Carrot":
            case "Review":
            case "Join":
                postService.postSelectAll(searchValue, postType);
        }

        postService.postSelectAll(searchValue, postType);

        return "redirect:/post/carrotMain";
    }


    @GetMapping("/carrotDetail")
    public String carrotDetail(Model model, Long postId) {
        Post post = postService.postDetailRead(postId);

        System.out.println("-----------------------");
        System.out.println(post.getContent());
        System.out.println("-----------------------");
        log.info("postImage {}, postId {}", post.getPostImages(), post.getPostId());
        List<ImageDTO> imageDTOS = post.getPostImages().stream()
                .map(image -> new ImageDTO(image.getImageId(), image.getUuid(), image.getFileName()))
                .collect(Collectors.toList());


        model.addAttribute("post", post);
        model.addAttribute("imageDTOS", imageDTOS);

        return "post/carrotDetail";
    }

    @GetMapping("/reviewDetail")
    public String reviewDetail(Model model, Long postId) {
        Post post = postService.postDetailRead(postId);

        List<ImageDTO> imageDTOS = post.getPostImages().stream()
                .map(image -> new ImageDTO(image.getImageId(), image.getUuid(), image.getFileName()))
                .collect(Collectors.toList());


        model.addAttribute("post", post);
        model.addAttribute("imageDTOS", imageDTOS);

        return "post/reviewDetail";
    }

    @GetMapping("/joinDetail")
    public String joinDetail(Model model, Long postId) {
        Post post = postService.postDetailRead(postId);

        List<ImageDTO> imageDTOS = post.getPostImages().stream()
                .map(image -> new ImageDTO(image.getImageId(), image.getUuid(), image.getFileName()))
                .collect(Collectors.toList());


        model.addAttribute("post", post);
        model.addAttribute("imageDTOS", imageDTOS);

        return "post/joinDetail";
    }

    @GetMapping("/carrotWrite")
    public String carrotWrite(HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/post/carrotWrite";
    }

    @PostMapping("/carrotWrite")
    public String carrotWriteInput(HttpSession session, CarrotDTO carrotDTO, @RequestParam("file") MultipartFile file) {
        postService.carrotRegister(carrotDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/carrotMain";
    }

    @GetMapping("/joinWrite")
    public String joinWrite(HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/post/joinWrite";
    }

    @PostMapping("/joinWrite")
    public String joinWriteInput(HttpSession session, JoinDTO joinDTO, @RequestParam("file") MultipartFile file) {
        postService.joinRegister(joinDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/joinMain";
    }

    @GetMapping("/reviewWrite")
    public String reviewWrite(HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/post/reviewWrite";
    }

    @PostMapping("/reviewWrite")
    public String reviewWriteInput(HttpSession session, ReviewDTO reviewDTO, @RequestParam("file") MultipartFile file) {
        postService.reviewRegister(reviewDTO, file, session);
        memberService.updateBoardCount(session);
        return "redirect:/post/reviewMain";
    }

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

//    @PostMapping("/delete/{postId}")
//    public String postSearchValue(@PathVariable Long postId, @RequestParam Long memberId, RedirectAttributes redirectAttributes) {
//        try {
//            if (postService.deletePost(postId, memberId)) {
//                redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 삭제되었습니다.");
//                return "redirect:/post/carrotMain";
//            } else {
//                redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패하였습니다.");
//                return "redirect:/post/carrotMain";
//            }
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "오류 발생: " + e.getMessage());
//            return "redirect:/error-page";
//        }
//    }


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
                return "redirect:/post/carrotDetail?postId=" + commentDTO.getPostId();
            case "Join":
                return "redirect:/post/joinDetail?postId=" + commentDTO.getPostId();
            case "Review":
                return "redirect:/post/reviewDetail?postId=" + commentDTO.getPostId();
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