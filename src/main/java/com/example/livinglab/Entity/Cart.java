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
    private Long cartnum;

    @ManyToOne
    @JoinColumn(name = "goods_num")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private int num;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Order> orders;  // 여러 개의 주문이 이 카트에 연결될 수 있음
}