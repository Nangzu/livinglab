package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Order;
import lombok.*;
import com.example.livinglab.Entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long order_num;  // 주문 번호
    private String py_method;  // 결제 방법
    private Long user_num;  // 사용자 정보
    private List<Long> cartnums;  // 장바구니 정보 리스트

    // Order 엔티티를 DTO로 변환하는 생성자
    public OrderDTO(Order order) {
        this.order_num = order.getOrder_num();
        this.py_method = order.getPy_method();
        this.user_num = order.getUser() != null ? order.getUser().getUser_num() : null;  // 사용자 정보
        this.cartnums = order.getCarts() != null ? order.getCarts().stream()
                .map(Cart::getCartnum)  // 각 Cart의 cartnum을 가져와서 리스트로 변환
                .collect(Collectors.toList()) : null;
    }
}