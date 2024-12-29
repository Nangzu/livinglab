package com.example.livinglab.Service;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CartRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    // 사용자별 장바구니 아이템 조회 (DTO로 반환)
    public List<CartDTO> getCartItems(Long userId) {
        // userId로 User 객체를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 조회한 User 객체를 사용하여 장바구니 아이템 조회
        List<Cart> carts = cartRepository.findByUser(user);

        return carts.stream().map(cart -> {
            return new CartDTO(
                    cart.getCart_num(),
                    cart.getGoods().getGoods_num(),
                    cart.getGoods().getGoods_name(),
                    cart.getNum()
            );
        }).collect(Collectors.toList());
    }
}