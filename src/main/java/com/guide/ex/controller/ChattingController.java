package com.guide.ex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChattingController {

    @GetMapping("/chatList")
    public void chatList (){

    }

    @GetMapping("/chatRoom")
    public void chatRoom () {

    }
}
