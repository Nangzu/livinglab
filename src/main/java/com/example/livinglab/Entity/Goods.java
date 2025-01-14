package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GOODS")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GOODSNUM")  // 대문자 컬럼 이름
    private Long goodsnum;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @ManyToOne
    @JoinColumn(name = "MARKET_CODE")  // 대문자 외래 키 이름
    private Market market;

    @Column(name = "GOODS_NAME")  // 대문자 컬럼 이름
    private String goods_name;

    @Column(name = "PRICE")  // 대문자 컬럼 이름
    private int price;

    @Column(name = "TAG")  // 대문자 컬럼 이름
    private String tag;

    @Column(name = "DETAILS")  // 대문자 컬럼 이름
    private String details;

    @Column(name = "GOODS_OPTION")  // 대문자 컬럼 이름
    private String goods_option;

    @Column(name = "MAIN_IMAGE")  // 대문자 컬럼 이름
    private String main_image;
}