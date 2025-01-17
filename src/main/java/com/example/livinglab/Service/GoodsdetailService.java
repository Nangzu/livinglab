package com.example.livinglab.Service;

import com.example.livinglab.Dto.GoodsdetailDTO;
import com.example.livinglab.Entity.Filestorage;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.Goodsdetail;
import com.example.livinglab.Repository.FilestorageRepository;
import com.example.livinglab.Repository.GoodsdetailRepository;
import com.example.livinglab.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsdetailService {

    @Autowired
    private GoodsdetailRepository goodsdetailRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private FilestorageService filestorageService;  // File storage service

    @Autowired
    private FilestorageRepository filestorageRepository;

    // 상품 세부 정보 등록
    public GoodsdetailDTO addGoodsDetails(GoodsdetailDTO goodsdetailDTO, MultipartFile file) throws IOException {
        Goods goods = goodsRepository.findById(goodsdetailDTO.getGoodsnum())
                .orElseThrow(() -> new IllegalArgumentException("Goods not found"));

        Goodsdetail goodsdetail = new Goodsdetail();
        goodsdetail.setGoods(goods);
        goodsdetail.setPackagingtype(goodsdetailDTO.getPackagingtype());
        goodsdetail.setSalesunit(goodsdetailDTO.getSalesunit());
        goodsdetail.setWeightcapacity(goodsdetailDTO.getWeightcapacity());
        goodsdetail.setExpirydate(goodsdetailDTO.getExpirydate());
        goodsdetail.setNotes(goodsdetailDTO.getNotes());

        goodsdetail = goodsdetailRepository.save(goodsdetail);

        Filestorage filestorage = new Filestorage();
        filestorage.setFiledata(file.getBytes());
        filestorage.setFilename(file.getOriginalFilename());  // 파일명 저장
        filestorage.setFiletype(file.getContentType());  // 파일타입 저장
        filestorage.setGoods(goods);

        filestorageRepository.save(filestorage);

        return new GoodsdetailDTO(goodsdetail);
    }

    // 상품 세부 정보 조회
    public GoodsdetailDTO getGoodsdetailByGoodsnum(Long goodsnum) {
        Goodsdetail goodsDetails = goodsdetailRepository.findByGoods_Goodsnum(goodsnum)
                .orElseThrow(() -> new IllegalArgumentException("Goods details not found"));

        return new GoodsdetailDTO(goodsDetails);
    }

    public GoodsdetailDTO updateGoodsDetails(Long goodsnum, GoodsdetailDTO goodsdetailDTO, MultipartFile file) throws IOException {
        Goodsdetail existingGoodsDetail = goodsdetailRepository.findByGoods_Goodsnum(goodsnum)
                .orElseThrow(() -> new IllegalArgumentException("Goods details not found"));

        Optional<Goods> existingGoodsOpt = goodsRepository.findById(goodsnum);

        existingGoodsDetail.setPackagingtype(goodsdetailDTO.getPackagingtype());
        existingGoodsDetail.setSalesunit(goodsdetailDTO.getSalesunit());
        existingGoodsDetail.setWeightcapacity(goodsdetailDTO.getWeightcapacity());
        existingGoodsDetail.setExpirydate(goodsdetailDTO.getExpirydate());
        existingGoodsDetail.setNotes(goodsdetailDTO.getNotes());

        existingGoodsDetail = goodsdetailRepository.save(existingGoodsDetail);

        if (file != null && !file.isEmpty()) {
            // goodsnum으로 기존 파일 찾기
            List<Filestorage> filestorageList = filestorageRepository.findByGoods_Goodsnum(existingGoodsDetail.getGoods().getGoodsnum());

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
                filestorage.setGoods(existingGoodsOpt.get());
                filestorageRepository.save(filestorage);  // 새 파일 저장
            }
        }

        return new GoodsdetailDTO(existingGoodsDetail);
    }

    public boolean deleteGoodsDetails(Long goodsnum) {
        Goodsdetail existingGoodsDetail = goodsdetailRepository.findByGoods_Goodsnum(goodsnum)
                .orElseThrow(() -> new IllegalArgumentException("Goods details not found"));

        goodsdetailRepository.delete(existingGoodsDetail);
        return true;
    }
}