package com.example.livinglab.Controller;

import com.example.livinglab.Dto.*;
import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Service.CbSendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/cbsend")
public class CbSendController {

    @Autowired
    private CbSendService cbSendService;

    @PostMapping("/upload")
    public ResponseEntity<CbSend> uploadFile(
            @RequestPart("cbsend") String cbsendDTOJSON,
            @RequestPart("file") MultipartFile file) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            CbsendDTO cbsendDTO = objectMapper.readValue(cbsendDTOJSON, CbsendDTO.class);

            CbSend cbSend = cbSendService.uploadFile(cbsendDTO, file);
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

    @GetMapping
    public List<String> getAllMarketNames() {
        return cbSendService.getAllMarketNames();
    }

    @GetMapping("/owner")
    public ResponseEntity<List<CbSend>> getCbSendsByUser(@RequestParam String userid) {
        try {
            // 특정 유저의 마켓과 연관된 CbSend 조회
            List<CbSend> cbSends = cbSendService.getCbSendsByUserAndMarket(userid);
            return ResponseEntity.ok(cbSends);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
