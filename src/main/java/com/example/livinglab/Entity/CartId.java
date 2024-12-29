package com.example.livinglab.Entity;
import lombok.*;

@Getter
@Setter
public class CartId implements java.io.Serializable {
    private Long cart_num;
    private Long goods;
    private Long user;
}