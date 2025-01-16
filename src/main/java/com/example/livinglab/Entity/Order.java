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
    private Long order_num;

    @Column(name = "PY_METHOD")  // 대문자 컬럼 이름
    private String py_method;

    @ManyToOne
    @JoinColumn(name = "CARTNUM")  // 대문자 외래 키 이름
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  // 여러 카트를 처리하도록 설정
    private List<Cart> carts;  // 여러 카트를 주문에 추가
}