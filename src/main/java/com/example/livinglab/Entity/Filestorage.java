package com.example.livinglab.Entity;

import jakarta.persistence.*;

@Entity
public class Filestorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FILENAME")
    private String filename;
    @Column(name = "FILETYPE")
    private String filetype;

    @Lob
    @Column(name = "FILEDATA", columnDefinition = "BLOB")
    private byte[] filedata;

    @ManyToOne
    @JoinColumn(name = "GOODSNUM")  // 대문자 외래 키 이름
    private Goods goods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}