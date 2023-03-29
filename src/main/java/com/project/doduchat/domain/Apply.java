package com.project.doduchat.domain;

import com.project.doduchat.domain.enums.ApplyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applys")
@Getter
@Setter
public class Apply {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "apply_id")
  private Long id; // 식별자

  private String matchTime1;
  private String matchTime2;
  private String matchTime3;
  @Column(name = "status")
  private int status;
  public ApplyStatus getStatusEnum(){
    return ApplyStatus.fromStatusCode(status);
  }
  @CreationTimestamp
  private LocalDateTime indate;

  @ManyToOne
  @JoinColumn(name = "mentee_id")
  private Mentee mentee;

  @ManyToOne
  @JoinColumn(name = "mentor_id")
  private Mentor mentor;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

}
