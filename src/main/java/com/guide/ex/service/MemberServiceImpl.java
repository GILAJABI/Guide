package com.guide.ex.service;

import com.guide.ex.domain.Member;
import com.guide.ex.dto.MemberDTO;
import com.guide.ex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.Year;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void signUp(MemberDTO dto) {

        String salt = generateSalt();
        String hashedPassword = hashPassword(dto.getPwd(), salt);

        // Build Member object
        Member member = Member.builder()
                .uid(dto.getUid())
                .salt(salt)
                .pwd(hashedPassword)
                .name(dto.getName())
                .phone(dto.getPhone())
                .travelType(dto.getTravelType())
                .year(dto.getYear())
                .gender(dto.getGender())
                .build();

        memberRepository.save(member);
    }

    @Override
    public boolean login(String uid, String pwd) {
        Member member = memberRepository.findByUid(uid);
        if (member != null) {
            String hashedPassword = hashPassword(pwd, member.getSalt());
            if (hashedPassword.equals(member.getPwd())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Long setLoginSession(String uid) {
        Member member = memberRepository.findByUid(uid);
        return member.getMemberId();
    }


    //Salt생성
    private String generateSalt() {
        return UUID.randomUUID().toString();
    }

    // (비밀번호 + Salt) -> Sha-256
    private String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }


}
