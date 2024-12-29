package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ADMIN_PAGE")
public class AdminPage {
    @Id
    @ManyToOne
    @JoinColumn(name = "market_code")
    private Market market;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private String page_cancel;
    private String refund;
    private String exch;
    private String page_order;
    private String payment;
    private String deli;
    private String sales;
}