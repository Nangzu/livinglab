package com.example.livinglab.Controller;

import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Service.CbSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/cbsend")
public class CbSendController {

    @Autowired
    private CbSendService cbSendService;

    @PostMapping("/upload")
    public ResponseEntity<CbSend> uploadFile(
            @RequestParam("usernum") Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Received userId: " + userId);
            System.out.println("Received file: " + file.getOriginalFilename());
            CbSend cbSend = cbSendService.uploadFile(userId, file);
            return ResponseEntity.ok(cbSend);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        CbSend cbSend = cbSendService.getFileById(id);

        byte[] fileData = cbSend.getFiles();
        String fileName = cbSend.getFileName();

        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileData);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding file name: " + e.getMessage());
        }
    }
}
