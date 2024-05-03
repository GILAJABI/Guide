package com.guide.ex.service;

import com.guide.ex.domain.Member;
import com.guide.ex.domain.MemberProfile;
import com.guide.ex.dto.MemberDTO;
import com.guide.ex.dto.MemberProfileDTO;
import com.guide.ex.repository.MemberProfileRepository;
import com.guide.ex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

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

    @Override
    public boolean isIdAlreadyExists(String uid) {
        Member member = memberRepository.findByUid(uid);
        if(member != null)
            return true;
        return false;
    }

    @Override
    public void fileUpload(MemberProfileDTO memberProfileDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberProfileDTO.getMemberId());
        Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("Member not found"));

        MemberProfile memberProfile = MemberProfile.builder()
                .uuid(memberProfileDTO.getUuid())
                .fileName(memberProfileDTO.getFileName())
                .content(memberProfileDTO.getContent())
                .member(member)
                .build();

        memberProfileRepository.save(memberProfile);
    }

    @Override
    public MemberProfileDTO findByMemberId(Long member_id) {
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("Member not found"));

        MemberProfile result = memberProfileRepository.findByMember(member);

        MemberProfileDTO dto = new MemberProfileDTO();
        
        //사진 정보
        dto.setFileName(result.getFileName());
        dto.setUuid(result.getUuid());
        
        //개인 소개
        dto.setContent(result.getContent());
        
        //회원 활동
        dto.setLikeCount(result.getMember().getLikeCount());
        dto.setPostCount(result.getMember().getPostCount());
        dto.setCommentCount(result.getMember().getCommentCount());





        return dto;
    }




    private String generateSalt() {
        return UUID.randomUUID().toString();
    }
    private String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }


}
