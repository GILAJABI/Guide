package com.guide.ex.repository.member;

import com.guide.ex.domain.member.Member;
import com.guide.ex.domain.member.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    Optional<MemberProfile> findByMember(Member member);
    Optional<MemberProfile> findByMember_MemberId(Long memberId);
}
