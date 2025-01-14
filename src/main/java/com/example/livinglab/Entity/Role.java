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
    @Column(name = "ROLE_CODE")  // 대문자 컬럼 이름
    private Long role_code;

    @Column(name = "USERS")  // 대문자 컬럼 이름
    private String users;

    @Column(name = "SELLER")  // 대문자 컬럼 이름
    private String seller;

    @Column(name = "STUDENT")  // 대문자 컬럼 이름
    private String student;

    @Column(name = "ADMINI")  // 대문자 컬럼 이름
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