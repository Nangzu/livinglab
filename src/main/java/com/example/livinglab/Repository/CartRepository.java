package com.example.livinglab.Repository;
import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Entity.Goods;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);
    // 사용자의 장바구니에서 특정 상품을 찾는 메소드
    Optional<Cart> findByUserAndGoods(Long userId, Long goodsId);
}

