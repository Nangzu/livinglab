package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "COLLABORATION")
public class Collaboration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COW_NUM")  // 대문자 컬럼 이름
    private Long cow_num;

    @ManyToOne
    @JoinColumn(name = "MARKET_CODE")  // 대문자 외래 키 이름
    private Market market;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;
}