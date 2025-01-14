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
    private Long send_num;

    @ManyToOne
    @JoinColumn(name = "cow_num")
    private Collaboration collaboration;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    private String file;
}