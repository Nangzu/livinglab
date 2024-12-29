package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findByTag(String tag);
}