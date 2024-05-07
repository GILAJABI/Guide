package com.guide.ex.controller.member;

import com.guide.ex.domain.member.MemberProfile;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
import com.guide.ex.repository.member.MemberProfileRepository;
import com.guide.ex.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jdt.internal.compiler.ast.UsesStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @Value("${com.guide.upload.path}")
    private String uploadPath;

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Autowired
    private MemberService memberService;

    // 생성자 주입을 통한 의존성 주입 (Lombok의 @RequiredArgsConstructor 사용 가능)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signUp")
    public void signUp() {

    }

    @PostMapping("/signUp")
    public String signUp(MemberDTO memberDTO) {
        memberService.signUp(memberDTO);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("uid") String uid, @RequestParam("pwd") String pwd, HttpSession session) {
        boolean check = memberService.login(uid, pwd);

        if (check) {
            Long memberId = memberService.setLoginSession(uid);
            session.setAttribute("member_id", memberId);
            if (memberService.setProfileSession(memberId)) {
                session.setAttribute("member_profile", true);
            }
            return "redirect:/main";

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
        return "redirect:/main";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        if (session.getAttribute("member_id") == null) {
            return "redirect:/member/login";
        }
        return "/member/profile";
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
            session.setAttribute("member_profile", true);
            return "redirect:/main";

        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }
    }

//    @PostMapping("/profile")
//    public String updateProfile(@ModelAttribute MemberProfileDTO memberProfileDTO, HttpSession session) {
//        Long memberId = (Long) session.getAttribute("member_id"); // 세션에서 회원 ID 가져오기
//        if (memberId == null) {
//            return "redirect:/member/login"; // 로그인 안되어있으면 로그인 페이지로 리다이렉션
//        }
//
//        memberProfileDTO.setMemberId(memberId);
//
//        // 프로필이 이미 존재하는지 확인
//        Optional<MemberProfile> existingProfile = memberProfileRepository.findByMember_MemberId(memberId);
//
//        if (existingProfile.isPresent()) {
//            // 프로필 수정
//            MemberDTO memberDTO = new MemberDTO();
//            memberDTO.setMemberId(memberId); // memberId 설정
//            memberService.profileModify(memberDTO, memberProfileDTO);
//        } else {
//            // 프로필 등록
//            memberService.profileRegister(memberProfileDTO);
//        }
//
//        return "redirect:/member/myPage/" + memberId; // 프로필 업데이트 후 myPage로 리디렉션
//    }

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
    public String myPage(HttpSession session, Model model) {
        Long memberId = (Long) session.getAttribute("member_id");
        MemberDTO member = memberService.memberReadOne(memberId);
        model.addAttribute("member", member);
        return "member/myPage";
    }

    @GetMapping("/otherPage/{memberId}")
    public String otherPage(@PathVariable Long memberId, Model model) {
        MemberDTO memberDTO = memberService.memberReadOne(memberId);
        model.addAttribute("member", memberDTO);
        model.addAttribute("profile", memberDTO.getProfileInfo());
        return "member/otherPage";
    }
}
