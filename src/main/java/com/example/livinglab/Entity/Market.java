package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

// Market Entity
@Entity
@Getter
@Setter
@IdClass(MarketId.class)
@Table(name = "MARKET")
public class Market {
    @Id
    private String market_code;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private String market_name;
}