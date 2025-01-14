package com.example.livinglab.Service;

import com.example.livinglab.Dto.LoginDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.User;
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
    private PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder 주입


    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
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
                    map.put("user_num", user.getUser_num());
                    map.put("userid", user.getUserid());
                    map.put("phone", user.getPhone());
                    map.put("email", user.getEmail());
                    map.put("user_name", user.getUser_name());
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
        // 사용자 id로 조회
        Optional<User> userOptional = userRepository.findByUserid(loginDTO.getUserid());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 비밀번호 비교
            if (passwordEncoder.matches(loginDTO.getPw(), user.getPw())) {
                // 비밀번호가 맞으면 UserDTO 반환
                return new UserDTO(user);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // 비밀번호 암호화
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
