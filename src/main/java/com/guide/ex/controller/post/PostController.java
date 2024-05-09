package com.guide.ex.controller.post;

import com.guide.ex.domain.post.Carrot;
import com.guide.ex.dto.post.*;
import com.guide.ex.service.MemberService;
import com.guide.ex.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/carrotWrite")
    public void carrotWtire() {

    }

    @PostMapping("/carrotWrite")
    public String carrotWtireInput(HttpSession session, CarrotDTO carrotDTO, @RequestParam("file") MultipartFile file) {

        System.out.println("========================Connect controller==============================");
        System.out.println(carrotDTO.getTitle());
        System.out.println(carrotDTO.getPrice());
        System.out.println(carrotDTO.getContent());
        System.out.println(carrotDTO.getContent());
        System.out.println(carrotDTO.getLocationX());
        System.out.println(carrotDTO.getLocationY());
        System.out.println("========================================================================");

        postService.carrotRegister(carrotDTO, file, session);

        return "redirect:/main";
    }

    @GetMapping("/joinWrite")
    public void joinWrite() {

    }

    @PostMapping("/joinWrite")
    public String joinWriteInput(HttpSession session, JoinDTO joinDTO, @RequestParam("file") MultipartFile file) {

        System.out.println("========================Connect joinWrite controller==============================");
        System.out.println(joinDTO.getTitle());
        System.out.println(joinDTO.getExpense());
        System.out.println(joinDTO.getNumPeople());
        System.out.println(joinDTO.getContent());
        System.out.println(joinDTO.getLocationX());
        System.out.println(joinDTO.getLocationY());
        System.out.println("========================================================================");
        postService.joinRegister(joinDTO, file, session);
        return "redirect:/main";
    }

    @GetMapping("/reviewWrite")
    public void reviewWrite() {

    }

    @PostMapping("/reviewWrite")
    public String reviewWriteInput(HttpSession session, ReviewDTO reviewDTO, @RequestParam("file") MultipartFile file) {
        System.out.println("========================Connect joinWrite controller==============================");
        System.out.println(reviewDTO.getTitle());
        System.out.println(reviewDTO.getExpense());
        System.out.println(reviewDTO.getGrade());
        System.out.println(reviewDTO.getContent());
        System.out.println(reviewDTO.getLocationX());
        System.out.println(reviewDTO.getLocationY());
        System.out.println("========================================================================");
        postService.reviewRegister(reviewDTO, file, session);

        return "redirect:/main";
    }

    //////////////////////////`
    @GetMapping("/carrotDetail")
    public void carrotDetail() {

    }


    @GetMapping("/carrotMain")
    public void carrotMain() {

    }


    @GetMapping("/joinMain")
    public void joinMain() {

    }

    @GetMapping("/reviewMain")
    public void reviewMain() {

    }

}
