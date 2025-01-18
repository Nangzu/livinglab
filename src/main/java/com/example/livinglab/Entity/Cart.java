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
    @JoinColumn(name = "GOODSNUM")  // 대문자 외래 키 이름
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @Column(name = "QUANTITY")  // 대문자 컬럼 이름
    private int quantity;

    @ManyToOne  // 'Order' 엔티티와의 관계를 설정
    @JoinColumn(name = "ORDER_NUM")  // Cart에서 외래 키 관리
    private Order order;  // 이 카트는 하나의 주문에 속함

    @Column(name = "TOTAL_PRICE")  // 대문자 컬럼 이름
    private int totalprice;

    @Column(name = "GOODSNAME")  // 대문자 컬럼 이름
    private String goodsname;
}