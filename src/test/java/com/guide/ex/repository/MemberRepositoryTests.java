package com.guide.ex.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.guide.ex.domain.Member;

import java.time.Year;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .name("kim")
                    .uid("travel")
                    .pwd("maker")
                    .salt("someSaltValue") // 여기에 적절한 salt 값을 추가
                    .phone("01012345678")
                    .year(Year.of(1999))
                    .gender("male")
                    .travelType("ISTJ")
                    .build();

            Member result = memberRepository.save(member);
            log.info("member_id: " + result.getMemberId());
        });
    }

    @Test
    public void memberSelectTest() {
        Long member_id = 43L;

        Optional<Member> result = memberRepository.findById(member_id);

        Member member = result.orElseThrow();

        log.info(member);
    }

    @Test
    public void memberUpdateTest() {
        Long member_id = 43L;

        Optional<Member> result = memberRepository.findById(member_id);

        Member member = result.orElseThrow();

        member.change("update 이채림");

        memberRepository.save(member);
    }

    @Test
    public void memberDeleteTest() {
        Long member_id = 57L;

        memberRepository.deleteById(member_id);
    }
}
