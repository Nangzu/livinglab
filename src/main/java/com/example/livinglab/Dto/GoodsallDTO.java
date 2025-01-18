package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Filestorage;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Entity.Goodsdetail;
import com.example.livinglab.Entity.Market;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsallDTO {
    private Long goodsnum;  // 상품번호
    private String goodsname; // 상품명
    private int price;      // 가격
    private String tag;     // 태그
    private String details; // 상세 설명
    private String goodsOption; // 상품 옵션
    private Long detailsid;  // 고유 ID
    private String packagingtype;  // 포장 타입
    private String salesunit;  // 판매 단위
    private String weightcapacity;  // 중량 또는 용량
    private String expirydate;  // 소비 기한
    private String notes;  // 안내 사항
    private String filename1;
    private String filename2;
    private String filetype;
    private String filetype2;
    private byte[] filedata1;
    private byte[] filedata2;
    private Long marketcode;  // 마켓 코드
    private String marketname;  // 마켓 이름
    private Long usernum;
    private String userid;     // id
    private String phone;   // phone
    private String email;   // email
    private String address; // address
    private String username; // user_name

    public GoodsallDTO(Goods goods, Goodsdetail goodsdetail, Filestorage filestorage1, Filestorage filestorage2, Market market, User user) {
        this.goodsnum = goods.getGoodsnum();
        this.goodsname = goods.getGoodsname();
        this.price = goods.getPrice();
        this.tag = goods.getTag();
        this.details = goods.getDetails();
        this.goodsOption = goods.getGoodsoption();
        this.detailsid = goodsdetail != null ? goodsdetail.getDetailsid() : null;
        this.packagingtype = goodsdetail != null ? goodsdetail.getPackagingtype() : null;
        this.salesunit = goodsdetail != null ? goodsdetail.getSalesunit() : null;
        this.weightcapacity = goodsdetail != null ? goodsdetail.getWeightcapacity() : null;
        this.expirydate = goodsdetail != null ? goodsdetail.getExpirydate() : null;
        this.notes = goodsdetail != null ? goodsdetail.getNotes() : null;
        this.filename1 = filestorage1 != null ? filestorage1.getFilename() : null;
        this.filename2 = filestorage2 != null ? filestorage2.getFilename() : null;
        this.filetype = filestorage1 != null ? filestorage1.getFiletype() : null;
        this.filetype2 = filestorage2 != null ? filestorage2.getFiletype() : null;
        this.filedata1 = filestorage1 != null ? filestorage1.getFiledata() : null;
        this.filedata2 = filestorage2 != null ? filestorage2.getFiledata() : null;
        this.marketcode = market != null ? market.getMarketcode() : null;
        this.marketname = market != null ? market.getMarketname() : null;
        this.usernum = user != null ? user.getUsernum() : null;
        this.userid = user != null ? user.getUserid() : null;
        this.phone = user != null ? user.getPhone() : null;
        this.email = user != null ? user.getEmail() : null;
        this.address = user != null ? user.getAddress() : null;
        this.username = user != null ? user.getUsername() : null;
    }

    // Getters and Setters
    public Long getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(Long goodsnum) {
        this.goodsnum = goodsnum;
    }

    public Long getUsernum() {
        return usernum;
    }

    public void setUsernum(Long usernum) {
        this.usernum = usernum;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getGoodsOption() {
        return goodsOption;
    }

    public void setGoodsOption(String goodsOption) {
        this.goodsOption = goodsOption;
    }

    public Long getDetailsid() {
        return detailsid;
    }

    public void setDetailsid(Long detailsid) {
        this.detailsid = detailsid;
    }

    public String getPackagingtype() {
        return packagingtype;
    }

    public void setPackagingtype(String packagingtype) {
        this.packagingtype = packagingtype;
    }

    public String getSalesunit() {
        return salesunit;
    }

    public void setSalesunit(String salesunit) {
        this.salesunit = salesunit;
    }

    public String getWeightcapacity() {
        return weightcapacity;
    }

    public void setWeightcapacity(String weightcapacity) {
        this.weightcapacity = weightcapacity;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFilename1() {
        return filename1;
    }

    public void setFilename1(String filename1) {
        this.filename1 = filename1;
    }

    public String getFilename2() {
        return filename2;
    }

    public void setFilename2(String filename2) {
        this.filename2 = filename2;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFiletype2() {
        return filetype2;
    }

    public void setFiletype2(String filetype2) {
        this.filetype2 = filetype2;
    }

    public byte[] getFiledata1() {
        return filedata1;
    }

    public void setFiledata1(byte[] filedata1) {
        this.filedata1 = filedata1;
    }

    public byte[] getFiledata2() {
        return filedata2;
    }

    public void setFiledata2(byte[] filedata2) {
        this.filedata2 = filedata2;
    }

    public Long getMarketcode() {
        return marketcode;
    }

    public void setMarketcode(Long marketcode) {
        this.marketcode = marketcode;
    }

    public String getMarketname() {
        return marketname;
    }

    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}