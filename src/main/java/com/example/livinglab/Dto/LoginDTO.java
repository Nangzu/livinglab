package com.example.livinglab.Dto;

import com.example.livinglab.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String userid;
    private String pw;

    public LoginDTO(User user) {
        this.userid = user.getUserid();
        this.pw = user.getPw();
    }
}