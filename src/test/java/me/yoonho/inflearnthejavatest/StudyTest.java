package me.yoonho.inflearnthejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
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