package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findByTag(String tag);
    Optional<Goods> findByGoodsnum(Long goodsnum);
}