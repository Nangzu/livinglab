package com.example.livinglab.Service;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Repository.GoodsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    private final GoodsRepository goodsRepository;
    public Goods createGoods(Goods goods) {
        try {
            // 1. 제품 저장
            Goods savedGoods = goodsRepository.save(goods);
            log.info("제품 정보 저장 완료 - ID : {}", savedGoods.getGoods_num());

            return goodsRepository.save(savedGoods); // 변경된 레시피 다시 저장
        } catch (Exception e) {
            log.error("제품 저장 중 오류 발생", e);
            e.printStackTrace(); // 상세 에러 확인을 위해 추가
            throw new RuntimeException("제품 저장 실패: " + e.getMessage(), e);
        }
    }
}