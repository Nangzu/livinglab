package com.example.livinglab.Service;

import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CbSendRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CbSendService {

    @Autowired
    private CbSendRepository cbSendRepository;

    @Autowired
    private UserRepository userRepository;


    public CbSend uploadFile(Long usernum, MultipartFile file) {
        User user = userRepository.findById(usernum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == null || user.getRole().getRoleCode() != 3L) {
            throw new RuntimeException("Only students (roleCode 3) can upload files.");
        }

        try {
            CbSend cbSend = new CbSend();
            cbSend.setUser(user);
            cbSend.setFiles(file.getBytes());
            cbSend.setFileName(file.getOriginalFilename());

            return cbSendRepository.save(cbSend);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }


    public CbSend getFileById(Long id) {
        return cbSendRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}
