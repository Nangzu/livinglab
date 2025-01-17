package com.example.livinglab.Controller;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> getCart(HttpSession session) {
        // 세션에서 사용자 정보를 확인
        if (session.getAttribute("user") == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 사용자 인증 실패
        }

        // 세션에서 사용자 번호 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        Long usernum = userDTO.getUsernum();

        // 사용자의 카트 조회
        try {
            List<CartDTO> cartItems = cartService.getCartItems(usernum);
            return new ResponseEntity<>(cartItems, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 서버 에러
        }
    }

    // 카트 삭제
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartId, HttpSession session) {
        // 세션에서 사용자 정보를 확인
        if (session.getAttribute("user") == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 사용자 인증 실패
        }

        try {
            cartService.removeFromCart(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    // 카트 업데이트
    @PutMapping("/update/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable Long cartId, @RequestBody CartDTO cartDTO, HttpSession session) {
        // 세션에서 사용자 정보를 확인
        if (session.getAttribute("user") == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 사용자 인증 실패
        }

        try {
            CartDTO updatedCart = cartService.updateCart(cartId, cartDTO);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK); // 200 OK
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}