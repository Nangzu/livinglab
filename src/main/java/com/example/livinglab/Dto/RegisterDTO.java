package com.example.livinglab.Dto;
import com.example.livinglab.Entity.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private Long usernum;  // user_num
    private String userid;     // id
    private String phone;   // phone
    private String email;   // email
    private String address; // address
    private String username; // user_name
    private String pw;
    private String marketname;
}