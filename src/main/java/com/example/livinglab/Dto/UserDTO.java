package com.example.livinglab.Dto;
import com.example.livinglab.Entity.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long user_num;  // user_num
    private String userid;     // id
    private String pw;
    private String phone;   // phone
    private String email;   // email
    private String address; // address
    private String user_name; // user_name

    // Role 필드가 필요한 경우, RoleDTO를 사용하거나 Role 정보를 문자열로 반환할 수 있음
    private String role;    // role 정보가 필요하다면

    // User 엔티티를 UserDTO로 변환하는 생성자 추가
    public UserDTO(User user) {
        this.user_num = user.getUser_num();
        this.userid = user.getUserid();
        this.pw = user.getPw();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.user_name = user.getUser_name();
        this.role = user.getRole() != null ? user.getRole().getRoleName() : null;  // roleName을 Role 클래스에서 가져올 수 있음
    }

    public UserDTO(Long userNum, String userName, String pw, Object o, @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.") String phone, @Email(message = "이메일 형식이 올바르지 않습니다.") String email, String address, String userid, String roleName) {
    }
}