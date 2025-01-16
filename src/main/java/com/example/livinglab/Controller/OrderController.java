package com.example.livinglab.Controller;

import com.example.livinglab.Dto.OrderDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 주문 생성
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam Long userId,
                                                @RequestParam List<Long> cartIds,
                                                @RequestParam String paymentMethod, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 사용자 정보가 있는 경우, 주문 생성
        OrderDTO createdOrder = orderService.createOrder(userId, cartIds, paymentMethod);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);  // 201 상태 코드 반환
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable String userId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 세션 사용자와 요청된 userId가 일치하는지 확인
        if (!userDTO.getUser_num().toString().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        // 세션에서 userDTO의 user_num을 사용하여 주문 조회
        List<OrderDTO> orders = orderService.getOrdersByUser(userId);

        return ResponseEntity.ok(orders);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable Long orderId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 주문 조회
        OrderDTO orderDetails = orderService.getOrderDetails(orderId);

        // 주문의 userNum과 세션의 userNum을 비교하여 일치하는지 확인
        if (orderDetails == null || !orderDetails.getUser_num().equals(userDTO.getUser_num())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        return ResponseEntity.ok(orderDetails);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/cart/{userId}/{goodsId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String userId, @PathVariable Long goodsId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 세션의 userId와 요청된 userId가 일치하는지 확인
        if (!userDTO.getUser_num().toString().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        // 장바구니에서 상품 삭제
        orderService.removeFromCart(userId, goodsId);
        return ResponseEntity.noContent().build();  // 204 상태 코드 반환
    }
}