package com.example.livinglab.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARTNUM")  // 대문자 컬럼 이름
    private Long cartnum;

    @ManyToOne
    @JoinColumn(name = "GOODS_NUM")  // 대문자 외래 키 이름
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @Column(name = "NUM")  // 대문자 컬럼 이름
    private int num;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Order> orders;  // 여러 개의 주문이 이 카트에 연결될 수 있음
}