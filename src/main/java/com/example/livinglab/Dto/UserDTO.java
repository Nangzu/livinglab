package com.example.livinglab.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long user_num;
    private String id;
    private String phone;
    private String email;
    private String address;
    private String user_name;
    private String role;
}
