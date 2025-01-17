package com.example.livinglab.Dto;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.Filestorage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class GoodsSubDTO {
    private String goodsname;
    private byte[] filedata;

    public GoodsSubDTO(Goods goods, Filestorage filestorage){
        this.goodsname = goods.getGoodsname();
        this.filedata = filestorage.getFiledata();
    }
}
