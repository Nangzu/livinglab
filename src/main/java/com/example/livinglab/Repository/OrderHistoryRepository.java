package com.example.livinglab.Repository;
import com.example.livinglab.Entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {}