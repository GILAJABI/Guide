package com.guide.ex.controller;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("uid") String uid, @RequestParam("pwd") String pwd, HttpSession session) {

        boolean check = memberService.login(uid, pwd);

        if(check) {
            //로그인 성공시 세션에 member_id 저장
            session.setAttribute("member_id", memberService.setLoginSession(uid));
            return "redirect:/member/testLogin";
        }else{
            return "redirect:/member/login?error=incorrect";
        }

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/member/login";
    }

    @GetMapping("/profile")
    public void profile() {

    }

    @GetMapping("/signUp")
    public void signUp() {

    }

    @PostMapping("/signUp")
    public String signUp(MemberDTO memberDTO) {
        memberService.register(memberDTO);
        return "redirect:/member/login";
    }

    @GetMapping("/testLogin")
    public void testLogin(HttpSession session) {
        System.out.println("=============sesssion check================");
        System.out.println(session.getAttribute("member_id"));
        System.out.println("===========================================");
    }

    @PostMapping("/checkDuplicateId")
    @ResponseBody
    public ResponseEntity<String> checkDuplicateId(@RequestParam String uid) {

        if (memberService.isIdAlreadyExists(uid)) {
            return ResponseEntity.ok("Duplicate");
        } else {
            return ResponseEntity.ok("Available");
        }
    }

    @GetMapping("/myPage")
    public void myPage() {

    }

    @GetMapping("/otherPage")
    public void otherPage() {

    }

}
