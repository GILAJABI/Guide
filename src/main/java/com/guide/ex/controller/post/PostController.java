package com.guide.ex.controller.post;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.post.*;
import com.guide.ex.repository.search.AllPostSearchImpl;
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
    private AllPostSearchImpl allPostSearchImpl;

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
        CarrotDTO carrotDTO = new CarrotDTO();
        int price = carrotDTO.getPrice();
        List<ImageDTO> imageDTOS =  carrotDTO.getImageDTOs();

        MemberDTO memberDTO = new MemberDTO();
        String name = memberDTO.getName();
        model.addAttribute("post", post);
        model.addAttribute("price", price);
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

}
