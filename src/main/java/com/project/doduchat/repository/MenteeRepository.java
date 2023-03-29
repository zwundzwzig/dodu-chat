package com.project.doduchat.repository;

import com.project.doduchat.domain.Mentee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    // 회원가입 시 이메일로 중복된 회원이 있는지 검사하는 쿼리 메서드
    Optional<Mentee> findByEmail(String email);
    List<Mentee> findAll(Sort sort);
}
