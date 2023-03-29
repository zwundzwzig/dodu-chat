package com.project.doduchat.repository;

import com.project.doduchat.domain.Mentor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MentorRepository extends CrudRepository<Mentor, Long> {
  List<Mentor> findAllByCategoryId(Long id);

  Optional<Mentor> findById(Long id);

  @Query(value = "select m from Mentor m join Verification v on m.id=v.mentor.id and v.status=3")
  List<Mentor> findAllByStatus(int status);

  List<Mentor> findAll(Sort sort);

  List<Mentor> findAllByStatusOrStatusOrderByIdAsc(int status1, int status2); // 번호순
  List<Mentor> findAllByStatusOrStatusOrderByNicknameAsc(int status1, int status2); // 이름순
  List<Mentor> findAllByStatusOrStatusOrderByUniversityAsc(int status1, int status2); // 대학순
  List<Mentor> findAllByStatusOrStatusOrderByMajorAsc(int status1, int status2); // 전공순
  List<Mentor> findAllByStatusOrStatusOrderByGenderAsc(int status1, int status2); // 성별순

}