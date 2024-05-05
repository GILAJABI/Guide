package com.guide.ex.service;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
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

    @Test
    public void signUpTest() {
        log.info(memberService.getClass().getName());

        MemberDTO memberDTO = MemberDTO.builder()
                .name("1팀")
                .uid("travle")
                .pwd("maker")
                .phone("01012345678")
                .year(Year.of(1999))
                .gender("male")
                .build();

        memberService.signUp(memberDTO);
    }

    @Test
    public void loginTest() {
        String uid = "testUser";
//        String pwd = "testPassword";
        String pwd = "testPassword1234";
        boolean result = memberService.login(uid,pwd);
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
    }

    @Test
    public void fileUploadTest() {
        MemberProfileDTO memberProfileDTO = new MemberProfileDTO();
        memberProfileDTO.setMemberProfileId(43L);
        memberProfileDTO.setUuid("uuidTest asdfsdfasdds");
        memberProfileDTO.setContent("content test asdfasdfsadfsdf");
        memberProfileDTO.setFileName("fileNamne.test");
        memberService.fileUpload(memberProfileDTO);
    }

    @Test
    public void memberReadOneTest() {
        MemberDTO memberDTO = memberService.memberReadOne(2L);
        log.info(memberDTO);
    }

    @Test
    public void profileModifyTest() {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(2L)
                .name("또 수정된 이름")
                .build();

        MemberProfileDTO memberProfileDTO = MemberProfileDTO.builder()
                .memberProfileId(2L)
                .uuid("또 modifyUuid")
                .fileName("또 modifyFileName")
                .content("또 수정된 한줄소개")
                .travelType("ENFP")
                .build();

        memberService.profileModify(memberDTO, memberProfileDTO);
    }
}
