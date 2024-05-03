package com.guide.ex.service;

import com.guide.ex.dto.member.MemberDTO;
import com.guide.ex.dto.member.MemberProfileDTO;

public interface MemberService {
//    void signUp(MemberDTO dto);
    Long register(MemberDTO memberDto);
    boolean login(String uid, String pwd);
    Long setLoginSession(String uid);
    boolean isIdAlreadyExists(String uid);
    void fileUpload(MemberProfileDTO dto);
    MemberProfileDTO memberInfo(Long member_id);
    MemberDTO readOne(Long member_id); // 조회작업처리
}
