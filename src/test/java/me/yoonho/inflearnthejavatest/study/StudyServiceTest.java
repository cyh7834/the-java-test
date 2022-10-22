package me.yoonho.inflearnthejavatest.study;

import me.yoonho.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Mock 객체는 인터페이스만 존재하고 구현체가 없는 클래스들을 구현체 없이 테스트를 위해 의존성을 주입할 수 있다.
 * 또한 구현체가 있다고 하더라도 의존된 클래스는 테스트하지 않고 특정 클래스만 테스트하고 싶을 때 사용하면 된다.
 * Mock 객체의 동작을 가정하여 코드를 작성해 사용할 수 있다.
 */
@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    // Mock 객체를 어노테이션으로 생성. 각 메소드에 파라미터로 전달하여 사용할 수도 있다.
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Assertions.assertNotNull(studyService);
    }
}
