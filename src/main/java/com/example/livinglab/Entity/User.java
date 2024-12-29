package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_num;

    private String id;
    private String pw;
    private String phone;
    private String email;
    private String address;
    private String user_name;

    @ManyToOne
    @JoinColumn(name = "role_code")
    private Role role;
}
