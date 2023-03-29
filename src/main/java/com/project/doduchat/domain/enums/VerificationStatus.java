package com.project.doduchat.domain.enums;

import lombok.Getter;

@Getter
public enum VerificationStatus {
    UNDEFINED(0, "미확인"),
    VERIFYING(1, "검토중"),
    SENDBACK(2, "반려"),
    PASSED(3, "통과");

    private final int statusCode;
    private final String status;

    VerificationStatus(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public static VerificationStatus fromStatusCode(int statusCode){
        for(VerificationStatus v : values()){
            if(v.statusCode == statusCode){
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid statusCode: " + statusCode);
    }
}
