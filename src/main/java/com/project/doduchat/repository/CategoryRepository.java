package com.project.doduchat.repository;

import com.project.doduchat.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
  List<Category> findAll();
}
