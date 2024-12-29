package com.example.livinglab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.example.livinglab.Repository.*;
import com.example.livinglab.Entity.*;
import lombok.*;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    // 주문 생성
    @Transactional
    public Order createOrder(Long userId, List<Long> cartIds, String paymentMethod) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 카트에서 상품 정보 가져오기
        List<Cart> carts = cartRepository.findAllById(cartIds);

        // 주문 생성
        Order order = new Order();
        order.setUser(user);  // 주문한 사용자
        order.setPy_method(paymentMethod);  // 결제 방법
        order.setCart((Cart) carts);  // 주문한 카트(상품)

        // 주문 저장
        return orderRepository.save(order);
    }

    // 사용자의 모든 주문 가져오기
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // 특정 주문의 상세 정보 가져오기
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // 장바구니에서 특정 상품 제거
    public void removeFromCart(Long userId, Long goodsId) {
        Cart cart = cartRepository.findByUserAndGoods(userId, goodsId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cartRepository.delete(cart);
    }
}