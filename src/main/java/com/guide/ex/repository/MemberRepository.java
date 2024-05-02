package com.guide.ex.repository;

import com.guide.ex.domain.Member;
import com.guide.ex.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUid(String uid);

}
