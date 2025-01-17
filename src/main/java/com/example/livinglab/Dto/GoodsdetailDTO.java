package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Goodsdetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsdetailDTO {
    private Long detailsid;  // 고유 ID
    private Long goodsnum;  // 상품 번호
    private String packagingtype;  // 포장 타입
    private String salesunit;  // 판매 단위
    private String weightcapacity;  // 중량 또는 용량
    private String expirydate;  // 소비 기한
    private String notes;  // 안내 사항

    // 생성자에서 GoodsDetails 객체를 DTO로 변환
    public GoodsdetailDTO(Goodsdetail goodsdetail) {
        this.detailsid = goodsdetail.getDetailsid();
        this.goodsnum = goodsdetail.getGoods() != null ? goodsdetail.getGoods().getGoodsnum() : null;
        this.packagingtype = goodsdetail.getPackagingtype();
        this.salesunit = goodsdetail.getSalesunit();
        this.weightcapacity = goodsdetail.getWeightcapacity();
        this.expirydate = goodsdetail.getExpirydate();
        this.notes = goodsdetail.getNotes();
    }
}