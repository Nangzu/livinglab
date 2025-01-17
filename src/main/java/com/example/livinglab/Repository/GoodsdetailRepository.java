package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Goodsdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsdetailRepository extends JpaRepository<Goodsdetail, Long> {
    Optional<Goodsdetail> findByGoods_Goodsnum(Long goodsnum);
}