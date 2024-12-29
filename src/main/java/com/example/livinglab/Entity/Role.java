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

    public String getRoleName() {
        if (this.user != null) {
            return "user";
        } else if (this.seller != null) {
            return "seller";
        } else if (this.student != null) {
            return "student";
        } else if (this.admin != null) {
            return "admin";
        }
        return null;  // 역할이 설정되지 않은 경우
    }
}
