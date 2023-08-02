package com.project.doduchat.domain.dto;

import com.project.doduchat.domain.Mentee;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MenteeDTO {

    private Long id;
    private String email;
    private String nickname;

    private String phone;

    private int age;

    private String gender;

    private LocalDateTime indate; // 생성일

    //private Wallet wallet;

    public MenteeDTO(Mentee entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.phone = entity.getPhone();
        this.age = entity.getAge();
        this.gender = entity.getGender();
        this.indate = entity.getIndate();
    }
}
