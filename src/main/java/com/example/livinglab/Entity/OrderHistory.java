package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ORDER_HISTORY")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_NUM")  // 대문자 컬럼 이름
    private Long historynum;

    @ManyToOne
    @JoinColumn(name = "ORDER_NUM")  // 대문자 외래 키 이름
    private Order order;

    @Column(name = "TOTAL")  // 대문자 컬럼 이름
    private int total;
}