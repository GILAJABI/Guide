package com.guide.ex.controller.post;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.*;
import com.guide.ex.service.CommentService;
import com.guide.ex.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/carrotWrite")
    public String carrotWtire(HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/post/carrotWrite";
    }

    @PostMapping("/carrotWrite")
    public String carrotWriteInput(HttpSession session, CarrotDTO carrotDTO, @RequestParam("file") MultipartFile file) {
        postService.carrotRegister(carrotDTO, file, session);
        return "redirect:/main";
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
        return "redirect:/main";
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
        return "redirect:/main";
    }

    @GetMapping("/carrotDetail")
    public String carrotDetail(Model model, Long postId) {
        Post post = postService.postDetailRead(postId, "Carrot");
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
    public void reviewDetail() {

    }

    @GetMapping("/joinDetail")
    public void joinDetail(){

    }

    @GetMapping("/carrotMain")
    public String carrotMain(@Valid Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int size) {
        Page<CarrotDTO> posts = postService.carrotTypeReadAll(size, page);
        System.out.println("-----------------------");
        System.out.println(posts.getContent());
        System.out.println("-----------------------");
        log.info("Carrot posts fetched: Total elements={}, Total pages={}, Current page index={}",
                posts.getTotalElements(), posts.getTotalPages(), posts.getNumber());

        model.addAttribute("posts", posts);
        return "post/carrotMain";  // View name for Thymeleaf template
    }


    @GetMapping("/joinMain")
    public void joinMain() {

    }

    @GetMapping("/reviewMain")
    public void reviewMain() {

    }

    @PostMapping("/carrotDetail")
    public ResponseEntity<?> registerComment(
            @RequestParam("commentContent") String commentContent,
            @RequestParam("postId") Long postId,
            @RequestParam("memberId") Long memberId,
            RedirectAttributes redirectAttributes) {

        if (commentContent == null || postId == null || memberId == null) {
            return ResponseEntity.badRequest().body("Null values are not allowed.");
        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContent(commentContent);
        commentDTO.setPostId(postId);
        commentDTO.setMemberId(memberId);

        try {
            Long commentId = commentService.register(commentDTO);
            return ResponseEntity.ok("Comment created with ID: " + commentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create comment: " + e.getMessage());
        }
    }
}
