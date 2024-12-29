package com.example.livinglab.Service;

import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


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
                    map.put("id", user.getId());
                    map.put("phone", user.getPhone());
                    map.put("email", user.getEmail());
                    map.put("user_name", user.getUser_name());
                    return map;
                })
                .collect(Collectors.toList());
    }


    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(userDTO, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
