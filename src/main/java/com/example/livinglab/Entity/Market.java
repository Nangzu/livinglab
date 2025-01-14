package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

// Market Entity
@Entity
@Getter
@Setter
@Table(name = "MARKET")
public class Market {
    @Id
    @Column(name = "market_code", unique = true)
    private String market_code;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private String market_name;
}