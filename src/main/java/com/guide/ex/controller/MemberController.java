package com.guide.ex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void login() {

    }

    @GetMapping("/profile")
    public void profile() {

    }

    @GetMapping("/signUp")
    public void signUp() {

    }
}
