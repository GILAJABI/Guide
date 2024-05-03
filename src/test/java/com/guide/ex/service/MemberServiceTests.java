package com.guide.ex.service;

import com.guide.ex.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;

@SpringBootTest
@Slf4j
public class MemberServiceTests {

    @Autowired
    private MemberService memberServiceTests;

    @Test
    public void signUpTest() {
        MemberDTO dto = new MemberDTO();
        dto.setUid("testUser");
        dto.setPwd("testPassword");
        dto.setName("John Doe");
        dto.setPhone("1234567890");
        dto.setTravelType("ISTJ");
        dto.setYear(Year.of(1990));
        dto.setGender("male");

        memberServiceTests.signUp(dto);
        log.info("=================Sign up Test ======================");
    }

    @Test
    public void loginTest() {
        String uid = "testUser";
        String pwd = "testPassword";
//        String pwd = "testPassword1234";
        boolean result = memberServiceTests.login(uid,pwd);
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
        log.info("=================" + result + "=====================");
    }
}
