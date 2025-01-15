package com.example.livinglab.Repository;

import com.example.livinglab.Entity.Filestorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilestorageRepository extends JpaRepository<Filestorage, Long> {
    List<Filestorage> findByGoods_Goodsnum(Long goodsnum);
}