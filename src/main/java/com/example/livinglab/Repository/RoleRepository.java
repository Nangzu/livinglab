package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // USERS, SELLER, STUDENT, ADMINI 중 하나를 찾음
    @Query("SELECT r FROM Role r WHERE " +
            "r.users = :role OR r.seller = :role OR r.student = :role OR r.admini = :role")
    Role findByRoleName(@Param("role") String role);
}
