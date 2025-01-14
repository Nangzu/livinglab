package com.example.livinglab.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartNum;
    private Long goodsNum;
    private String goodsName;
    private int num;  // 장바구니에서 선택된 상품 수량
}