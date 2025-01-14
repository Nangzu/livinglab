package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ADMIN_PAGE")
public class AdminPage {
    @Id
    @Column(name = "market_code")
    private String marketCode;

    @OneToOne
    @JoinColumn(name = "market_code", insertable = false, updatable = false)
    private Market market;

    private String page_cancel;
    private String refund;
    private String exch;
    private String page_order;
    private String payment;
    private String deli;
    private String sales;
}