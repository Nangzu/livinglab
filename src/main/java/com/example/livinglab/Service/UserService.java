package com.example.livinglab.Service;

import com.example.livinglab.Dto.LoginDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.Role;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.RoleRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder 주입


    public UserDTO createUser(UserDTO userDTO) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(userDTO.getPw());

        // Role 설정 (Role Code가 1인 'user' 역할 조회)
        Role defaultRole = roleRepository.findByRoleCode(4L)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        // UserDTO를 User 엔티티로 변환
        User user = modelMapper.map(userDTO, User.class);

        // 비밀번호 설정
        user.setPw(encodedPassword);

        // 역할 설정
        user.setRole(defaultRole);

        // User 저장
        User savedUser = userRepository.save(user);

        // 저장된 User 엔티티를 UserDTO로 변환하여 반환
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO createSellerUser(UserDTO userDTO) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(userDTO.getPw());

        // Role 설정 (Role Code가 1인 'user' 역할 조회)
        Role defaultRole = roleRepository.findByRoleCode(2L)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        // UserDTO를 User 엔티티로 변환
        User user = modelMapper.map(userDTO, User.class);

        // 비밀번호 설정
        user.setPw(encodedPassword);

        // 역할 설정
        user.setRole(defaultRole);

        // User 저장
        User savedUser = userRepository.save(user);

        // 저장된 User 엔티티를 UserDTO로 변환하여 반환
        return modelMapper.map(savedUser, UserDTO.class);
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    public List<Map<String, Object>> getAllUsersAsMap() {
        return userRepository.findAll().stream()
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("usernum", user.getUsernum());
                    map.put("userid", user.getUserid());
                    map.put("phone", user.getPhone());
                    map.put("email", user.getEmail());
                    map.put("username", user.getUsername());
                    return map;
                })
                .collect(Collectors.toList());
    }


    public Optional<UserDTO> getUserById(String userid) {
        return userRepository.findByUserid(userid)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }


    public UserDTO updateUser(String userid, UserDTO userDTO) {
        User user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(userDTO, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }


    public void deleteUser(String userid) {
        userRepository.deleteByUserid(userid);
    }

    public UserDTO loginUser(LoginDTO loginDTO) {
        // 사용자 아이디로 조회
        Optional<User> userOptional = userRepository.findByUserid(loginDTO.getUserid());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 입력된 비밀번호와 저장된 비밀번호 비교
            if (passwordEncoder.matches(loginDTO.getPw(), user.getPw())) {
                // 비밀번호가 맞으면 UserDTO 반환
                return new UserDTO(user);
            } else {
                throw new RuntimeException("Invalid credentials"); // 비밀번호 불일치
            }
        } else {
            throw new RuntimeException("User not found"); // 아이디를 찾을 수 없음
        }
    }
}
