package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Cart;
import lombok.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartNum;  // 장바구니 번호
    private Long goodsNum;  // 상품 번호
    private String goodsName;  // 상품 이름
    private int quantity;  // 수량

    // Cart 엔티티를 기반으로 DTO 생성
    public CartDTO(Cart cart) {
        this.cartNum = cart.getCartnum();
        this.goodsNum = cart.getGoods() != null ? cart.getGoods().getGoodsnum() : null;
        this.goodsName = cart.getGoods() != null ? cart.getGoods().getGoods_name() : null;
        this.quantity = cart.getNum();
    }
}