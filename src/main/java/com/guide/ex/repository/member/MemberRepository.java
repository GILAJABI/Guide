package com.guide.ex.repository.member;

import com.guide.ex.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUid(String uid);

}
