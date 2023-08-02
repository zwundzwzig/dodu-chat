package com.project.doduchat.domain.dto;

import com.project.doduchat.domain.Apply;
import com.project.doduchat.domain.Mentee;
import com.project.doduchat.domain.Mentor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApplyResultDTO {
  private Long id;
  private String matchTime1;
  private String matchTime2;
  private String matchTime3;
  private int status;
  private LocalDateTime indate;
  private Mentee mentee;
  private Mentor mentor;
  private String category;
  private String question;
  private String selectedMatchTime;

  public ApplyResultDTO(Apply entity) {
    this.id = entity.getId();
    this.matchTime1 = entity.getMatchTime1();
    this.matchTime2 = entity.getMatchTime2();
    this.matchTime3 = entity.getMatchTime3();
    this.status = entity.getStatus();
    this.indate = entity.getIndate();
    this.mentee = entity.getMentee();
    this.mentor = entity.getMentor();
    this.category = entity.getMentor().getCategory().getTitle();
    this.question = entity.getQuestion().getQuestion();
  }
}
