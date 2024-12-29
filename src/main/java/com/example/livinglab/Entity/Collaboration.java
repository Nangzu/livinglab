package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Table(name = "COLLABORATION")
public class Collaboration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cow_num;

    @Id
    @ManyToOne
    @JoinColumn(name = "market_code")
    private Market market;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;
}