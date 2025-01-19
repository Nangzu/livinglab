package com.example.livinglab.Controller;

import com.example.livinglab.Service.FilestorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FilestorageController {

    @Autowired
    private FilestorageService filestorageService;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("goodsnum") Long goodsnum) throws IOException {
        filestorageService.uploadFile(file, goodsnum);  // 서비스에서 파일 업로드 처리
        return ResponseEntity.ok("File uploaded successfully");
    }

    // 파일 다운로드
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        byte[] fileData = filestorageService.downloadFile(id);  // 서비스에서 파일 다운로드 처리
        return ResponseEntity.ok(fileData);
    }
}