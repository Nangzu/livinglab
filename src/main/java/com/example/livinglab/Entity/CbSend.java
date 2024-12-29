package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "CB_SEND")
public class CbSend {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "cow_num")
    private Collaboration collaboration;

    private String file;
}