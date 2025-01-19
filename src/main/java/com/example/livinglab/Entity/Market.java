package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

// Market Entity
@Entity
@Getter
@Setter
@Table(name = "MARKET")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_CODE", unique = true)  // 대문자 컬럼 이름
    private Long marketcode;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @Column(name = "MARKET_NAME")  // 대문자 컬럼 이름
    private String marketname;
}