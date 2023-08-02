package com.project.doduchat.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatLogDTO {

    private Long id;

    private String send_id;
    private String content;
    private LocalDateTime indate;

    private Long chat_id;

    public ChatLogDTO(String send_id, String content, LocalDateTime indate, Long chat_id) {
        this.send_id = send_id;
        this.content = content;
        this.indate = indate;
        this.chat_id = chat_id;
    }
}
