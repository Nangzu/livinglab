package com.example.livinglab.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String role_code;

    private String user;
    private String seller;
    private String student;
    private String admin;
}
