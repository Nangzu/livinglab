package com.example.livinglab.Service;

import com.example.livinglab.Dto.CartDTO;
import com.example.livinglab.Entity.Cart;
import com.example.livinglab.Entity.Filestorage;
import com.example.livinglab.Entity.Goods;
import com.example.livinglab.Entity.User;
import com.example.livinglab.Repository.CartRepository;
import com.example.livinglab.Repository.FilestorageRepository;
import com.example.livinglab.Repository.GoodsRepository;
import com.example.livinglab.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilestorageRepository filestorageRepository;

    public CartDTO addToCart(CartDTO cartDTO) {
        // 상품 조회
        Goods goods = goodsRepository.findById(cartDTO.getGoodsnum())
                .orElseThrow(() -> new IllegalArgumentException("Invalid goods ID"));

        System.out.println(goods.getGoodsname());

        // 사용자 조회
        User user = userRepository.findById(cartDTO.getUsernum())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Filestorage filestorage = filestorageRepository.findFirstByGoods_GoodsnumOrderByIdAsc(cartDTO.getGoodsnum()).orElse(null);
        byte[] filedata = filestorage.getFiledata();

        int totalPrice = 0;  // 총 가격을 계산하기 위한 변수

        int quantity = cartDTO.getQuantity();
        int price = goods.getPrice();

        totalPrice = quantity * price;

        // 새로운 카트 생성
        Cart cart = new Cart();
        cart.setGoods(goods);
        cart.setUser(user);
        cart.setGoodsname(goods.getGoodsname());
        cart.setQuantity(cartDTO.getQuantity());
        cart.setTotalprice(totalPrice);

        // 카트 저장
        Cart savedCart = cartRepository.save(cart);

        // 저장된 카트를 DTO로 변환
        return new CartDTO(savedCart, filedata);
    }

    @Transactional
    public List<CartDTO> getCartItems(Long usernum) {
        System.out.println("Fetching cart items for usernum: " + usernum);

        // 사용자 확인
        User user = userRepository.findById(usernum)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 특정 사용자의 카트를 조회
        List<Cart> cartList = cartRepository.findCartsByUser_Usernum(usernum);

        // 조회된 카트를 DTO로 변환, 파일 데이터 추가
        return cartList.stream()
                .filter(Objects::nonNull) // null을 걸러내기
                .map(cart -> {
                    // 상품 번호로 파일 스토리지 조회
                    byte[] filedata = null;
                    if (cart.getGoods() != null) {
                        Filestorage filestorage = filestorageRepository
                                .findFirstByGoods_GoodsnumOrderByIdAsc(cart.getGoods().getGoodsnum())
                                .orElse(null);

                        if (filestorage != null) {
                            filedata = filestorage.getFiledata();
                        }
                    }

                    // CartDTO 생성
                    return new CartDTO(cart, filedata);
                })
                .collect(Collectors.toList());
    }

    public void removeFromCart(Long cartId) {
        // 카트 항목 조회
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        // 카트 항목 삭제
        cartRepository.delete(cart);
    }

    public CartDTO updateCart(Long cartId, CartDTO cartDTO) {
        // 카트 항목 조회
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        // 새로운 수량과 상품 적용
        Goods goods = goodsRepository.findById(cartDTO.getGoodsnum())
                .orElseThrow(() -> new IllegalArgumentException("Invalid goods ID"));

        Filestorage filestorage = filestorageRepository.findFirstByGoods_GoodsnumOrderByIdAsc(cartDTO.getGoodsnum()).orElse(null);;
        byte[] filedata = filestorage.getFiledata();

        int totalPrice = 0;  // 총 가격을 계산하기 위한 변수

        int quantity = cartDTO.getQuantity();
        int price = goods.getPrice();

        totalPrice = quantity * price;

        cart.setGoods(goods);
        cart.setGoodsname(goods.getGoodsname());
        cart.setQuantity(cartDTO.getQuantity());
        cart.setTotalprice(totalPrice);

        // 업데이트된 카트 저장
        Cart updatedCart = cartRepository.save(cart);

        // 저장된 카트를 DTO로 변환
        return new CartDTO(updatedCart, filedata);
    }

}