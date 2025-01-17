package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 여러 개의 카트 아이템을 한 번에 조회
    public List<Cart> findAllByCartnumIn(List<Long> cartnums);

    // 사용자별 카트 아이템 조회
    List<Cart> findByUser(User user);

    // 사용자와 상품에 해당하는 장바구니 아이템 조회
    Optional<Cart> findByCartnumAndUser_Userid(Long cartnum, String userid);

    List<Cart> findCartsByUser_Usernum(Long usernum);
}