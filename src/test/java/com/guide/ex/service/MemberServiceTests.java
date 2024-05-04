package com.guide.ex.service;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
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

//    @Test
//    public void signUpTest() {
//        MemberDTO dto = new MemberDTO();
//        dto.setUid("qwer");
//        dto.setPwd("qwer");
//        dto.setName("yurt");
//        dto.setPhone("1234567890");
//        dto.setTravelType("ISTJ");
//        dto.setYear(Year.of(1999));
//        dto.setGender("male");
//
//        memberService.signUp(dto);
//        log.info("=================Sign up Test ======================");
//    }

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
    public void selectOneTest() {
        MemberProfileDTO memberInfo = memberService.memberInfo(43L);
        log.info("=====================================================");
        log.info(memberInfo.toString());
        log.info("=====================================================");
    }

}
