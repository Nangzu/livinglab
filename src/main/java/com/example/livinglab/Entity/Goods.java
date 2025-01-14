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
    private Long goodsnum;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    @ManyToOne
    @JoinColumn(name = "market_code")
    private Market market;

    private String goods_name;
    private int price;
    private String tag;
    private String details;
    private String goods_option;
    private String main_image;

}
