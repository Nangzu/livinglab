package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Order;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long order_num;  // 주문 번호
    private String py_method;  // 결제 방법
    private UserDTO user;  // 사용자 정보
    private CartDTO cart;  // 장바구니 정보

    public OrderDTO(Order order) {
        this.order_num = order.getOrder_num();
        this.py_method = order.getPy_method();
        this.user = order.getUser() != null ? new UserDTO(order.getUser()) : null;  // UserDTO로 변환
        this.cart = order.getCart() != null ? new CartDTO(order.getCart()) : null;  // CartDTO로 변환
    }

}