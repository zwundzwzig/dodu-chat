package com.project.doduchat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "mentees")
public class Mentee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mentee_id")
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

  private String address;

  @CreationTimestamp
  private LocalDateTime indate; // 생성일

  @Builder
  public Mentee(Long id, String password, String email, String nickname, String phone, String gender, int age) {
    this.id = id;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
    this.phone = phone;
    this.gender = gender;
    this.age = age;
  }


  public Mentee update(String nickname, String gender, String phone, int age) {
    this.nickname = nickname;
    this.gender = gender;
    this.phone = phone;
    this.age = age;
    return this;
  }



  //구글
    public Mentee update2(String nickname) {
      this.nickname=nickname;
      return this;
    }
}
