package com.example.livinglab.Controller;

import com.example.livinglab.Dto.LoginDTO;
import com.example.livinglab.Dto.MarketDTO;
import com.example.livinglab.Dto.OrderDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    @Autowired
    private OrderService MarketService;

    @PostMapping("/create")
    public ResponseEntity<Void> removeFromCart(@RequestBody MarketDTO marketdto, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        Long usernum = userDTO.getUsernum();

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null || userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 장바구니에서 상품 삭제
        MarketService.createMarket(marketdto);
        return ResponseEntity.noContent().build();  // 204 상태 코드 반환
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeFromCart(@RequestBody LoginDTO logindto, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        Long usernum = userDTO.getUsernum();

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null || userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        if (!userDTO.getUserid().equals(logindto.getUserid())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        // 장바구니에서 상품 삭제
        MarketService.removeMarket(usernum);
        return ResponseEntity.noContent().build();  // 204 상태 코드 반환
    }

    @PutMapping("/update")
    public ResponseEntity<OrderDTO> updateOrderState(@RequestPart LoginDTO logindto, @RequestPart MarketDTO marketdto, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        Long usernum = userDTO.getUsernum();

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null || userDTO.getRole() != 2L) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        if (!userDTO.getUserid().equals(logindto.getUserid())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        // 장바구니에서 상품 삭제
        MarketService.updateMarket(usernum, marketdto);
        return ResponseEntity.noContent().build();  // 204 상태 코드 반환
    }
}
