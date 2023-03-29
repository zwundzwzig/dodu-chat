package com.project.doduchat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Choice implements Serializable {
    private String text;
    private Integer index;

    @JsonProperty("finish_reason")
    private String finishReason;

    @Builder
    public Choice(String text, Integer index, String finishReason){
        this.index = index;
        this.text = text;
        this.finishReason = finishReason;

    }
}
