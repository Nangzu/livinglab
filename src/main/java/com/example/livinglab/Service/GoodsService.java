package com.example.livinglab.Service;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Dto.GoodsDTO;
import com.example.livinglab.Repository.GoodsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
            log.info("제품 정보 저장 완료 - ID : {}", savedGoods.getGoodsnum());

            return goodsRepository.save(savedGoods); // 변경된 레시피 다시 저장
        } catch (Exception e) {
            log.error("제품 저장 중 오류 발생", e);
            e.printStackTrace(); // 상세 에러 확인을 위해 추가
            throw new RuntimeException("제품 저장 실패: " + e.getMessage(), e);
        }
    }

    public GoodsDTO getGoodsDetails(Long goodsNum) {
        try {
            // 1. 상품 번호로 제품 찾기
            Goods goods = goodsRepository.findById(goodsNum)
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. ID: " + goodsNum));

            // 2. Goods 엔티티를 GoodsDTO로 변환
            GoodsDTO goodsDTO = new GoodsDTO(
                    goods.getGoodsnum(),
                    goods.getGoods_name(),
                    goods.getPrice(),
                    goods.getTag(),
                    goods.getDetails(),
                    goods.getGoods_option(),
                    goods.getMain_image()
            );

            log.info("상품 정보 조회 완료 - ID: {}", goodsDTO.getGoodsNum());
            return goodsDTO;
        } catch (EntityNotFoundException e) {
            log.error("상품 조회 실패 - ID: {}", goodsNum, e);
            throw e;  // 다시 던져서 호출한 곳에서 처리하도록 할 수 있음
        } catch (Exception e) {
            log.error("상품 조회 중 오류 발생", e);
            throw new RuntimeException("상품 조회 실패: " + e.getMessage(), e);
        }
    }

    // 특정 tag에 해당하는 상품 목록을 가져오는 메서드
    public List<GoodsDTO> getGoodsByTag(String tag) {
        try {
            // tag에 맞는 상품 리스트를 가져오기
            List<Goods> goodsList = goodsRepository.findByTag(tag);

            // Goods 엔티티를 GoodsDTO로 변환하여 반환
            List<GoodsDTO> goodsDTOList = goodsList.stream()
                    .map(GoodsDTO::new) // 각 Goods를 GoodsDTO로 변환
                    .collect(Collectors.toList());

            log.info("{} tag에 해당하는 상품 목록 조회 완료", tag);
            return goodsDTOList;
        } catch (Exception e) {
            log.error("상품 목록 조회 중 오류 발생", e);
            throw new RuntimeException("상품 목록 조회 실패: " + e.getMessage(), e);
        }
    }
}