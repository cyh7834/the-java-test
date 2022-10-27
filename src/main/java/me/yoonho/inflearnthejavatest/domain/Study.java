package me.yoonho.inflearnthejavatest.domain;

import me.yoonho.inflearnthejavatest.study.StudyStatus;

import java.time.LocalDateTime;

public class Study {
    private StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;

    private String name;

    private Member owner;

    private LocalDateTime openedDateTime;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("스터디 정원은 0보다 커야 한다.");
        }
        this.limit = limit;
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.studyStatus = StudyStatus.OPENED;
    }

    public LocalDateTime getOpenedDateTime() {
        return openedDateTime;
    }

    public StudyStatus getStatus() {
        return this.studyStatus;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getName() {
        return this.name;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Study{" +
                "studyStatus=" + studyStatus +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
