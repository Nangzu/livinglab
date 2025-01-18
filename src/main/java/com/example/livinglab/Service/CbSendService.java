package com.example.livinglab.Service;

import com.example.livinglab.Dto.CbsendDTO;
import com.example.livinglab.Dto.MarketDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.CbSend;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CbSendRepository;
import com.example.livinglab.Repository.MarketRepository;
import com.example.livinglab.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CbSendService {

    @Autowired
    private CbSendRepository cbSendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public CbSend uploadFile(CbsendDTO cbsendDTO, MultipartFile file, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Long num = userDTO.getUsernum();
        Optional<User> userOpt = userRepository.findById(num);
        Optional<Market> market = marketRepository.findById(cbsendDTO.getMarketcode());

        if (userOpt.get().getRole() == null || userOpt.get().getRole().getRoleCode() != 3L) {
            throw new RuntimeException("Only students (roleCode 3) can upload files.");
        }
        System.out.println(file.getOriginalFilename());

        try {
            CbSend cbSend = new CbSend();
            cbSend.setUser(userOpt.get());
            cbSend.setFiles(file.getBytes());
            cbSend.setMarket(market.get());
            cbSend.setOneliner(cbsendDTO.getOneliner());
            cbSend.setFileName(file.getOriginalFilename());
            cbSend.setMarket(market.get());
            return cbSendRepository.save(cbSend);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }


    public CbSend getFileById(Long id) {
        return cbSendRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    public List<String> getAllMarketNames() {
        List<Market> markets = marketRepository.findAll();  // Market 엔티티 리스트 가져오기
        return markets.stream()
                .map(Market::getMarketname)  // 마켓 이름만 추출
                .collect(Collectors.toList());  // String 리스트로 변환
    }

    public List<CbSend> getCbSendsByUserAndMarket(String userId) {
        // 유저를 조회
        User user = userRepository.findByUserid(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 유저가 속한 마켓을 조회
        Optional<Market> market = marketRepository.findByUser_Usernum(user.getUsernum());

        if (market.isEmpty()) {
            throw new RuntimeException("Market not found for the user");
        }

        // 해당 마켓에 속하는 CbSend 데이터 조회
        return cbSendRepository.findByMarket_Marketcode(market.get().getMarketcode());
    }
}
