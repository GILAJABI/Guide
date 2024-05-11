package com.guide.ex.controller.post;

import com.guide.ex.dto.post.CarrotDTO;
import com.guide.ex.dto.post.JoinDTO;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.dto.post.ReviewDTO;
import com.guide.ex.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;


@Controller
@Log4j2
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/carrotWrite")
    public String carrotWtire(HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/post/carrotWrite";
    }

    @PostMapping("/carrotWrite")
    public String carrotWtireInput(HttpSession session, CarrotDTO carrotDTO, @RequestParam("file") MultipartFile file) {
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
    public void carrotDetail() {

    }

    @GetMapping("/reviewDetail")
    public void reviewDetail() {

    }

    @GetMapping("/joinDetail")
    public void joinDetail(){

    }

    @GetMapping("/carrotMain")
    public void carrotMain(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "12") int size) {

        Page<CarrotDTO> post = postService.carrotTypeReadAll(size, page);
        System.out.println("-----------------------");
        System.out.println(post.getContent());
        System.out.println("-----------------------");
        log.info("Carrot posts fetched: Total elements={}, Total pages={}, Current page index={}",
                post.getTotalElements(), post.getTotalPages(), post.getNumber());

//        if (!post.hasContent()) {
//            log.warn("No content available for page {}", page + 1);
//            model.addAttribute("message", "No posts available");
//            return "post/carrotMain";
//        }

        model.addAttribute("posts", post);
        model.addAttribute("currentPage", page + 1);
    }

    @GetMapping("/joinMain")
    public void joinMain() {

    }

    @GetMapping("/reviewMain")
    public void reviewMain() {

    }

}
