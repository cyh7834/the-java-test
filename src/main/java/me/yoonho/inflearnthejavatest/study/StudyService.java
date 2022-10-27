package me.yoonho.inflearnthejavatest.study;

import me.yoonho.inflearnthejavatest.domain.Member;
import me.yoonho.inflearnthejavatest.domain.Study;
import me.yoonho.inflearnthejavatest.member.MemberService;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;

    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doens't exist for id : " + memberId)));

        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member.get());

        return newStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = studyRepository.save(study);
        memberService.notify(openedStudy);

        return openedStudy;
    }
}
