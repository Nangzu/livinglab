package com.example.livinglab.Controller;

import com.example.livinglab.Dto.MarketDTO;
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
    public UserDTO registerSellerUser(@RequestBody UserDTO userDTO, @RequestParam String marketname) {
        UserDTO userdto = userService.createSellerUser(userDTO);
        marketService.createmarket(marketname, userdto);
        return userdto;
    }
}