package com.project.doduchat.dto;

import com.project.doduchat.domain.Mentee;
import com.project.doduchat.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SaveApplyDTO {
    private Long id;
    private Timestamp matchTime1;
    private Timestamp matchTime2;
    private Timestamp matchTime3;
    private int status;
    private LocalDateTime indate;
    private Mentee mentee;
    private Question question;

    @Builder
    public SaveApplyDTO(Long id, Timestamp matchTime1, Timestamp matchTime2, Timestamp matchTime3,
                        int status, LocalDateTime indate, Mentee mentee, Question question) {
        this.id = id;
        this.matchTime1 = matchTime1;
        this.matchTime2 = matchTime2;
        this.matchTime3 = matchTime3;
        this.status = status;
        this.indate = indate;
        this.mentee = mentee;
        this.question = question;

    }

//    public Apply toEntity(){
//        return Apply.builder()
//                .id(id)
//                .matchTime1(matchTime1)
//                .matchTime2(matchTime2)
//                .matchTime3(matchTime3)
//                .status(status)
//                .indate(indate)
//                .mentee(mentee)
//                .build();
//    }
}
