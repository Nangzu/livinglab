package com.example.livinglab.Repository;
import com.example.livinglab.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUsersIsNotNull();  // 'users' 역할이 설정된 Role을 찾는 메서드
    Optional<Role> findBySellerIsNotNull(); // 'seller' 역할이 설정된 Role을 찾는 메서드
    Optional<Role> findByStudentIsNotNull(); // 'student' 역할이 설정된 Role을 찾는 메서드
    Optional<Role> findByAdminiIsNotNull(); // 'admini' 역할이 설정된 Role을 찾는 메서드
}