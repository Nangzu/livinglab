package com.example.livinglab.Controller;

import com.example.livinglab.Dto.*;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Service.GoodsService;
import com.example.livinglab.Service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @PostMapping("/register")
    public ResponseEntity<MarketDTO> registerMarket(@RequestBody MarketDTO marketdto, HttpSession session) {
        // 세션에서 사용자 정보 확인
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null || !userDTO.getRole().equals(2L)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Market 생성
        MarketDTO createdMarket = marketService.createmarket(marketdto, session);
        return new ResponseEntity<>(createdMarket, HttpStatus.CREATED);
    }
}