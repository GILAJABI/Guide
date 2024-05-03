package com.guide.ex.service;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.member.MemberProfile;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
import com.guide.ex.repository.member.MemberProfileRepository;
import com.guide.ex.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final MemberProfileRepository memberProfileRepository;

//    @Override
//    public void signUp(MemberDTO dto) {
//
//        String salt = generateSalt();
//        String hashedPassword = hashPassword(dto.getPwd(), salt);
//
//        // Build Member object
//        Member member = Member.builder()
//                .uid(dto.getUid())
//                .salt(salt)
//                .pwd(hashedPassword)
//                .name(dto.getName())
//                .phone(dto.getPhone())
//                .travelType(dto.getTravelType())
//                .year(dto.getYear())
//                .gender(dto.getGender())
//                .build();
//
//        memberRepository.save(member);
//    }

    @Override
    public Long register(MemberDTO memberDto) {
        Member member = modelMapper.map(memberDto, Member.class);

        Long memberId = memberRepository.save(member).getMemberId();

        return memberId;
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
    public MemberProfileDTO memberInfo(Long member_id) {
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

        //회원 정보
        dto.setName(result.getMember().getName());
        dto.setGender(result.getMember().getGender());
        dto.setPhone(result.getMember().getPhone());
        dto.setYear(result.getMember().getYear());
        dto.setRating(result.getMember().getRating());
        dto.setTravelType(result.getMember().getTravelType());

        return dto;
    }

    private String generateSalt() {
        return UUID.randomUUID().toString();
    }
    private String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }

    public MemberDTO readOne(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);

        Member member = result.orElseThrow();

        MemberDTO dto = modelMapper.map(member, MemberDTO.class);

        return dto;
    }
}
