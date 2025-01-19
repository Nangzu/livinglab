package com.example.livinglab.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GOODS_DETAILS")
public class Goodsdetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAILS_ID")
    private Long detailsid;  // 고유 ID

    @ManyToOne
    @JoinColumn(name = "GOODSNUM")  // GOODS 테이블과 연관
    private Goods goods;  // 상품 정보

    @Column(name = "PACKAGING_TYPE")
    private String packagingtype;  // 포장 타입

    @Column(name = "SALES_UNIT")
    private String salesunit;  // 판매 단위

    @Column(name = "WEIGHT_CAPACITY")
    private String weightcapacity;  // 중량 또는 용량

    @Column(name = "EXPIRY_DATE")
    private String expirydate;  // 소비 기한

    @Column(name = "NOTES")
    private String notes;  // 안내 사항
}