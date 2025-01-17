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
    private Long ordernum;  // 주문 번호
    private String pymethod;  // 결제 방법
    private Long usernum;  // 사용자 정보
    private String state;  // 장바구니 정보 리스트

    // Order 엔티티를 DTO로 변환하는 생성자
    public OrderDTO(Order order) {
        this.ordernum = order.getOrdernum();
        this.pymethod = order.getPymethod();
        this.usernum = order.getUser() != null ? order.getUser().getUsernum() : null;  // 사용자 정보
        this.state = order.getState();
    }
}