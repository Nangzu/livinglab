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
    public OrderDTO createOrder(Long userid, List<Long> cartnum) {
        // 사용자 확인
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 선택된 카트 정보 가져오기 (선택한 cart_num만)
        List<Cart> selectedCarts = cartRepository.findAllByCartnumIn(cartnum);

        // 카트가 없으면 예외 처리
        if (selectedCarts.isEmpty()) {
            throw new RuntimeException("No selected items in the cart");
        }

        String paid = "unpaid";
        String paymethod = "unpaid";

        // 주문 생성
        Order order = new Order();
        order.setUser(user);  // 주문한 사용자
        order.setCarts(selectedCarts);  // 여러 개의 카트를 주문에 추가
        order.setPymethod(paymethod);
        order.setState(paid);
        for (Cart cart : selectedCarts) {
            cart.setOrder(order);  // 각 카트에 주문을 설정
        }

        // 주문 저장
        order = orderRepository.save(order);

        // DTO로 반환
        return new OrderDTO(
                order.getOrdernum(),
                order.getPymethod(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보 (user_num)
                order.getState()  // 선택된 카트 번호 리스트
        );
    }

    // 사용자의 모든 주문 가져오기
    public List<OrderDTO> getOrdersByUser(String userid) {
        List<Order> orders = orderRepository.findByUser_Userid(userid);  // 사용자 ID로 주문을 조회

        return orders.stream().map(order -> {
            // Order 객체에서 CartDTO 정보 생성

            // UserDTO 생성
            UserDTO userDTO = new UserDTO(
                    order.getUser().getUsernum(),
                    order.getUser().getUsername(),
                    order.getUser().getPhone(),
                    order.getUser().getEmail(),
                    order.getUser().getAddress(),
                    order.getUser().getUserid(),
                    null,
                    order.getUser().getRole().getRoleCode()
            );

            return new OrderDTO(
                    order.getOrdernum(),
                    order.getPymethod(),  // 결제 방법
                    order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보 (UserDTO에서 user_num)
                    order.getState()  // Cart의 cart_num 리스트
            );
        }).collect(Collectors.toList());
    }

    // 특정 주문의 상세 정보 가져오기
    public OrderDTO getOrderDetails(Long orderId) {
        // 주문 ID로 Order 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 단일 Cart에 대한 DTO 생성
        // UserDTO 생성
        UserDTO userDTO = new UserDTO(
                order.getUser().getUsernum(),
                order.getUser().getUsername(),
                order.getUser().getPhone(),
                order.getUser().getEmail(),
                order.getUser().getAddress(),
                order.getUser().getUserid(),
                null,
                order.getUser().getRole().getRoleCode()
        );

        // OrderDTO 반환
        return new OrderDTO(
                order.getOrdernum(),
                order.getPymethod(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보
                order.getState()
        );
    }

    // 장바구니에서 특정 상품 제거
    public void removeFromCart(Long cartnum, String userid) {
        Cart cart = cartRepository.findByCartnumAndUser_Userid(cartnum, userid)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cartRepository.delete(cart);
    }

    public OrderDTO updateOrderState(Long ordernum, Long usernum, String pymethod) {
        // 사용자 확인
        User user = userRepository.findById(usernum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 사용자에 해당하는 미결제 주문 찾기 (주문번호와 사용자 ID로 찾기)
        Order order = orderRepository.findByOrdernumAndUser_Usernum(ordernum, usernum);

        if (order == null) {
            throw new RuntimeException("Order not found for the user.");
        }

        System.out.println(order.getOrdernum());

        // pymethod와 state를 수정
        order.setPymethod(pymethod);  // 전달받은 pymethod로 설정
        order.setState("paid");  // 상태를 "paid"로 변경

        orderRepository.save(order);
        // 주문 저장 (자동 커밋)
        order = orderRepository.save(order);

        // DTO로 반환
        return new OrderDTO(
                order.getOrdernum(),
                order.getPymethod(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보 (user_num)
                order.getState()  // 주문 상태
        );
    }
}