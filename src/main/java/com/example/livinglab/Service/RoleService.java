package com.example.livinglab.Service;

import com.example.livinglab.Entity.Role;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;

    private static final Long SELLER_ROLE_CODE = 2L;

    // Seller 역할을 Student 사용자에게 임의로 부여/제거
    public void toggleSellerRoleForStudent(Long sellerId, Long studentId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Seller 권한 확인
        if (seller.getRole() == null || !SELLER_ROLE_CODE.equals(seller.getRole().getRoleCode())) {
            throw new RuntimeException("User is not a seller");
        }

        // 임의로 역할 부여 또는 제거
        if (Math.random() < 0.5) {
            // 역할 부여
            if (student.getRole() == null || !SELLER_ROLE_CODE.equals(student.getRole().getRoleCode())) {
                Role sellerRole = new Role();
                sellerRole.setRoleCode(SELLER_ROLE_CODE); // Role 엔티티에서 필요한 정보를 채워야 함
                student.assignRole(sellerRole);
                System.out.println("Seller role assigned to student.");
            } else {
                System.out.println("Student already has seller role.");
            }
        } else {
            // 역할 제거
            if (student.getRole() != null && SELLER_ROLE_CODE.equals(student.getRole().getRoleCode())) {
                student.removeRole();
                System.out.println("Seller role removed from student.");
            } else {
                System.out.println("Student does not have seller role.");
            }
        }

        userRepository.save(student);
    }
}