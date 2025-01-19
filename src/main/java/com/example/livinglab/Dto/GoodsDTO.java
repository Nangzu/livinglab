package com.example.livinglab.Dto;
import com.example.livinglab.Entity.Goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO {
    private Long goodsnum;  // 상품번호
    @JsonProperty("usernum") // JSON에서 'userNum'을 매핑
    private Long usernum;
    @JsonProperty("marketCode") // JSON에서 'marketCode'를 매핑
    private Long marketCode;
    private String goodsname; // 상품명
    private int price;      // 가격
    private String tag;     // 태그
    private String details; // 상세 설명
    private String goodsOption; // 상품 옵션


    public GoodsDTO(Goods goods) {
        this.goodsnum = goods.getGoodsnum();
        this.usernum = goods.getUser().getUsernum();
        this.marketCode = goods.getMarket().getMarketcode();
        this.goodsname = goods.getGoodsname();
        this.price = goods.getPrice();
        this.tag = goods.getTag();
        this.details = goods.getDetails();
        this.goodsOption = goods.getGoodsoption();
    }
}