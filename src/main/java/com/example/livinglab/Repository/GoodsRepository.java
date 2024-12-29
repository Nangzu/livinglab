package com.example.livinglab.Repository;
import com.example.livinglab.Entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GoodsRepository extends JpaRepository<Goods, Long> {}