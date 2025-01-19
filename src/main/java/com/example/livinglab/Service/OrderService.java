package com.example.livinglab.Service;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Dto.OrderDTO;
import com.example.livinglab.Dto.UserDTO;
import com.example.livinglab.Entity.*;
import com.example.livinglab.Repository.CartRepository;
import com.example.livinglab.Repository.FilestorageRepository;
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

    @Autowired
    private FilestorageRepository filestorageRepository;

    // 주문 생성
    @Transactional
    public OrderDTO createOrder(Long userid, List<Long> cartnum, String pymethod, String state) {

        // 사용자 확인
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 선택된 카트 정보 가져오기 (선택한 cart_num만)
        List<Cart> selectedCarts = cartRepository.findAllByCartnumIn(cartnum);

        // 카트가 없으면 예외 처리
        if (selectedCarts.isEmpty()) {
            throw new RuntimeException("No selected items in the cart");
        }
//        String paid = "unpaid";
//        String paymethod = "unpaid";

        int totalPrice = 0;  // 총 가격을 계산하기 위한 변수

        for (Cart cart : selectedCarts) {
            // 카트의 상품에서 가격을 가져오기
            Goods goods = cart.getGoods();  // Cart에서 Goods 엔티티 가져오기
            int itemPrice = goods.getPrice();  // 해당 상품의 가격
            int quantity = cart.getQuantity();  // 상품의 수량

            // 가격 계산 (수량 * 가격)
            totalPrice += itemPrice * quantity;
            // 각 카트에 주문을 설정
        }

        // 주문 생성
        Order order = new Order();
        order.setUser(user);  // 주문한 사용자
        order.setCarts(selectedCarts);  // 여러 개의 카트를 주문에 추가
        order.setPymethod(pymethod);
        order.setState(state);
        order.setTotalprice(totalPrice);
        for (Cart cart : selectedCarts) {
            cart.setOrder(order);  // 각 카트에 주문을 설정
        }

        // 주문 저장
        order = orderRepository.save(order);

        // DTO로 반환
        List<Long> cartnums = selectedCarts.stream()
                .map(Cart::getCartnum)  // cartnum을 Long으로 추출
                .collect(Collectors.toList());

        List<String> goodsnames = selectedCarts.stream()
                .map(cart -> cart.getGoods() != null ? cart.getGoods().getGoodsname() : "Unknown")
                .collect(Collectors.toList());

        List<byte[]> filedatas = selectedCarts.stream()
                .map(cart -> {
                    Filestorage filestorage = filestorageRepository.findFirstByGoods_GoodsnumOrderByIdAsc(cart.getGoods().getGoodsnum()).orElse(null);
                    return filestorage != null ? filestorage.getFiledata() : null;  // 파일 데이터 반환
                })
                .collect(Collectors.toList());

        // DTO 반환
        return new OrderDTO(
                order.getOrdernum(),
                order.getPymethod(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보 (user_num)
                order.getState(),  // 주문 상태
                order.getTotalprice(),
                cartnums,
                goodsnames,
                filedatas
        );
    }

    // 사용자의 모든 주문 가져오기
    public List<OrderDTO> getOrdersByUser(String userid) {
        List<Order> orders = orderRepository.findByUser_Userid(userid);  // 사용자 ID로 주문을 조회

        return orders.stream().map(order -> {
            // Order 객체에서 CartDTO 정보 생성
            List<Long> cartnums = order.getCarts().stream()
                    .map(Cart::getCartnum)  // cartnum을 Long으로 추출
                    .collect(Collectors.toList());

            List<String> goodsnames = order.getCarts().stream()
                    .map(cart -> cart.getGoods() != null ? cart.getGoods().getGoodsname() : "Unknown")
                    .collect(Collectors.toList());

            List<byte[]> filedatas = order.getCarts().stream()
                    .map(cart -> {
                        Filestorage filestorage = filestorageRepository.findFirstByGoods_GoodsnumOrderByIdAsc(cart.getGoods().getGoodsnum()).orElse(null);
                        return filestorage != null ? filestorage.getFiledata() : null;
                    })
                    .collect(Collectors.toList());

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
                    order.getState(), // 주문 상태
                    order.getTotalprice(),
                    cartnums,
                    goodsnames,
                    filedatas
            );
        }).collect(Collectors.toList());
    }

    // 특정 주문의 상세 정보 가져오기
    public OrderDTO getOrderDetails(Long orderId) {
        // 주문 ID로 Order 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 카트번호, 상품명, 파일데이터 처리
        List<Long> cartnums = order.getCarts().stream()
                .map(Cart::getCartnum)
                .collect(Collectors.toList());

        List<String> goodsnames = order.getCarts().stream()
                .map(cart -> cart.getGoods() != null ? cart.getGoods().getGoodsname() : "Unknown")
                .collect(Collectors.toList());

        List<byte[]> filedatas = order.getCarts().stream()
                .map(cart -> {
                    Filestorage filestorage = filestorageRepository.findFirstByGoods_GoodsnumOrderByIdAsc(cart.getGoods().getGoodsnum()).orElse(null);
                    return filestorage != null ? filestorage.getFiledata() : null;
                })
                .collect(Collectors.toList());

        // OrderDTO 반환
        return new OrderDTO(
                order.getOrdernum(),
                order.getPymethod(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보
                order.getState(),
                order.getTotalprice(),
                cartnums,
                goodsnames,
                filedatas
        );
    }

    // 장바구니에서 특정 상품 제거
    @Transactional
    public void removeOrderAndCarts(Long orderId, String userId) {
        // 주문 번호로 오더 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 주문의 userId가 세션의 userId와 일치하는지 확인
        if (!order.getUser().getUserid().equals(userId)) {
            throw new RuntimeException("User not authorized to delete this order");
        }

        // 해당 오더와 관련된 카트들을 삭제
        List<Cart> carts = order.getCarts();  // 오더와 관련된 카트 목록

        // 카트들을 삭제
        cartRepository.deleteAll(carts);  // 관련된 카트들을 모두 삭제

        // 오더 삭제
        orderRepository.delete(order);  // 오더도 삭제
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

        // pymethod와 state를 수정
        order.setPymethod(pymethod);  // 전달받은 pymethod로 설정
        order.setState("paid");  // 상태를 "paid"로 변경

        orderRepository.save(order);

        // DTO로 반환
        return new OrderDTO(
                order.getOrdernum(),
                order.getPymethod(),  // 결제 방법
                order.getUser() != null ? order.getUser().getUsernum() : null,  // 사용자 정보
                order.getState(),  // 주문 상태
                order.getTotalprice(),
                null, // cartnums는 이미 저장되어 있음
                null, // goodsnames는 이미 저장되어 있음
                null  // filedatas는 이미 저장되어 있음
        );
    }
}