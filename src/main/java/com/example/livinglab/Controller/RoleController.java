package com.example.livinglab.Controller;

import com.example.livinglab.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Seller 역할을 Student 사용자에게 부여/제거 요청 처리
    @PostMapping("/toggle-seller-role")
    public ResponseEntity<String> toggleSellerRole(@RequestParam Long sellerId, @RequestParam Long studentId) {
        try {
            roleService.toggleSellerRoleForStudent(sellerId, studentId);
            return ResponseEntity.ok("Role toggle operation completed.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}