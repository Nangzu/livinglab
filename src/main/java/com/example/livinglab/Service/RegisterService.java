package com.example.livinglab.Service;

import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.Role;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.RoleRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserDTO createUser(UserDTO userDTO) {
        // Role 엔티티 조회
        Role role = roleRepository.findByRoleCode(userDTO.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + userDTO.getRole()));

        // User 엔티티 생성
        User user = new User();
        user.setUserid(userDTO.getUserid());
        user.setPw(userDTO.getPw()); // 비밀번호 추가
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setUser_name(userDTO.getUser_name());
        user.setRole(role); // Role 설정

        // 저장
        userRepository.save(user);

        // DTO 반환
        return new UserDTO(user);
    }
}