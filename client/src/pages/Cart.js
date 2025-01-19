import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./cart.css";

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const DELIVERY_FEE = 3000;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        const response = await axios.get(`http://localhost:8082/api/cart/check`);
        setCartItems(response.data);
        console.log("유저 카트 데이터:", response.data);
        const total = response.data.reduce((sum, item) => sum + (item.price || 0) * (item.quantity || 0), 0);
        setTotalPrice(total);
      } catch (error) {
        console.error("Failed to fetch cart items:", error);
      }
    };

    fetchCartItems();
  }, []);

  const handleDeleteItem = async (cartId) => {
    try {
      const sessionUser = JSON.parse(sessionStorage.getItem("user"));
      const userId = sessionUser.userid;
      await axios.delete(`http://localhost:8082/api/orders/cart/${userId}/${cartId}`);
      setCartItems((prevItems) => prevItems.filter((item) => item.cartnum !== cartId));
      console.log(`Item with cartId ${cartId} deleted.`);
    } catch (error) {
      console.error("Failed to delete cart item:", error);
      alert("아이템 삭제에 실패했습니다.");
    }
  };

  const handleCheckout = () => {
    navigate("/order");
  };

  return (
    <div className="cart-page">
      {cartItems.length > 0 ? (
        <>
          <div className="cart-items">
            <div className="cart-header">
              <input type="checkbox" />
              <span>전체선택</span>
            </div>
            {cartItems.map((item) => (
              <div key={item.cartnum} className="cart-item">
                <div className="item-header">
                  <input type="checkbox" />
                  <button
                    className="delete-button"
                    onClick={() => handleDeleteItem(item.cartnum)}
                  >
                    &times;
                  </button>
                </div>
                <div className="item-details">
                  <img src={`data:image/png;base64,${item.filedata}`} alt={item.goodsname} />
                  <div className="item-info">
                    <h3>{item.goodsname}</h3>
                    <p>{item.description}</p>
                    <div className="item-prices">
                      <span className="current-price">{(item.price || 0).toLocaleString()}원</span>
                    </div>
                  </div>
                </div>
                <div className="quantity-controls">
                  <button>-</button>
                  <span>{item.quantity}</span>
                  <button>+</button>
                </div>
                <div className="item-total-price">
                  {((item.price || 0) * (item.quantity || 0)).toLocaleString()}원
                </div>
              </div>
            ))}
          </div>
          <div className="cart-summary">
            <h3>결제금액</h3>
            <div className="summary-detail">
              <span>상품금액</span>
              <span>{(totalPrice || 0).toLocaleString()}원</span>
            </div>
            <div className="summary-detail">
              <span>배송비</span>
              <span>{DELIVERY_FEE.toLocaleString()}원</span>
            </div>
            <div className="summary-total">
              <span>결제예정금액</span>
              <span>{(totalPrice + DELIVERY_FEE).toLocaleString()}원</span>
            </div>
            <button className="checkout-button" onClick={handleCheckout}>
              결제하러가기
            </button>
          </div>
        </>
      ) : (
        <div className="empty-cart">
          <h2>장바구니에 물품이 없습니다.</h2>
        </div>
      )}
    </div>
  );
};

export default Cart;
