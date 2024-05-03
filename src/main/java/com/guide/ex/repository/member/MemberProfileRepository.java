package com.guide.ex.repository.member;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.member.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    MemberProfile findByMember(Member member);
}
