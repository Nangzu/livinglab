package com.example.livinglab.Service;

import com.example.livinglab.Entity.Filestorage;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Repository.FilestorageRepository;
import com.example.livinglab.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FilestorageService {

    @Autowired
    private FilestorageRepository filestorageRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    // 파일 업로드
    public void uploadFile(MultipartFile file, Long goodsnum) throws IOException {
        // 상품번호에 맞는 Goods 객체 찾기
        Goods goods = goodsRepository.findById(goodsnum)
                .orElseThrow(() -> new RuntimeException("Goods not found"));

        // Filestorage 객체 생성 후 데이터 설정
        Filestorage filestorage = new Filestorage();
        filestorage.setFiledata(file.getBytes());
        filestorage.setFilename(file.getOriginalFilename());  // 파일명 저장
        filestorage.setFiletype(file.getContentType());  // 파일타입 저장
        filestorage.setGoods(goods);  // Goods 객체 연결

        // 파일을 DB에 저장
        filestorageRepository.save(filestorage);
    }

    // 파일 다운로드
    public byte[] downloadFile(Long id) {
        // DB에서 파일 데이터 가져오기
        Filestorage filestorage = filestorageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        // 파일 데이터 가져오기
        return filestorage.getFiledata();
    }
}