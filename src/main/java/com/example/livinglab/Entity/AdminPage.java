package com.example.livinglab.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ADMIN_PAGE")
public class AdminPage {

    @Id
    @Column(name = "MARKET_CODE")  // 대문자 컬럼 이름
    private String marketCode;

    @OneToOne
    @JoinColumn(name = "MARKET_CODE", insertable = false, updatable = false)  // 대문자 외래 키 이름
    private Market market;

    @Column(name = "PAGE_CANCEL")  // 대문자 컬럼 이름
    private String pageCancel;

    @Column(name = "REFUND")  // 대문자 컬럼 이름
    private String refund;

    @Column(name = "EXCH")  // 대문자 컬럼 이름
    private String exch;

    @Column(name = "PAGE_ORDER")  // 대문자 컬럼 이름
    private String pageOrder;

    @Column(name = "PAYMENT")  // 대문자 컬럼 이름
    private String payment;

    @Column(name = "DELI")  // 대문자 컬럼 이름
    private String deli;

    @Column(name = "SALES")  // 대문자 컬럼 이름
    private String sales;
}