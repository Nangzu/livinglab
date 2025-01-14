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
        @UniqueConstraint(columnNames = "userid"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_num;

    @Column(nullable = false, unique = true)
    private String userid;

    @JsonIgnore
    @Column(nullable = false)
    private String pw;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    @Column(nullable = false)
    private String user_name;

    @ManyToOne
    @JoinColumn(name = "role_code", nullable = false)
    private Role role;
}
