package com.example.livinglab.Controller;

import com.example.livinglab.Dto.GoodsDTO;
import com.example.livinglab.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    // 상품 등록
    @PostMapping("/add")
    public ResponseEntity<GoodsDTO> addGoods(@RequestBody GoodsDTO goodsDTO) {
        GoodsDTO createdGoods = goodsService.addGoods(goodsDTO);
        return new ResponseEntity<>(createdGoods, HttpStatus.CREATED);
    }

    // 모든 상품 조회
    @GetMapping("/all")
    public ResponseEntity<List<GoodsDTO>> getAllGoods() {
        List<GoodsDTO> goodsList = goodsService.getAllGoods();
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    // 특정 상품 조회
    @GetMapping("/{goodsnum}")
    public ResponseEntity<GoodsDTO> getGoodsById(@PathVariable Long goodsnum) {
        Optional<GoodsDTO> goodsDTO = goodsService.getGoodsById(goodsnum);
        return goodsDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GoodsDTO>> getGoodsByTag(@RequestParam String tag) {
        List<GoodsDTO> goodsDTOList = goodsService.findGoodsByTag(tag);
        return ResponseEntity.ok(goodsDTOList);
    }
}