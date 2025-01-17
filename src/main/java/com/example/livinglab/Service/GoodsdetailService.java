package com.example.livinglab.Service;

import com.example.livinglab.Dto.GoodsdetailDTO;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.Goodsdetail;
import com.example.livinglab.Repository.GoodsdetailRepository;
import com.example.livinglab.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsdetailService {

    @Autowired
    private GoodsdetailRepository goodsdetailRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    // 상품 세부 정보 등록
    public GoodsdetailDTO addGoodsDetails(GoodsdetailDTO goodsdetailDTO) {
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
        return new GoodsdetailDTO(goodsdetail);
    }

    // 상품 세부 정보 조회
    public GoodsdetailDTO getGoodsdetailByGoodsnum(Long goodsnum) {
        Goodsdetail goodsDetails = goodsdetailRepository.findByGoods_Goodsnum(goodsnum)
                .orElseThrow(() -> new IllegalArgumentException("Goods details not found"));

        return new GoodsdetailDTO(goodsDetails);
    }
}