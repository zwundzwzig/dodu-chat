package com.project.doduchat.repository;

import com.project.doduchat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
  List<Chat> findAllByMentee_Id(Long id);
}
