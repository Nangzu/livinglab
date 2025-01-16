package com.example.livinglab.Controller;

import com.example.livinglab.Entity.Collaboration;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Service.CollaborationService;
import com.example.livinglab.Dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collaboration")
public class CollaborationController {

    @Autowired
    private CollaborationService collaborationService;

    @PostMapping("/create")
    public ResponseEntity<Collaboration> createCollaboration(HttpSession session,
                                                             @RequestBody Long marketCode) {  // marketCode를 Long으로 받기

        // 세션에서 UserDTO 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에 사용자 정보가 없거나 권한이 없는 경우
        if (userDTO == null || userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 권한이 없으면 401 반환
        }

        // UserDTO를 User 엔티티로 변환
        User user = new User();
        user.setUser_num(userDTO.getUser_num());  // 필요한 필드들로 초기화
        user.setUser_name(userDTO.getUser_name());
        user.setEmail(userDTO.getEmail());
        // 필요한 경우 다른 필드들도 설정
        // 협업 생성
        Collaboration collaboration = collaborationService.createCollaboration(user, marketCode);

        return new ResponseEntity<>(collaboration, HttpStatus.CREATED);
    }
}