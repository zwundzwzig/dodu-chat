package com.project.doduchat.dto;

import com.project.doduchat.domain.Category;
import com.project.doduchat.domain.Mentor;
import com.project.doduchat.domain.Question;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDTO {

  private Long id;
  private String title;
  private List<Mentor> mentors;
  private List<Question> questions;

  public CategoryDTO(Category entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.mentors = entity.getMentors();
    this.questions = entity.getQuestions();
  }
}