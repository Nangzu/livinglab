package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@IdClass(CartId.class)
@Table(name = "CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_num;

    @Id
    @ManyToOne
    @JoinColumn(name = "goods_num")
    private Goods goods;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private int num;
}