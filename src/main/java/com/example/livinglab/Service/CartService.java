package com.example.livinglab.Service;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CartRepository;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserRepository userRepository;

    public CartDTO addToCart(CartDTO cartDTO) {
        // 상품 조회
        Goods goods = goodsRepository.findById(cartDTO.getGoodsnum())
                .orElseThrow(() -> new IllegalArgumentException("Invalid goods ID"));

        // 사용자 조회
        User user = userRepository.findById(cartDTO.getUsernum())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 새로운 카트 생성
        Cart cart = new Cart();
        cart.setGoods(goods);
        cart.setUser(user);
        cart.setQuantity(cartDTO.getQuantity());

        // 카트 저장
        Cart savedCart = cartRepository.save(cart);

        // 저장된 카트를 DTO로 변환
        return new CartDTO(savedCart);
    }
    public List<CartDTO> getCartItems(Long usernum) {

        // 특정 사용자의 카트를 조회
        List<Cart> cartList = cartRepository.findCartsByUser_Usernum(usernum);

        // 조회된 카트를 DTO로 변환
        return cartList.stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());
    }

    public void removeFromCart(Long cartId) {
        // 카트 항목 조회
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        // 카트 항목 삭제
        cartRepository.delete(cart);
    }

    public CartDTO updateCart(Long cartId, CartDTO cartDTO) {
        // 카트 항목 조회
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        // 새로운 수량과 상품 적용
        Goods goods = goodsRepository.findById(cartDTO.getGoodsnum())
                .orElseThrow(() -> new IllegalArgumentException("Invalid goods ID"));

        cart.setGoods(goods);
        cart.setQuantity(cartDTO.getQuantity());

        // 업데이트된 카트 저장
        Cart updatedCart = cartRepository.save(cart);

        // 저장된 카트를 DTO로 변환
        return new CartDTO(updatedCart);
    }

}