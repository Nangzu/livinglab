package com.example.livinglab.Repository;

import com.example.livinglab.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    void deleteByUserid(String userid);
}