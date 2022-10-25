package me.yoonho.inflearnthejavatest.member;

import me.yoonho.inflearnthejavatest.domain.Member;
import me.yoonho.inflearnthejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    boolean validate(Long memberId);

    void notify(Study study);

    void notify(Member member);
}
