package com.guide.ex.controller.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @GetMapping("/carrotWtire")
    public void carrotWtire() {
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
    public void carrotMain() {

    }


    @GetMapping("/joinMain")
    public void joinMain() {

    }

    @GetMapping("/reviewMain")
    public void reviewMain() {

    }

}
