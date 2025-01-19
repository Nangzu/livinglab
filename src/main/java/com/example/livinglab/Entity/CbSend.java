package com.example.livinglab.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "CB_SEND")
public class CbSend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEND_NUM")
    private Long sendNum;

    @ManyToOne
    @JoinColumn(name = "COW_NUM")
    private Collaboration collaboration;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")
    private User user;

    @Lob // BLOB 컬럼 매핑
    @Column(name = "FILES")
    private byte[] files;

    @Column(name = "FILE_NAME")
    private String fileName;

    @ManyToOne
    @JoinColumn(name ="MARKETCODE")
    private Market market;

    @Column(name="ONE_LINER")
    private String oneliner;
}
