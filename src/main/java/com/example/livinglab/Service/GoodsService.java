package com.example.livinglab.Service;

import com.example.livinglab.Dto.GoodsDTO;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Repository.UserRepository;
import com.example.livinglab.Repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 상품 등록
    public GoodsDTO addGoods(GoodsDTO goodsDTO) {

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
        goods.setMain_image(goodsDTO.getMainImage());

        goods = goodsRepository.save(goods);
        return new GoodsDTO(
                goods.getGoodsnum(),
                goods.getUser().getUser_num(),
                goods.getMarket().getMarket_code(),
                goods.getGoods_name(),
                goods.getPrice(),
                goods.getTag(),
                goods.getDetails(),
                goods.getGoods_option(),
                goods.getMain_image()
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
                        goods.getGoods_option(),
                        goods.getMain_image()))
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
                g.getGoods_option(),
                g.getMain_image())
        );
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
                        goods.getGoods_option(),
                        goods.getMain_image()
                ))
                .collect(Collectors.toList());
    }
}