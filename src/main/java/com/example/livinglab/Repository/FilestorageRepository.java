package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Filestorage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface FilestorageRepository extends JpaRepository<Filestorage, Long> {
    // 특정 상품 번호에 연결된 모든 파일 찾기
    List<Filestorage> findByGoods_Goodsnum(Long goodsnum);

    // 특정 상품명에 연결된 모든 파일 찾기
    List<Filestorage> findByGoods_Goodsname(String goodsname);

    // 특정 상품 번호에 연결된 첫 번째 파일 (정렬 조건 필요)
    Optional<Filestorage> findFirstByGoods_GoodsnumOrderByIdAsc(Long goodsnum);

    // 특정 상품 번호에 연결된 두 번째 파일 (정렬 조건 필요)
    default Optional<Filestorage> findSecondByGoods_GoodsnumOrderByIdAsc(Long goodsnum) {
        List<Filestorage> files = findByGoods_Goodsnum(goodsnum);
        return files.size() > 1 ? Optional.of(files.get(1)) : Optional.empty();
    }
}