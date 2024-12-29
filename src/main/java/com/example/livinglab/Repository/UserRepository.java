package com.example.livinglab.Repository;

import com.example.livinglab.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}