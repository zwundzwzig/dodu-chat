package com.project.doduchat.repository;

import com.project.doduchat.domain.Apply;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyListRepository extends JpaRepository<Apply, Long> {
  List<Apply> findAllByIdOrderByIndateDesc(Long id);

  List<Apply> findAllByMenteeId(Long id);
  List<Apply> findAll(Sort sort);

  List<Apply> findAllByMentee_IdAndMentor_Id(Long menteeId, Long mentorId);

  Optional<Apply> findByMentee_IdAndMentor_Id(Long menteeId, Long mentorId);

}
