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
    private Long role_code;

    private String users;
    private String seller;
    private String student;
    private String admini;

    public String getRoleName() {
        if (this.users != null) {
            return "users";
        } else if (this.seller != null) {
            return "seller";
        } else if (this.student != null) {
            return "student";
        } else if (this.admini != null) {
            return "admini";
        }
        return null;  // 역할이 설정되지 않은 경우
    }
}
