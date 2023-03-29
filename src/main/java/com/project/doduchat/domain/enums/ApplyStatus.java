package com.project.doduchat.domain.enums;

import lombok.Getter;

@Getter
public enum ApplyStatus {
    NOTACCEPTED(0, "미수락"),
    ACCEPTED(1, "수락");
    private final int statusCode;
    private final String status;

    ApplyStatus(int statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }
    public static ApplyStatus fromStatusCode(int statusCode) {
        for (ApplyStatus a : values()) {
            if (a.statusCode == statusCode) {
                return a;
            }
        }
        throw new IllegalArgumentException("Invalid statusCode: " + statusCode);
    }
}
