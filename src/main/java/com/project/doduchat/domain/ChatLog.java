package com.project.doduchat.domain;

import com.project.doduchat.dto.ChatLogDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "chat_logs")
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatlog_id")
    private Long id;

    private String sendId;

    private String content;

    private LocalDateTime indate;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public static ChatLog toChatLogEntity(ChatLogDTO chatLogDTO, Chat chat){
        ChatLog chatLog = new ChatLog();

        chatLog.setChat(chat);
        chatLog.setSendId(chatLogDTO.getSend_id());
        chatLog.setContent(chatLogDTO.getContent());
        return chatLog;

    }

}
