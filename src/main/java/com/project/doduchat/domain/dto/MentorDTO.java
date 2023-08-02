package com.project.doduchat.domain.dto;

import com.project.doduchat.domain.Mentor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MentorDTO {

  private Long id;
  private String email;
  private String nickname;

  private String phone;

  private int age;

  private String gender;

  private LocalDateTime indate; // 생성일

  private String image;

  private String university;

  private String major;

  private String status;

  private int matchCount;

  private String category;

  public MentorDTO(Mentor entity) {
    this.id = entity.getId();
    this.nickname = entity.getNickname();
    this.image = entity.getImage();
    this.university = entity.getUniversity();
    this.category = entity.getCategory().getTitle();
    this.major = entity.getMajor();
    this.matchCount = entity.getMatchCount();
  }
}

