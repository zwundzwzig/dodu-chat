package com.project.doduchat.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wallet_id")
  private Long id; // 식별자

  @Size(min = 3, max = 15)
  private String point;

  private LocalDateTime indate; // 생성일

}
