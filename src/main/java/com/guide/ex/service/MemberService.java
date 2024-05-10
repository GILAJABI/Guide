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
    void fileUpload(MemberProfileDTO dto, MultipartFile file);
    boolean setProfileSession(Long member_id);
    MemberDTO memberReadOne(Long member_id);
    void profileModify(MemberProfileDTO memberProfileDTO);
    void profileRegister(MemberProfileDTO memberProfileDTO);
    List<MemberDTO> findProfileMember();
}
