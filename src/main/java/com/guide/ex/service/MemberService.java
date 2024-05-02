package com.guide.ex.service;

import com.guide.ex.domain.Member;
import com.guide.ex.dto.MemberDTO;

public interface MemberService {
    void signUp(MemberDTO dto);
    boolean login(String uid, String pwd);
    Long setLoginSession(String uid);
    boolean isIdAlreadyExists(String uid);
}
