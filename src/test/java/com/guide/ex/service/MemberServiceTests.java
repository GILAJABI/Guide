package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.member.MemberProfile;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
import com.guide.ex.repository.member.MemberProfileRepository;
import com.guide.ex.repository.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberProfileRepository memberProfileRepository;

    // 회원가입
    @Test
    public void signUpTest() {
        log.info(memberService.getClass().getName());

        MemberDTO memberDTO = MemberDTO.builder()
                .name("정범수")
                .uid("jeongbeomsu")
                .pwd("0000")
                .phone("01050199044")
                .year(Year.of(2000))
                .gender("male")
                .build();

        memberService.signUp(memberDTO);
    }

    // 로그인
    @Test
    public void loginTest() {
        String uid = "leechaerim";
//        String pwd = "1111";
        String pwd = "0000";
        boolean result = memberService.login(uid,pwd);
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
    }

    // 프로필 생성
    @Test
    public void profileRegisterTest() {
        MemberProfileDTO memberProfileDTO = MemberProfileDTO.builder()
                .memberId(13L)
                .uuid("test")
                .content("test")
                .fileName("fileName.test")
                .travelType("ESTP")
                .build();

        memberService.profileRegister(memberProfileDTO);
    }

    // 프로필 수정
    @Test
    public void profileModifyTest() {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(8L)
                .name("수정된 이채림")
                .build();

        MemberProfileDTO memberProfileDTO = MemberProfileDTO.builder()
                .memberProfileId(8L)
                .uuid("new")
                .fileName("new")
                .content("new")
                .travelType("ISFP")
                .build();

        memberService.profileModify(memberDTO, memberProfileDTO);
    }

    // 회원 정보 읽기(member + memberProfile)
    @Test
    public void memberReadOneTest() {
        MemberDTO memberDTO = memberService.memberReadOne(7L);
        log.info(memberDTO);
    }

//    @Test
//    public void fileUploadTest() {
//        MemberProfileDTO memberProfileDTO = new MemberProfileDTO();
//        memberProfileDTO.setMemberProfileId(43L);
//        memberProfileDTO.setUuid("uuidTest asdfsdfasdds");
//        memberProfileDTO.setContent("content test asdfasdfsadfsdf");
//        memberProfileDTO.setFileName("fileNamne.test");
//        memberService.fileUpload(memberProfileDTO);
//    }
}
