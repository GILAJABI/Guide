package com.guide.ex.controller.post;

import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/carrotWrite")
    public void carrotWrite() {
    }
    @GetMapping("/joinWrite")
    public void joinWrite() {

    }

    @GetMapping("/reviewWrite")
    public void reviewWrite() {

    }
//////////////////////////
    @GetMapping("/carrotDetail")
    public void carrotDetail() {

    }

    @GetMapping("/carrotMain")
    public String carrotMain(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size) {

        Page<PostDTO> post = postService.PostTypeReadAll("Carrot", size, page);
        log.info("Carrot posts fetched: Total elements={}, Total pages={}, Current page index={}",
                post.getTotalElements(), post.getTotalPages(), post.getNumber());

        if (!post.hasContent()) {
            log.warn("No content available for page {}", page + 1);
            model.addAttribute("message", "No posts available");
            return "post/carrotMain";
        }

        model.addAttribute("posts", post);
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", post);

        return "post/carrotMain";
    }






    @GetMapping("/joinMain")
    public void joinMain() {

    }

    @GetMapping("/reviewMain")
    public void reviewMain() {

    }

}
