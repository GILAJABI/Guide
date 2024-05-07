package com.guide.ex.service;

import com.guide.ex.domain.member.MemberProfile;
import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;

public interface MemberService {
    void signUp(MemberDTO memberDto);
    boolean login(String uid, String pwd);
    Long setLoginSession(String uid);
    boolean isIdAlreadyExists(String uid);
    void fileUpload(MemberProfileDTO dto);
    void profileRegister(MemberProfileDTO memberProfileDTO);
    MemberDTO memberReadOne(Long member_id);
    void profileModify(MemberDTO memberDTO, MemberProfileDTO memberProfileDTO);
//    void memberRemove(Long member_id);
}
