package com.example.livinglab.Repository;

import com.example.livinglab.Entity.AdminPage;
import com.example.livinglab.Entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPageRepository extends JpaRepository<AdminPage, String> {
}