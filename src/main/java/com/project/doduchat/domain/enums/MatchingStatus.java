package com.project.doduchat.domain.enums;

import lombok.Getter;

@Getter
public enum MatchingStatus {
    BEFOREMATCHING(0, "매칭전"),
    MATCHINGFAILTURE(1, "매칭실패"),
    MATCHINGCOMPLETE(2, "매칭완료");

    private final int statusCode;
    private final String status;

    MatchingStatus(int statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }
    public static MatchingStatus fromStatusCode(int statusCode){
        for(MatchingStatus m : values()){
            if(m.statusCode == statusCode){
                return m;
            }
        }
        throw new IllegalArgumentException("Invalid statusCode: " + statusCode);
    }
}
