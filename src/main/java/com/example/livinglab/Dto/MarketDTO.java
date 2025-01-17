package com.example.livinglab.Dto;

import com.example.livinglab.Entity.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketDTO {
    private Long marketcode;  // 마켓 코드
    private Long usernum;     // 사용자 번호 (User 엔티티와 연관)
    private String marketname;  // 마켓 이름

    public MarketDTO(Market market) {
        this.marketcode = market.getMarketcode();
        this.usernum = market.getUser().getUsernum();
        this.marketname = market.getMarketname();
    }

    public Long getUsernum() {
        return usernum;
    }

    public void setUsernum(Long usernum) {
        this.usernum = usernum;
    }
}