package com.guide.ex.repository;

import com.guide.ex.domain.Member;
import com.guide.ex.domain.MemberProfile;
import com.guide.ex.repository.MemberProfileRepository;
import com.guide.ex.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class MemberProfileRepositoryTests {

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Test
    public void memberProfileInsertTest() {
        Member member = Member.builder().memberId(44L).build();
        MemberProfile memberProfile = MemberProfile.builder()
                .member(member)
                .fileName("exfileName.jpg")
                .content("자기소개 ..................")
                .uuid("asdkjfh3h9e8uehw")
                .build();

        memberProfileRepository.save(memberProfile);
    }

    @Test
    @Transactional
    public void memberProfileSelectOneTest() {
        Member member = Member.builder().memberId(44L).build();
        MemberProfile memberProfile = memberProfileRepository.findByMember(member);
        log.info(memberProfile.toString());
    }

}
