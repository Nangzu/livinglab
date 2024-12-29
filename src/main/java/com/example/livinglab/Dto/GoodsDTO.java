package com.example.livinglab.Dto;
import com.example.livinglab.Entity.Goods;

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

    public GoodsDTO(Goods goods) {
        this.goodsNum = goods.getGoods_num();
        this.goodsName = goods.getGoods_name();
        this.price = goods.getPrice();
        this.tag = goods.getTag();
        this.details = goods.getDetails();
        this.goodsOption = goods.getGoods_option();
        this.mainImage = goods.getMain_image();
    }

}