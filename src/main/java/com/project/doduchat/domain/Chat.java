package com.project.doduchat.domain;

import com.project.doduchat.domain.enums.ChatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    @ToString.Exclude
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    @ToString.Exclude
    private Mentee mentee;

    @Column(name="status")
    private int status;
    public ChatStatus getStatusEnum(){
        return ChatStatus.fromStatusCode(status);
    }

    private String startTime;

    private String finishTime;

}
