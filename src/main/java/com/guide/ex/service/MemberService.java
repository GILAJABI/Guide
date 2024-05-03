package com.guide.ex.service;

import com.guide.ex.domain.Member;
import com.guide.ex.domain.MemberProfile;
import com.guide.ex.dto.MemberDTO;
import com.guide.ex.dto.MemberProfileDTO;

public interface MemberService {
    void signUp(MemberDTO dto);
    boolean login(String uid, String pwd);
    Long setLoginSession(String uid);
    boolean isIdAlreadyExists(String uid);
    void fileUpload(MemberProfileDTO dto);
    MemberProfileDTO findByMemberId(Long member_id);
}
