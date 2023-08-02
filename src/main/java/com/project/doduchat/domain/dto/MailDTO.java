package com.project.doduchat.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {
    private String address;
    private String title;
    private String message;
}
