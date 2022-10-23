package me.yoonho.inflearnthejavatest.member;

import me.yoonho.inflearnthejavatest.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    boolean validate(Long memberId);
}
