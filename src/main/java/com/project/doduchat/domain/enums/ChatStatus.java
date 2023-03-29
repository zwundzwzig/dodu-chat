package com.project.doduchat.domain.enums;

import lombok.Getter;

@Getter
public enum ChatStatus {
    BEFORECHAT(0, "진행전"),
    CHATTING(1, "진행중"),
    CHATFINISHED(2,"종료");
    private final int statusCode;
    private final String status;
    ChatStatus(int statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }
    public static ChatStatus fromStatusCode(int statusCode){
        for(ChatStatus c : values()){
            if(c.statusCode == statusCode){
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid statusCode: " + statusCode);
    }
}
