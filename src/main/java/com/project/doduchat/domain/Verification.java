package com.project.doduchat.domain;

import com.project.doduchat.domain.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "verifications")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verfication_id")
    private Long id; // 식별자
    private String file;
    @Column(name = "status")
    private int status;
    public VerificationStatus getStatusEnum(){
        return VerificationStatus.fromStatusCode(status);
    }

    private LocalDateTime indate;

    @OneToOne
    @JoinColumn(name = "mentor_id", unique = true)
    private Mentor mentor;

    public void update(int status){
        this.status = status;
    }

}
