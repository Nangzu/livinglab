package com.example.livinglab.Controller;

import com.example.livinglab.Dto.MarketDTO;
import com.example.livinglab.Dto.RegisterDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Service.MarketService;
import com.example.livinglab.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private MarketService marketService;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping("/sellerregister")
    public UserDTO registerSellerUser(@RequestBody RegisterDTO registerDTO) {
        UserDTO userdto = userService.createSellerUser(registerDTO);
        marketService.createmarket(registerDTO);
        return userdto;
    }
}