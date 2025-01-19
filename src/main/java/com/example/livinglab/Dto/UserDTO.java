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
    private Long usernum;  // user_num
    private String userid;     // id
    private String phone;   // phone
    private String email;   // email
    private String address; // address
    private String username; // user_name
    private String pw;

    // Role 필드가 필요한 경우, RoleDTO를 사용하거나 Role 정보를 문자열로 반환할 수 있음
    private Long role;    // role 정보가 필요하다면

    // User 엔티티를 UserDTO로 변환하는 생성자 추가
    public UserDTO(User user) {
        this.usernum = user.getUsernum();
        this.userid = user.getUserid();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.username = user.getUsername();
        this.role = (long) Math.toIntExact(user.getRole() != null ? user.getRole().getRoleCode() : null);  // roleName을 Role 클래스에서 가져올 수 있음
    }


    public UserDTO(Long usernum,
                   String username,
                   String pw,
                   @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.") String phone,
                   @Email(message = "이메일 형식이 올바르지 않습니다.") String email,
                   String address, String userid, Object o, Long roleCode) {
    }

    public String getPw() {
        return pw;
    }

    public Long getUsernum() {
        return usernum;
    }

    public void setUsernum(Long usernum) {
        this.usernum = usernum;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
        // UserDTO -> User 변환
        public User toUser() {
            User user = new User();
            user.setUsernum(this.usernum);
            user.setUsername(this.username);
            return user;
        }
}