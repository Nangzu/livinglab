package com.example.livinglab.Controller;

import com.example.livinglab.Dto.OrderDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Repository.CartRepository;
import com.example.livinglab.Repository.OrderRepository;
import com.example.livinglab.Entity.Order;
import com.example.livinglab.Entity.Cart;
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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    // 주문 생성
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam List<Long> cartIds,
                                                HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Long usernum = userDTO.getUsernum();

        // 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 사용자 정보가 있는 경우, 주문 생성
        OrderDTO createdOrder = orderService.createOrder(usernum, cartIds);
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
        if (!userDTO.getUserid().toString().equals(userId)) {
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
        if (orderDetails == null || !orderDetails.getUsernum().equals(userDTO.getUsernum())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        return ResponseEntity.ok(orderDetails);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long orderId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        String userId = userDTO.getUserid();

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        try {
            // 서비스 계층에서 오더 및 카트 삭제 처리
            orderService.removeOrderAndCarts(orderId, userId);
            return ResponseEntity.noContent().build();  // 204 상태 코드 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }
    }

    @PutMapping("/paid")
    public ResponseEntity<OrderDTO> updateOrderState(@RequestBody OrderDTO requestDTO, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에 사용자 정보가 없으면 Unauthorized 상태 코드와 함께 응답
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401 상태 코드 반환
        }

        // 세션 사용자와 요청된 userId가 일치하는지 확인
        if (!userDTO.getUsernum().equals(requestDTO.getUsernum())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 상태 코드 반환
        }

        if (requestDTO.getOrdernum() == null || requestDTO.getUsernum() == null || requestDTO.getPymethod() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 상태 코드 반환
        }

        OrderDTO orderDTO = orderService.updateOrderState(requestDTO.getOrdernum(), requestDTO.getUsernum(), requestDTO.getPymethod());

        return ResponseEntity.ok(orderDTO);
    }
}