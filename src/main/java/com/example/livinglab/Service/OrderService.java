package com.example.livinglab.Service;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Dto.OrderDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.Order;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CartRepository;
import com.example.livinglab.Repository.OrderRepository;
import com.example.livinglab.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // 주문 생성
    @Transactional
    public OrderDTO createOrder(Long userid, List<Long> cart_num, String py_method) {
        // 사용자 확인
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 선택된 카트 정보 가져오기 (선택한 cart_num만)
        List<Cart> selectedCarts = cartRepository.findAllByCartnumIn(cart_num);

        // 카트가 없으면 예외 처리
        if (selectedCarts.isEmpty()) {
            throw new RuntimeException("No selected items in the cart");
        }

        // 주문 생성
        Order order = new Order();
        order.setUser(user);  // 주문한 사용자
        order.setCarts(selectedCarts);  // 여러 개의 카트를 주문에 추가

        // 주문 저장
        order = orderRepository.save(order);

        // DTO로 반환
        return new OrderDTO(
                order.getOrder_num(),
                py_method,  // 결제 방법
                order.getUser() != null ? order.getUser().getUser_num() : null,  // 사용자 정보 (user_num)
                selectedCarts.stream().map(Cart::getCartnum).collect(Collectors.toList())  // 선택된 카트 번호 리스트
        );
    }

    // 사용자의 모든 주문 가져오기
    public List<OrderDTO> getOrdersByUser(String userid) {
        List<Order> orders = orderRepository.findByUser_Userid(userid);  // 사용자 ID로 주문을 조회

        return orders.stream().map(order -> {
            // Order 객체에서 CartDTO 정보 생성
            List<CartDTO> cartDTOs = order.getCart() != null ? List.of(new CartDTO(
                    order.getCart().getCartnum(),
                    order.getCart().getGoods().getGoodsnum(),
                    order.getCart().getGoods().getGoodsname(),
                    order.getCart().getUser().getUser_num(),
                    order.getCart().getQuantity()
            )) : List.of();  // Cart가 없는 경우 빈 리스트 처리

            // UserDTO 생성
            UserDTO userDTO = new UserDTO(
                    order.getUser().getUser_num(),
                    order.getUser().getUser_name(),
                    order.getUser().getPhone(),
                    order.getUser().getEmail(),
                    order.getUser().getAddress(),
                    order.getUser().getUserid(),
                    null,
                    order.getUser().getRole().getRoleCode()
            );

            return new OrderDTO(
                    order.getOrder_num(),
                    order.getPy_method(),  // 결제 방법
                    order.getUser() != null ? order.getUser().getUser_num() : null,  // 사용자 정보 (UserDTO에서 user_num)
                    cartDTOs.stream().map(cart -> cart.getCartnum()).collect(Collectors.toList())  // Cart의 cart_num 리스트
            );
        }).collect(Collectors.toList());
    }

    // 특정 주문의 상세 정보 가져오기
    public OrderDTO getOrderDetails(Long orderId) {
        // 주문 ID로 Order 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 단일 Cart에 대한 DTO 생성
        CartDTO cartDTO = (order.getCart() != null) ? new CartDTO(
                order.getCart().getCartnum(),
                order.getCart().getGoods().getGoodsnum(),
                order.getCart().getGoods().getGoodsname(),
                order.getCart().getUser().getUser_num(),
                order.getCart().getQuantity()
                ) : null;  // Cart가 없으면 null 처리

        // UserDTO 생성
        UserDTO userDTO = new UserDTO(
                order.getUser().getUser_num(),
                order.getUser().getUser_name(),
                order.getUser().getPhone(),
                order.getUser().getEmail(),
                order.getUser().getAddress(),
                order.getUser().getUserid(),
                null,
                order.getUser().getRole().getRoleCode()
        );

        // OrderDTO 반환
        return new OrderDTO(
                order.getOrder_num(),
                order.getPy_method(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUser_num() : null,  // 사용자 정보
                order.getCarts() != null ? order.getCarts().stream()  // 여러 카트 정보
                        .map(Cart::getCartnum)  // 각 Cart의 cartnum을 가져와 리스트로 변환
                        .collect(Collectors.toList()) : null  // 리스트로 변환
        );
    }

    // 장바구니에서 특정 상품 제거
    public void removeFromCart(String userid, Long goodsnum) {
        Cart cart = cartRepository.findByUser_UseridAndGoods_Goodsnum(userid, goodsnum)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cartRepository.delete(cart);
    }
}