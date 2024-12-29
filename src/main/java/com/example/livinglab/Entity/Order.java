package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_num;

    private String py_method;

    @ManyToOne
    @JoinColumn(name = "cart_num")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;
}