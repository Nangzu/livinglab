package com.example.livinglab.Repository;

import com.example.livinglab.Entity.CbSend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CbSendRepository extends JpaRepository<CbSend, Long> {
    List<CbSend> findByMarket_Marketcode(Long marketcode);
}