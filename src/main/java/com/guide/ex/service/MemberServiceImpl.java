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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    @Value("${com.guide.upload.path}")
    private String uploadPath;

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    // 회원 등록 작업(회원가입)
    @Override
    public void signUp(MemberDTO memberDto) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(memberDto.getPwd(), salt);

        memberDto.setSalt(salt);
        memberDto.setPwd(hashedPassword);

        Member member = modelMapper.map(memberDto, Member.class);

        memberRepository.save(member);
    }

    @Override
    public boolean login(String uid, String pwd) {
        Member member = memberRepository.findByUid(uid);

        if (member != null && !member.isBan()) {
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
        if (member != null)
            return true;
        return false;
    }

    @Override
    public void fileUpload(MemberProfileDTO dto, MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }
        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        try {
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            file.transferTo(savePath);
            dto.setUuid(uuid);
            dto.setFileName(originalName);

            MemberProfile memberProfile = modelMapper.map(dto, MemberProfile.class);

            Optional<Member> result = memberRepository.findById(dto.getMemberId());
            Member member = result.orElseThrow(() -> new NoSuchElementException("해당하는 회원을 찾을 수 없습니다.")); // 조회된 Member가 없을 경우 예외 발생

            memberProfile.setMember(member);
            memberProfileRepository.save(memberProfile);


        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 도중 에러가 발생했습니다.", e);
        }
    }

    private String generateSalt() {
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }

    @Override
    public MemberDTO memberReadOne(Long memberId) {
        // Member 엔티티 조회
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(() -> new NoSuchElementException("해당하는 회원을 찾을 수 없습니다.")); // 조회된 Member가 없을 경우 예외 발생

        // MemberDTO로 변환
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);

        // MemberProfile 조회
        Optional<MemberProfile> memberProfileResult = memberProfileRepository.findByMember(member);
        MemberProfile memberProfile = memberProfileResult.orElse(null); // 조회된 MemberProfile이 없을 경우 null을 반환

        // MemberProfile 정보를 MemberDTO에 추가
        // 예를 들어 MemberDTO에 setProfileInfo 메서드를 정의했다고 가정
        if (memberProfile != null) {
            memberDTO.setProfileInfo(modelMapper.map(memberProfile, MemberProfileDTO.class));
        } else {
            memberDTO.setProfileInfo(null); // 또는 빈 MemberProfileDTO 객체를 넣어도 됩니다.
        }

        return memberDTO;
    }

    @Override
    public void profileRegister(MemberProfileDTO memberProfileDTO) {
        Member member = memberRepository.findById(memberProfileDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberProfileDTO.getMemberId()));

        MemberProfile memberProfile = modelMapper.map(memberProfileDTO, MemberProfile.class);
        memberProfile.setMember(member); // Member 설정
        memberProfileRepository.save(memberProfile);
    }

    @Override
    public List<MemberDTO> findProfileMember() {
        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (Member member : members) {
            Optional<MemberProfile> memberProfileResult = memberProfileRepository.findByMember(member);
            memberProfileResult.ifPresent(memberProfile -> {
                MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
                MemberProfileDTO memberProfileDTO = modelMapper.map(memberProfile, MemberProfileDTO.class);
                memberDTO.setProfileInfo(memberProfileDTO);
                memberDTOList.add(memberDTO);
            });
        }
        return memberDTOList;
    }

    @Override
    public void profileModify(MemberProfileDTO memberProfileDTO) {
        // MemberProfile 엔티티 조회 및 변경
        MemberProfile memberProfile = memberProfileRepository.findById(memberProfileDTO.getMemberProfileId())
                .orElseThrow(() -> new RuntimeException("MemberProfile not found"));
        memberProfile.change(
                memberProfileDTO.getUuid(),
                memberProfileDTO.getFileName(),
                memberProfileDTO.getContent(),
                memberProfileDTO.getTravelType()
        );
        memberProfileRepository.save(memberProfile);

    }

    @Override
    public boolean setProfileSession(Long member_id) {
        MemberDTO member = memberReadOne(member_id);
        if (member.getProfileInfo() != null) {
            return true;
        }
        return false;
    }
//    // 회원 삭제 작업
//    @Override
//    public void memberRemove(Long memberId) {
//        memberProfileRepository.deleteByMemberId(memberId);
//        memberRepository.deleteById(memberId);
//    }


}
