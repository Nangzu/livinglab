package com.example.livinglab.Controller;

import com.example.livinglab.Entity.Order;
import com.example.livinglab.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 주문 생성
    @PostMapping("/create")
    public Order createOrder(@RequestParam Long userId,
                             @RequestParam List<Long> cartIds,
                             @RequestParam String paymentMethod) {
        return orderService.createOrder(userId, cartIds, paymentMethod);
    }

    // 사용자별 주문 목록 조회
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public Order getOrderDetails(@PathVariable Long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/cart/{userId}/{goodsId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long goodsId) {
        orderService.removeFromCart(userId, goodsId);
    }
}