package com.guide.ex.repository;

import com.guide.ex.domain.Member;
import com.guide.ex.domain.MemberProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void memberSignUpTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .uid("uid")
                    .pwd("aaa")
                    .name("kim")
                    .phone("0100000000")
                    .salt("exsalt")
                    .travelType("ISTJ")
                    .year(Year.of(1999))
                    .gender("men")
                    .build();

            Member result = memberRepository.save(member);
        });

    }

}
