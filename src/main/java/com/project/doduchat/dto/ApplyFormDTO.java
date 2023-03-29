package com.project.doduchat.dto;

import com.project.doduchat.domain.Apply;
import com.project.doduchat.domain.Mentee;
import com.project.doduchat.domain.Mentor;
import com.project.doduchat.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyFormDTO {
  private Long id; // 식별자
  private String  matchTime1;
  private String matchTime2;
  private String  matchTime3;
  private int status;
  private LocalDateTime indate;
  private Mentee mentee;
  private Mentor mentor;
  private Question question;

  public static ApplyFormDTO applyDto(Apply apply){

    return new ApplyFormDTO(
            apply.getId(),
            apply.getMatchTime1(),
            apply.getMatchTime2(),
            apply.getMatchTime3(),
            apply.getStatus(),
            apply.getIndate(),
            apply.getMentee(),
            apply.getMentor(),
            apply.getQuestion()
    );
  }

  @Getter
  @NoArgsConstructor
  public static class GetApplyForm {
    private Long id;
    private String nickname;
    private String image;
    private String university;
    private String major;
    private List<QuestionsDto> questions = new ArrayList<>();

    public GetApplyForm(Mentor mentor) {
      this.id = mentor.getId();
      this.nickname = mentor.getNickname();
      this.university = mentor.getUniversity();
      this.image = mentor.getImage();
      this.major = mentor.getMajor();
      this.questions = mentor.getCategory().getQuestions().stream()
              .map(QuestionsDto::new)
              .collect(Collectors.toList());
    }
  }

  @Getter
  public static class QuestionsDto {
    private Long id;
    private String question;

    public QuestionsDto(Question question) {
      this.id = question.getId();
      this.question = question.getQuestion();
    }
  }

}
