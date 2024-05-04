package com.guide.ex.controller.member;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
import com.guide.ex.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Value("${com.guide.upload.path}")
    private String uploadPath;


    @Autowired
    private MemberService memberService;


    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("uid") String uid, @RequestParam("pwd") String pwd, HttpSession session) {

        boolean check = memberService.login(uid, pwd);

        if (check) {
            session.setAttribute("member_id", memberService.setLoginSession(uid));
            return "redirect:/member/testSuccess";
        } else {
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
    public String profile(HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/member/profile.html";
    }

    @PostMapping("/profile")
    public String uploadFile(HttpSession session,
                             MemberProfileDTO memberProfileDTO,
                             @RequestParam("file") MultipartFile file) {
        memberProfileDTO.setMemberId((Long) session.getAttribute("member_id"));

        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            memberProfileDTO.setUuid(uuid);
            memberProfileDTO.setFileName(originalName);
            memberService.fileUpload(memberProfileDTO);
            return "redirect:/member/testSuccess";

        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }
    }


    @GetMapping("/signUp")
    public void signUp() {

    }

    @PostMapping("/signUp")
    public String signUp(MemberDTO memberDTO) {
        memberService.register(memberDTO);
        return "redirect:/member/login";
    }

    @GetMapping("/testSuccess")
    public void testSuccess(HttpSession session) {

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
