package com.example.livinglab.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USERID"),
        @UniqueConstraint(columnNames = "EMAIL")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NUM")  // 대문자 컬럼 이름
    private Long user_num;

    @Column(name = "USERID", nullable = false)  // 대문자 컬럼 이름
    private String userid;

    @JsonIgnore
    @Column(name = "PW", nullable = false)  // 대문자 컬럼 이름
    private String pw;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    @Column(name = "PHONE")  // 대문자 컬럼 이름
    private String phone;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(name = "EMAIL", nullable = false, unique = true)  // 대문자 컬럼 이름
    private String email;

    @Column(name = "ADDRESS")  // 대문자 컬럼 이름
    private String address;

    @Column(name = "USER_NAME", nullable = false)  // 대문자 컬럼 이름
    private String user_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_CODE", nullable = false)  // 대문자 외래 키 이름
    private Role role;


    // 역할 부여 메서드
    public void assignRole(Role newRole) {
        this.role = newRole;
    }

    // 역할 제거 메서드
    public void removeRole() {
        this.role = null; // 역할 제거 시 null로 설정
    }

    // 역할 확인 메서드
    public boolean hasRole(Long roleCode) {
        return this.role != null && this.role.getRoleCode().equals(roleCode);
    }
}