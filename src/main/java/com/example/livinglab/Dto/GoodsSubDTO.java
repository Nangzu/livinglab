package com.example.livinglab.Dto;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.Filestorage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class GoodsSubDTO {
    private String firstGoodsname;
    private byte[] firstFileData;

    public GoodsSubDTO(String firstGoodsname, byte[] firstFileData) {
        this.firstGoodsname = firstGoodsname;
        this.firstFileData = firstFileData;
    }

    // Getters and Setters
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
}
