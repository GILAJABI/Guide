package com.guide.ex.service;

import com.guide.ex.domain.member.MemberProfile;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService {
    void signUp(MemberDTO memberDto);
    boolean login(String uid, String pwd);
    Long setLoginSession(String uid);
    boolean isIdAlreadyExists(String uid);
//    void fileUpload(MemberProfileDTO dto);
    void fileUpload(MemberProfileDTO dto, MultipartFile file);

    //    MemberProfileDTO memberInfo(Long member_id);
//    MemberDTO readOne(Long member_id);
//    void modify(MemberProfileDTO memberProfileDTO);
    //아래 함수는 DTO로 ModelMapper를 활용해 수정
//    MemberProfile memberInfo(Long member_id);
    //프로필 id 세션 할당 값
    boolean setProfileSession(Long member_id);
    MemberDTO memberReadOne(Long member_id);
//    void profileModify(MemberDTO memberDTO, MemberProfileDTO memberProfileDTO);
    void profileModify(MemberProfileDTO memberProfileDTO);
    void profileRegister(MemberProfileDTO memberProfileDTO);
    List<MemberDTO> findProfileMember();
}
