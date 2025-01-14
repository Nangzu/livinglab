package com.example.livinglab.Controller;

import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class RegistController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
}
