package com.project.doduchat.repository;

import com.project.doduchat.domain.Matching;
import com.project.doduchat.domain.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

  List<Matching> findAllByApply_Mentor(Mentor mentor);
  List<Matching> findAllByApply_Mentee_Id(Long id);
}
