package com.example.livinglab.Service;

import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CbSendRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CbSendService {

    @Autowired
    private CbSendRepository cbSendRepository;

    @Autowired
    private UserRepository userRepository;

    public CbSend uploadFile(Long userId, String filePath) {
        // 학생 여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == null || user.getRole().getRoleCode() != 3L) {
            throw new RuntimeException("Only students (roleCode 3) can upload files.");
        }

        // CbSend 엔티티 생성 및 저장
        CbSend cbSend = new CbSend();
        cbSend.setUser(user);
        cbSend.setFiles(filePath);

        return cbSendRepository.save(cbSend);
    }
}
