package com.example.livinglab.Repository;
import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findByUser_Usernum(Long usernum);
    Optional<Market> findByMarketname(String marketname);
}
