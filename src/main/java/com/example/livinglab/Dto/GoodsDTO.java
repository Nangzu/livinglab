package com.example.livinglab.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO {
    private Long goodsNum;
    private String goodsName;
    private int price;
    private String tag;
    private String details;
    private String goodsOption;
    private String mainImage;
}