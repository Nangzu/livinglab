package com.example.livinglab.Controller;

import com.example.livinglab.Dto.*;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Repository.GoodsdetailRepository;
import com.example.livinglab.Service.FilestorageService;
import com.example.livinglab.Service.GoodsService;
import com.example.livinglab.Service.GoodsdetailService;
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
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FilestorageService filestorageService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsdetailService goodsdetailService;

    @Autowired
    private GoodsdetailRepository goodsdetailRepository;


    // 상품 등록
    @PostMapping("/add")
    public ResponseEntity<GoodsDTO> addGoods(
            @RequestPart("goodsDTO") String goodsDTOJson, // JSON 문자열로 받음
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("goodsdetailDTO") String goodsdetailDTOJSON,
            @RequestPart(value = "file", required = false) MultipartFile file1,
            HttpSession session) throws IOException {

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null && userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        System.out.println("Received goodsDTO: " + goodsDTOJson);
        // JSON 문자열을 GoodsDTO 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        GoodsDTO goodsDTO = objectMapper.readValue(goodsDTOJson, GoodsDTO.class);



        GoodsdetailDTO goodsdetailDTO = objectMapper.readValue(goodsdetailDTOJSON, GoodsdetailDTO.class);

        GoodsDTO createdGoods = goodsService.addGoods(goodsDTO, file, goodsdetailDTO, session, file1);
        return new ResponseEntity<>(createdGoods, HttpStatus.CREATED);
    }

    @PutMapping("/update/{goodsnum}")
    public ResponseEntity<GoodsDTO> updateGoods(
            @PathVariable Long goodsnum,
            @RequestPart("goodsDTO") String goodsDTOJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("goodsdetailDTO") String goodsdetailDTOJSON,
            @RequestPart(value = "file", required = false) MultipartFile file1,
            HttpSession session) throws IOException {

        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null || userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 권한이 없으면 401
        }

        // 상품 조회
        Optional<Goods> existingGoodsOpt = goodsRepository.findById(goodsnum);
        if (existingGoodsOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 상품을 찾을 수 없으면 404
        }

        Goods existingGoods = existingGoodsOpt.get();

        // 요청한 상품이 본인의 상품이 아닌 경우
        if (!existingGoods.getUser().getUsernum().equals(userDTO.getUsernum())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 상태 코드 반환
        }

        // JSON 문자열을 GoodsDTO 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        GoodsDTO goodsDTO = objectMapper.readValue(goodsDTOJson, GoodsDTO.class);
        GoodsdetailDTO goodsdetailDTO = objectMapper.readValue(goodsdetailDTOJSON, GoodsdetailDTO.class);


        // 상품 수정
        GoodsDTO updatedGoods = goodsService.updateGoods(goodsnum, goodsDTO, file, goodsdetailDTO, file1);
        return new ResponseEntity<>(updatedGoods, HttpStatus.OK);
    }

    // 상품 삭제
    @DeleteMapping("/delete/{goodsnum}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long goodsnum, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null || userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 권한이 없으면 401
        }

        // 상품 조회
        Optional<Goods> existingGoodsOpt = goodsRepository.findById(goodsnum);
        if (existingGoodsOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 상품을 찾을 수 없으면 404
        }

        Goods existingGoods = existingGoodsOpt.get();

        // 요청한 상품이 본인의 상품이 아닌 경우
        if (!existingGoods.getUser().getUsernum().equals(userDTO.getUsernum())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 상태 코드 반환
        }

        // 상품 삭제
        boolean deleted1 = goodsdetailService.deleteGoodsDetails(goodsnum);
        boolean deleted = goodsService.deleteGoods(goodsnum);
        if (deleted || deleted1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 상태 코드 반환
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 상태 코드 반환
        }
    }

    // 모든 상품 조회
    @GetMapping("/all")
    public ResponseEntity<List<GoodsSubDTO>> getAllGoods() {
        List<GoodsSubDTO> goodsList = goodsService.getAllGoods();
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    // 특정 상품 조회
    @GetMapping("/{goodsnum}")
    public ResponseEntity<GoodsallDTO> getGoodsById(@PathVariable Long goodsnum) {
        Optional<GoodsallDTO> goodsDTO = goodsService.getGoodsById(goodsnum);
        return goodsDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{goodsnum}/file")
    public ResponseEntity<FileDTO> getFileByGoodsNum(@PathVariable Long goodsnum) {
        Optional<FileDTO> fileDTO = goodsService.getFileByGoodsNum(goodsnum);
        return fileDTO.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GoodsSubDTO>> getGoodsByname(@RequestParam String goodsname) {
        List<GoodsSubDTO> goodsDTOList = goodsService.findGoodsByGoodsname(goodsname);
        return ResponseEntity.ok(goodsDTOList); // 결과를 반환
    }
}