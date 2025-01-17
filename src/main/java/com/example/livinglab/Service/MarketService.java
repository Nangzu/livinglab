package com.example.livinglab.Service;

import com.example.livinglab.Dto.MarketDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Entity.Role;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.MarketRepository;
import com.example.livinglab.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarketService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void configureModelMapper() {
        modelMapper.typeMap(Market.class, MarketDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUsernum(), MarketDTO::setUsernum);
        });
    }

    public MarketDTO createmarket(MarketDTO marketdto, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            throw new RuntimeException("User is not logged in");
        }

        // 사용자 정보 확인
        Long num = userDTO.getUsernum();
        Optional<User> userOpt = userRepository.findById(num);

        System.out.println(num);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOpt.get();
        System.out.println(user.getUsernum());

        // Market 엔티티 생성 및 설정
        Market market = modelMapper.map(marketdto, Market.class);
        market.setUser(user);
        System.out.println(market.getUser().getUsernum());
        market.setMarketname(marketdto.getMarketname());

        // Market 저장
        Market savedMarket = marketRepository.save(market);

        // 저장된 Market 엔티티를 MarketDTO로 변환하여 반환
        return modelMapper.map(savedMarket, MarketDTO.class);
    }
}
