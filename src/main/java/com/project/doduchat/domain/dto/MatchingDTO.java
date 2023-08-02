package com.project.doduchat.domain.dto;

import com.project.doduchat.domain.Apply;
import com.project.doduchat.domain.Matching;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MatchingDTO {

  private Long id;
  private String selectedMatchTime;
  private int status;
  private Apply apply;

  public MatchingDTO(Matching entity) {
    this.id = entity.getId();
    this.selectedMatchTime = entity.getSelectedMatchTime();
    this.status = entity.getStatus();
    this.apply = entity.getApply();
  }

  public MatchingDTO(List<Matching> match) {
  }
}
