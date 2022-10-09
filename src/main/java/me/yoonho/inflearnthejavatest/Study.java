package me.yoonho.inflearnthejavatest;

public class Study {
    private StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("스터디 정원은 0보다 커야 한다.");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.studyStatus;
    }

    public int getLimit() {
        return this.limit;
    }
}
