package com.project.doduchat.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.doduchat.domain.enums.MentorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Getter
@Table(name = "mentors")
public class Mentor implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mentor_id")
  private Long id;

  @Column(nullable = true)
  private String password; // 비밀번호

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String nickname;

  private String phone;

  private int age;

  private String gender;

  private LocalDateTime indate; // 생성일

  private String image;

  private String university;

  private String major;

  @Column(name = "status")
  private int status;

  public MentorStatus getStatusEnum(){
    return MentorStatus.fromStatusCode(status);
  }
  private int matchCount;

  @ManyToOne//(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  @ToString.Exclude
  private Category category;

  @OneToOne
  @JoinColumn(name = "wallet_id")
  private Wallet wallet;

  public void update(int status){
    this.status = status;
  }

}
