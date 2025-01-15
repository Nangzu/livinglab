package com.example.livinglab.Controller;

import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Service.CbSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/cbsend")
public class CbSendController {

    @Autowired
    private CbSendService cbSendService;

    private final String UPLOAD_DIR = "/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<CbSend> uploadFile(
            @RequestParam Long userId,
            @RequestParam MultipartFile file) {
        try {
            // 파일 저장
            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // CbSend 엔티티 저장
            CbSend cbSend = cbSendService.uploadFile(userId, filePath.toString());
            return ResponseEntity.ok(cbSend);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
