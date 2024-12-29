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
    private Long history_num;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_num")
    private Order order;

    private int total;
}