package me.yoonho.inflearnthejavatest.study;

import me.yoonho.inflearnthejavatest.domain.Member;
import me.yoonho.inflearnthejavatest.domain.Study;
import me.yoonho.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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
        Member member = new Member();
        member.setId(1L);
        member.setEmail("yoonho@email.com");

        // id 1L을 가진 멤버를 조회했을때 전달한 멤버를 리턴하라는 mock 객체 stubbing (행동을 정의)
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "java");

        Optional<Member> newMember = memberService.findById(1L);
        assertEquals("yoonho@email.com", newMember.get().getEmail());

        // id 1L을 가진 멤버를 validate 메소드로 조회했을때 예외 처리를 발생.
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        // 첫 번째, 두 번째, 세 번째 호출 마다 결과 값을 다르게 stubbing.
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        //Study newStudy = studyService.createNewStudy(1L, study);
        //assertNotNull(newStudy);

        // mock 연습문제
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        // notify 메소드가 한번 호출되어야 한다. mock 객체가 어떻게 사용되는지 확인하는 방법
        verify(memberService, times(1)).notify(study);

        // validate 호출되면 안된다.
        verify(memberService, never()).validate(any());

        // study의 notify 후에 member의 notify가 실행돼야한다.
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

        // memberService에서 작업이 더이상 일어나면 안된디.
        //verifyNoMoreInteractions(memberService);

        // Mockito BDD
        // given
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // when
        studyService.createNewStudy(1L, study);

        // then
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }
}
