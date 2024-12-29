package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Order;
import com.example.livinglab.Dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_User_num(Long userId);
}