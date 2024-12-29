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
    private Long goods_num;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private String goods_name;
    private int price;
    private String tag;
    private String details;
    private String goods_option;
    private String main_image;

    @ManyToOne
    @JoinColumn(name = "market_code")
    private Market market;
}
