package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_NUM")  // 대문자 컬럼 이름
    private Long ordernum;

    @Column(name = "PY_METHOD")  // 대문자 컬럼 이름
    private String pymethod;

    @Column(name = "STATE")  // 대문자 컬럼 이름
    private String state;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @Column(name = "TOTAL_PRICE")  // 대문자 컬럼 이름
    private int totalprice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  // 여러 카트를 처리하도록 설정
    private List<Cart> carts;  // 여러 카트를 주문에 추가
}