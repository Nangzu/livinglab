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
    @Column(name = "SEND_NUM")  // 대문자 컬럼 이름
    private Long send_num;

    @ManyToOne
    @JoinColumn(name = "COW_NUM")  // 대문자 외래 키 이름
    private Collaboration collaboration;

    @ManyToOne
    @JoinColumn(name = "USER_NUM")  // 대문자 외래 키 이름
    private User user;

    @Column(name = "FILES")  // 대문자 컬럼 이름
    private String files;
}