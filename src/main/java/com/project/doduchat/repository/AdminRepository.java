package com.project.doduchat.repository;

import com.project.doduchat.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByAdminNameAndPassword(String adminName, String password);
    Optional<Admin> findByAdminName(String adminName);
}
