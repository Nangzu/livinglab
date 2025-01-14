package com.example.livinglab.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderNum;
    private UserDTO user;  // 사용자 정보
    private List<CartDTO> cartItems;  // 장바구니 아이템들
}