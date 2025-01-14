package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "CART_NUM")  // 대문자 외래 키 이름
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;
}