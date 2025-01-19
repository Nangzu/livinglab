package com.example.livinglab.Controller;

import com.example.livinglab.Dto.LoginDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public UserDTO createUser(@RequestBody UserDTO userDTO) {
//        return userService.createUser(userDTO);
//    }


    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/map")
    public List<Map<String, Object>> getAllUsersAsMap() {
        return userService.getAllUsersAsMap();
    }


    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }


    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "User successfully deleted.";
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        UserDTO userDTO = userService.loginUser(loginDTO);
        // 세션에 사용자 정보 저장
        session.setAttribute("user", userDTO);
        return userDTO;
    }
}
