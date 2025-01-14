package com.example.livinglab.Dto;
import com.example.livinglab.Entity.Goods;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO {
    private Long goodsnum;  // 상품번호
    private Long userNum;   // 사용자 번호
    private Long marketCode; // 마켓 코드
    private String goodsName; // 상품명
    private int price;      // 가격
    private String tag;     // 태그
    private String details; // 상세 설명
    private String goodsOption; // 상품 옵션
    private String mainImage;  // 대표 이미지

    public GoodsDTO(Goods goods) {
        this.goodsnum = goods.getGoodsnum();
        this.userNum = goods.getUser().getUser_num();
        this.marketCode = goods.getMarket().getMarket_code();
        this.goodsName = goods.getGoods_name();
        this.price = goods.getPrice();
        this.tag = goods.getTag();
        this.details = goods.getDetails();
        this.goodsOption = goods.getGoods_option();
        this.mainImage = goods.getMain_image();
    }

}