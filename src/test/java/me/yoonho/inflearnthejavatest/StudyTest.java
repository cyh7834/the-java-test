package me.yoonho.inflearnthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

// 메소드 명의 언더 바를 공백으로 바꿔줌
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    // 메소드 이름이 아닌 테스트 이름을 직접 선
    @DisplayName("스터디 만들기")
    void create() {
        Study study = new Study(10);

        // 예외 발생 테스트
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals(exception.getMessage(), "스터디 정원은 0보다 커야 한다.");

        // 테스트를 순차적이 아닌 한꺼번에 실행해야 할 경우 사용할 수 있다.
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태 값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
        );

        // 처리 시간을 테스트. 10초 안에 스터디 생성이 끝나야 한다.
        assertTimeout(Duration.ofSeconds(10), () -> new Study(10));
        // 타임 아웃이 발생하면 그 이후에 코드는 실행하지 않고 바로 종료.
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            new Study(10);
            Thread.sleep(3000);
        });
    }

    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    @EnabledOnOs(OS.MAC)
    @EnabledOnJre(JRE.JAVA_11)
    void assumeTrueTest() {
        String testEnv = "LOCAL";

        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));
        assumingThat("DEV".equalsIgnoreCase(testEnv), () -> {

        });
    }

    @Test
    // intellij junit 테스트 설정이나 maven surefire 설정을 통해 특정 태그의 테스트만 실행할 수 있다.
    @Tag("fast")
    void fastTest() {

    }

    @Test
    @Tag("slow")
    void slowTest() {

    }

    @FastTest
    @DisplayName("커스텀 애노테이션 테스트")
    void customAnnotation() {

    }

    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();

        System.out.println("currentRepetition = " + currentRepetition);
        System.out.println("totalRepetitions = " + totalRepetitions);

    }

    // 정의된 파라미터로 반복하여 테스트를 진행한다.
    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} {0}")
    @ValueSource(strings = {"a", "b", "c", "d"})
    void parameterizedTest(String alpha) {
        System.out.println(alpha);
    }

    // 정의된 파라미터로 반복하여 테스트를 진행한다.
    @DisplayName("파라미터 테스트2")
    @ParameterizedTest(name = "{index} {displayName} {0}")
    //@ValueSource(ints = {1, 2, 3})
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    //@EmptySource // 비어있는 문자열을 추가
    //@NullSource // NULL을 추가
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "Can only convert to Study");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    @ParameterizedTest
    @DisplayName("파라미터 테스트3")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest3(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study.toString());
    }

    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    @Test
    void create1() {
        System.out.println("create1");
    }

    /**
     * 모든 테스트가 실행되기 이전에 딱 한번 호출된다.
     * static method를 사용해야하고 리턴 타입이 있으면 안된다.
     * */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    /**
     * 모든 테스트가 실행된 후에 딱 한번 호출된다.
     * */
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    /**
     * 각 테스트가 실행되기 전에 실행된다.
     * */
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    /**
     * 각 테스트가 실행된 후에 실행된다.
     * */
    @AfterEach
    void afterEach() {
        System.out.println("after Each");
    }
}