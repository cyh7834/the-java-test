package me.yoonho.inflearnthejavatest.study;

import me.yoonho.inflearnthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
