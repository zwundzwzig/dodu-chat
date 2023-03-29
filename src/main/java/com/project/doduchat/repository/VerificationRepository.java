package com.project.doduchat.repository;

import com.project.doduchat.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

    List<Verification> findAllByStatusNot(int status);
//    List<Verification> findAll(Sort sort);
    Optional<Verification> findByMentorId(Long mentor_id);



}
