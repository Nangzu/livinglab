package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartnum;  // 카트 번호

    @JsonProperty("goodsnum")  // JSON에서 'goodsNum'을 매핑
    private Long goodsnum;  // 상품 번호

    @JsonProperty("goodsname")  // JSON에서 'goodsNum'을 매핑
    private String goodsname;  // 상품 이름

    @JsonProperty("usernum")  // JSON에서 'userNum'을 매핑
    private Long usernum; // 사용자 번호

    private int quantity;   // 수량

    private int price;

    private int totalprice;

    private byte[] filedata;


    // Cart 엔티티에서 DTO로 변환하는 생성자
    public CartDTO(Cart cart, byte[] filedata) {
        this.cartnum = cart.getCartnum();
        this.goodsnum = (cart.getGoods() != null) ? cart.getGoods().getGoodsnum() : null;
        this.goodsname = (cart.getGoods() != null) ? cart.getGoods().getGoodsname() : "Unknown";
        this.usernum = (cart.getUser() != null) ? cart.getUser().getUsernum() : null;
        this.quantity = cart.getQuantity();
        this.price = (cart.getGoods() != null) ? cart.getGoods().getPrice() : 0;
        this.totalprice = cart.getTotalprice();
        this.filedata = filedata;
    }



    // Getters and Setters

    public Long getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(Long goodsnum) {
        this.goodsnum = goodsnum;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }
}