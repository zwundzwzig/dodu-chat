package com.project.doduchat.repository;

import com.project.doduchat.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
