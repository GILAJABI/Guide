package com.guide.ex.repository;

import com.guide.ex.domain.Member;
import com.guide.ex.domain.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    MemberProfile findByMember(Member member);
}
