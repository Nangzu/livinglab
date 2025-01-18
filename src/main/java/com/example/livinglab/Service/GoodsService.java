package com.example.livinglab.Service;

import com.example.livinglab.Dto.*;
import com.example.livinglab.Entity.Filestorage;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Repository.FilestorageRepository;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Repository.UserRepository;
import com.example.livinglab.Repository.MarketRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private GoodsdetailService goodsdetailService; // GoodsdetailService 추가



    // 상품 등록
    public GoodsDTO addGoods(GoodsDTO goodsDTO, MultipartFile file, GoodsdetailDTO goodsdetailDTO, HttpSession session, MultipartFile file1) throws IOException {


        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Long num = userDTO.getUsernum();
        Optional<User> userOpt = userRepository.findById(num);
        Optional<Market> marketOpt = marketRepository.findByUser_Usernum(num);


        // 유효성 검사: 사용자와 마켓이 존재하는지 확인
        if (userOpt.isEmpty() || marketOpt.isEmpty()) {
            throw new IllegalArgumentException("User or Market not found");
        }


        Goods goods = new Goods();
        goods.setGoodsnum(goodsDTO.getGoodsnum()); // 여기서 받는다
        goods.setUser(userOpt.get());  // 사용자 객체 설정
        goods.setMarket(marketOpt.get());
        goods.setGoodsname(goodsDTO.getGoodsname());
        goods.setPrice(goodsDTO.getPrice());
        goods.setTag(goodsDTO.getTag());
        goods.setDetails(goodsDTO.getDetails());
        goods.setGoodsoption(goodsDTO.getGoodsOption());

        goods = goodsRepository.save(goods);

        goodsdetailDTO.setGoodsnum(goods.getGoodsnum());
        goodsdetailService.addGoodsDetails(goodsdetailDTO, file1);

        Filestorage filestorage = new Filestorage();
        filestorage.setFiledata(file.getBytes());
        filestorage.setFilename(file.getOriginalFilename());  // 파일명 저장
        filestorage.setFiletype(file.getContentType());  // 파일타입 저장
        filestorage.setGoods(goods);

        filestorageRepository.save(filestorage);

        return new GoodsDTO(
                goods.getGoodsnum(),
                goods.getUser().getUsernum(),
                goods.getMarket().getMarketcode(),
                goods.getGoodsname(),
                goods.getPrice(),
                goods.getTag(),
                goods.getDetails(),
                goods.getGoodsoption()
        );
    }

    // 상품 조회
    public List<GoodsDTO> getAllGoods() {
        List<Goods> goodsList = goodsRepository.findAll();
        return goodsList.stream()
                .map(goods -> new GoodsDTO(
                        goods.getGoodsnum(),
                        goods.getUser().getUsernum(),
                        goods.getMarket().getMarketcode(),
                        goods.getGoodsname(),
                        goods.getPrice(),
                        goods.getTag(),
                        goods.getDetails(),
                        goods.getGoodsoption()
                ))
                .toList();
    }

    // 특정 상품 조회
    public Optional<GoodsDTO> getGoodsById(Long goodsnum) {
        Optional<Goods> goods = goodsRepository.findById(goodsnum);
        return goods.map(g -> new GoodsDTO(
                g.getGoodsnum(),
                g.getUser().getUsernum(),
                g.getMarket().getMarketcode(),
                g.getGoodsname(),
                g.getPrice(),
                g.getTag(),
                g.getDetails(),
                g.getGoodsoption()
        ));
    }

    public List<GoodsSubDTO> findGoodsByGoodsname(String goodsname) {
        List<Goods> goodsList = goodsRepository.findByGoodsname(goodsname);
        List<Filestorage> filestorageList = filestorageRepository.findByGoods_Goodsname(goodsname);

        Long goodsnum = goodsList.isEmpty() ? null : goodsList.get(0).getGoodsnum();
        int price = goodsList.isEmpty() ? null : goodsList.get(0).getPrice();
        String firstGoodsname = goodsList.isEmpty() ? null : goodsList.get(0).getGoodsname();
        byte[] firstFileData = filestorageList.isEmpty() ? null : filestorageList.get(0).getFiledata();
        String marketname = goodsList.isEmpty() ? null : goodsList.get(0).getMarket().getMarketname();

        GoodsSubDTO goodsSubDTO = new GoodsSubDTO(goodsnum, price, firstGoodsname, firstFileData, marketname);
        List<GoodsSubDTO> result = new ArrayList<>();
        result.add(goodsSubDTO);

        return result;
    }

    // 상품 수정
    public GoodsDTO updateGoods(Long goodsnum, GoodsDTO goodsDTO, MultipartFile file, GoodsdetailDTO goodsdetailDTO, MultipartFile file1) throws IOException {
        Optional<Goods> existingGoodsOpt = goodsRepository.findById(goodsnum);
        if (existingGoodsOpt.isEmpty()) {
            throw new IllegalArgumentException("Goods not found");
        }

        Goods existingGoods = existingGoodsOpt.get();

        // 기존 상품 정보 업데이트
        existingGoods.setGoodsname(goodsDTO.getGoodsname());
        existingGoods.setPrice(goodsDTO.getPrice());
        existingGoods.setTag(goodsDTO.getTag());
        existingGoods.setDetails(goodsDTO.getDetails());
        existingGoods.setGoodsoption(goodsDTO.getGoodsOption());


        goodsdetailService.updateGoodsDetails(goodsnum, goodsdetailDTO, file1);


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
                existingGoods.getUser().getUsernum(),
                existingGoods.getMarket().getMarketcode(),
                existingGoods.getGoodsname(),
                existingGoods.getPrice(),
                existingGoods.getTag(),
                existingGoods.getDetails(),
                existingGoods.getGoodsoption()
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
    public Optional<FileDTO> getFileByGoodsNum(Long goodsnum) {
        // 특정 상품에 연결된 첫 번째 파일 데이터 반환
        return filestorageRepository.findByGoods_Goodsnum(goodsnum)
                .stream()
                .findFirst()
                .map(file -> {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setFilename(file.getFilename());
                    fileDTO.setFiletype(file.getFiletype());
                    fileDTO.setFiledata(file.getFiledata());
                    return fileDTO;
                });
    }
}