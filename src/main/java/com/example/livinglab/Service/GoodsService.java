package com.example.livinglab.Service;

import com.example.livinglab.Dto.GoodsDTO;
import com.example.livinglab.Entity.Filestorage;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Repository.FilestorageRepository;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Repository.UserRepository;
import com.example.livinglab.Repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private FilestorageService filestorageService;  // File storage service

    @Autowired
    private FilestorageRepository filestorageRepository;


    // 상품 등록
    public GoodsDTO addGoods(GoodsDTO goodsDTO, MultipartFile file) throws IOException {

        Optional<User> userOpt = userRepository.findById(goodsDTO.getUserNum());
        Optional<Market> marketOpt = marketRepository.findById(goodsDTO.getMarketCode());

        // 유효성 검사: 사용자와 마켓이 존재하는지 확인
        if (userOpt.isEmpty() || marketOpt.isEmpty()) {
            throw new IllegalArgumentException("User or Market not found");
        }

        Goods goods = new Goods();
        goods.setGoodsnum(goodsDTO.getGoodsnum());
        goods.setUser(userOpt.get());  // 사용자 객체 설정
        goods.setMarket(marketOpt.get());
        goods.setGoods_name(goodsDTO.getGoodsName());
        goods.setPrice(goodsDTO.getPrice());
        goods.setTag(goodsDTO.getTag());
        goods.setDetails(goodsDTO.getDetails());
        goods.setGoods_option(goodsDTO.getGoodsOption());

        goods = goodsRepository.save(goods);

        Filestorage filestorage = new Filestorage();
        filestorage.setFiledata(file.getBytes());
        filestorage.setFilename(file.getOriginalFilename());  // 파일명 저장
        filestorage.setFiletype(file.getContentType());  // 파일타입 저장
        filestorage.setGoods(goods);

        filestorageRepository.save(filestorage);

        return new GoodsDTO(
                goods.getGoodsnum(),
                goods.getUser().getUser_num(),
                goods.getMarket().getMarket_code(),
                goods.getGoods_name(),
                goods.getPrice(),
                goods.getTag(),
                goods.getDetails(),
                goods.getGoods_option()
        );
    }

    // 상품 조회
    public List<GoodsDTO> getAllGoods() {
        List<Goods> goodsList = goodsRepository.findAll();
        return goodsList.stream()
                .map(goods -> new GoodsDTO(
                        goods.getGoodsnum(),
                        goods.getUser().getUser_num(),
                        goods.getMarket().getMarket_code(),
                        goods.getGoods_name(),
                        goods.getPrice(),
                        goods.getTag(),
                        goods.getDetails(),
                        goods.getGoods_option()
                ))
                .toList();
    }

    // 특정 상품 조회
    public Optional<GoodsDTO> getGoodsById(Long goodsnum) {
        Optional<Goods> goods = goodsRepository.findById(goodsnum);
        return goods.map(g -> new GoodsDTO(
                g.getGoodsnum(),
                g.getUser().getUser_num(),
                g.getMarket().getMarket_code(),
                g.getGoods_name(),
                g.getPrice(),
                g.getTag(),
                g.getDetails(),
                g.getGoods_option()
        ));
    }

    public List<GoodsDTO> findGoodsByTag(String tag) {
        List<Goods> goodsList = goodsRepository.findByTag(tag);
        return goodsList.stream()
                .map(goods -> new GoodsDTO(
                        goods.getGoodsnum(),
                        goods.getUser().getUser_num(),
                        goods.getMarket().getMarket_code(),
                        goods.getGoods_name(),
                        goods.getPrice(),
                        goods.getTag(),
                        goods.getDetails(),
                        goods.getGoods_option()
                ))
                .collect(Collectors.toList());
    }

    // 상품 수정
    public GoodsDTO updateGoods(Long goodsnum, GoodsDTO goodsDTO, MultipartFile file) throws IOException {
        Optional<Goods> existingGoodsOpt = goodsRepository.findById(goodsnum);
        if (existingGoodsOpt.isEmpty()) {
            throw new IllegalArgumentException("Goods not found");
        }

        Goods existingGoods = existingGoodsOpt.get();

        // 기존 상품 정보 업데이트
        existingGoods.setGoods_name(goodsDTO.getGoodsName());
        existingGoods.setPrice(goodsDTO.getPrice());
        existingGoods.setTag(goodsDTO.getTag());
        existingGoods.setDetails(goodsDTO.getDetails());
        existingGoods.setGoods_option(goodsDTO.getGoodsOption());

        // 파일이 존재하면 새로 저장
        if (file != null && !file.isEmpty()) {
            // goodsnum으로 기존 파일 찾기
            List<Filestorage> filestorageList = filestorageRepository.findByGoods_Goodsnum(existingGoods.getGoodsnum());

            // 기존 파일이 있으면, 모든 파일을 업데이트
            if (!filestorageList.isEmpty()) {
                // 모든 기존 파일 업데이트
                for (Filestorage filestorage : filestorageList) {
                    filestorage.setFiledata(file.getBytes());
                    filestorage.setFilename(file.getOriginalFilename());  // 파일명 업데이트
                    filestorage.setFiletype(file.getContentType());  // 파일타입 업데이트
                    filestorageRepository.save(filestorage);  // 파일 저장
                }
            } else {
                // 기존 파일이 없다면 새로 추가
                Filestorage filestorage = new Filestorage();
                filestorage.setFiledata(file.getBytes());
                filestorage.setFilename(file.getOriginalFilename());
                filestorage.setFiletype(file.getContentType());
                filestorage.setGoods(existingGoods);
                filestorageRepository.save(filestorage);  // 새 파일 저장
            }
        }

        // 업데이트된 상품 저장
        existingGoods = goodsRepository.save(existingGoods);

        return new GoodsDTO(
                existingGoods.getGoodsnum(),
                existingGoods.getUser().getUser_num(),
                existingGoods.getMarket().getMarket_code(),
                existingGoods.getGoods_name(),
                existingGoods.getPrice(),
                existingGoods.getTag(),
                existingGoods.getDetails(),
                existingGoods.getGoods_option()
        );
    }

    // 상품 삭제
    public boolean deleteGoods(Long goodsnum) {
        Optional<Goods> goodsOpt = goodsRepository.findById(goodsnum);
        if (goodsOpt.isEmpty()) {
            return false;  // 상품이 없으면 삭제할 수 없음
        }

        Goods goods = goodsOpt.get();

        // 관련된 파일도 삭제
        List<Filestorage> filestorageList = filestorageRepository.findByGoods_Goodsnum(goods.getGoodsnum());
        if (filestorageList != null && !filestorageList.isEmpty()) {
            // 기존 파일들이 있을 경우, 모두 삭제
            for (Filestorage filestorage : filestorageList) {
                filestorageRepository.delete(filestorage);
            }
        }

        // 상품 삭제
        goodsRepository.delete(goods);
        return true;  // 삭제 성공
    }
}