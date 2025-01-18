package com.example.livinglab.Dto;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.Filestorage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class GoodsSubDTO {
    private Long goodsnum;
    private int price;
    private String firstGoodsname;
    private byte[] firstFileData;
    private String marketname;


    public GoodsSubDTO(Long goodsnum, int price,String firstGoodsname, byte[] firstFileData, String marketname) {
        this.goodsnum = goodsnum;
        this.price = price;
        this.firstGoodsname = firstGoodsname;
        this.firstFileData = firstFileData;
        this.marketname = marketname;
    }


    // Getters and Setters
    public Long getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(Long goodsnum) {
        this.goodsnum = goodsnum;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getFirstGoodsname() {
        return firstGoodsname;
    }

    public void setFirstGoodsname(String firstGoodsname) {
        this.firstGoodsname = firstGoodsname;
    }

    public byte[] getFirstFileData() {
        return firstFileData;
    }

    public void setFirstFileData(byte[] firstFileData) {
        this.firstFileData = firstFileData;
    }

    public String getMarketname() {
        return marketname;
    }

    public void setMarketname(String firstGoodsname) {
        this.marketname = marketname;
    }
}
