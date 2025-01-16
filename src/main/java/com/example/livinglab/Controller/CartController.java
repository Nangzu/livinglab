package com.example.livinglab.Controller;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 카트에 상품 추가
    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {
        // 세션에서 사용자 정보를 확인
        if (session.getAttribute("user") == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 사용자 인증 실패
        }

        CartDTO addedCart = cartService.addToCart(cartDTO);
        return new ResponseEntity<>(addedCart, HttpStatus.CREATED); // 201 Created
    }
}