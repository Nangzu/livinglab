package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Order;
import lombok.*;
import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.Filestorage;
import java.util.List;
import java.util.stream.Collectors;
import com.example.livinglab.Repository.FilestorageRepository;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long ordernum;  // 주문 번호
    private String pymethod;  // 결제 방법
    private Long usernum;  // 사용자 정보
    private String state;  // 주문 상태
    private int totalprice;

    private List<Long> cartnums;  // 카트 번호들
    private List<String> goodsnames;  // 상품명들
    private List<byte[]> filedatas;  // 파일 데이터들

    // Order 엔티티를 DTO로 변환하는 생성자
    public OrderDTO(Order order, List<Long> cartnums, List<String> goodsnames, List<byte[]> filedatas) {
        this.ordernum = order.getOrdernum();
        this.pymethod = order.getPymethod();
        this.usernum = order.getUser() != null ? order.getUser().getUsernum() : null;  // 사용자 정보
        this.state = order.getState();
        this.totalprice = order.getTotalprice();
        this.cartnums = cartnums;
        this.goodsnames = goodsnames;
        this.filedatas = filedatas;
    }
}