package com.project.doduchat.domain.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDTO implements Serializable {
    private String question;
}
