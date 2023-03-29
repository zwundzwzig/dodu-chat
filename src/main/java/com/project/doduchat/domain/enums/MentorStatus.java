package com.project.doduchat.domain.enums;

import lombok.Getter;

@Getter
public enum MentorStatus {
    VERIFICATIONSTEP(0, "검증단계"),
    REGCOMPLETE(1, "가입완료"),
    BLOCK(9, "블랙");
    private final int statusCode;
    private final String status;

    MentorStatus(int statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }
    public static MentorStatus fromStatusCode(int statusCode){
        for(MentorStatus m : values()){
            if(m.statusCode == statusCode){
                return m;
            }
        }
        throw new IllegalArgumentException("Invalid statusCode: " + statusCode);
    }
}
