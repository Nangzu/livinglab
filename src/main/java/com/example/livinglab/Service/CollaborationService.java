package com.example.livinglab.Service;

import com.example.livinglab.Entity.Collaboration;
import com.example.livinglab.Entity.Market;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CollaborationRepository;
import com.example.livinglab.Repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollaborationService {

    @Autowired
    private CollaborationRepository collaborationRepository;

    @Autowired
    private MarketRepository marketRepository;

    public Collaboration createCollaboration(User user, Long marketCode) {
        // Market 조회
        Market market = marketRepository.findById(marketCode)
                .orElseThrow(() -> new RuntimeException("Market not found"));

        // Collaboration 생성
        Collaboration collaboration = new Collaboration();
        collaboration.setUser(user);
        collaboration.setMarket(market);

        // 저장
        return collaborationRepository.save(collaboration);
    }
}