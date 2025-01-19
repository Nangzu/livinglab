import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./order.css";

const Order = () => {
  const [deliveryMessage, setDeliveryMessage] = useState("부재시 문 앞에 놔주세요");
  const [paymentMethod, setPaymentMethod] = useState("무통장입금");
  const [recipientName, setRecipientName] = useState("");
  const [recipientPhone, setRecipientPhone] = useState("");
  const [address, setAddress] = useState("");
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const DELIVERY_FEE = 3000;
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    setRecipientName(user?.username || "사용자 이름");
    setRecipientPhone(user?.phone || "010-0000-0000");
    setAddress(user?.address || "배송지 주소를 입력하세요");

    const fetchCartItems = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/cart/check");
        setCartItems(response.data);
        console.log("카트데이터:", response.data);
        const total = response.data.reduce((sum, item) => sum + item.price * item.quantity, 0);
        setTotalPrice(total);
      } catch (error) {
        console.error("Failed to fetch cart items:", error);
      }
    };

    fetchCartItems();
  }, []);

  const handleOrderSubmit = async () => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    if (!user) {
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }
  
    try {
      const cartIds = cartItems.map((item) => item.cartnum).join(","); // 배열을 문자열로 변환
      const orderData = {
        recipientName,
        recipientPhone,
        address,
        deliveryMessage,
        pymethod : paymentMethod,
        totalAmount: totalPrice + DELIVERY_FEE,
        state : "결제 대기",
      };
  
      console.log("주문 데이터:", orderData);
  
      // 수정된 요청
      const response = await axios.post(
        `http://localhost:8082/api/orders/create`,
        orderData,
        {
          params: { cartIds },
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
  
      console.log("주문 성공:", response.data);
      alert("주문이 완료되었습니다.");
      navigate("/order-confirmation");
    } catch (error) {
      console.error("주문 실패:", error);
      alert("주문에 실패했습니다. 다시 시도해주세요.");
    }
  };
  
  

  return (
    <div className="order-page">
      <div className="order-delivery">
        <h2>배송지</h2>
        <div className="delivery-info">
          <p>{recipientName}</p>
          <p>{recipientPhone}</p>
          <p>{address}</p>
          <select
            value={deliveryMessage}
            onChange={(e) => setDeliveryMessage(e.target.value)}
            className="delivery-message-dropdown"
          >
            <option value="부재시 문 앞에 놔주세요">부재시 문 앞에 놔주세요</option>
            <option value="관리사무소에 맡겨 주세요">관리사무소에 맡겨 주세요</option>
            <option value="경비실에 맡겨 주세요">경비실에 맡겨 주세요</option>
            <option value="배송 전 연락 부탁드립니다">배송 전 연락 부탁드립니다</option>
          </select>
        </div>
      </div>

      <div className="order-items">
        <h2>주문상품</h2>
        {cartItems.length > 0 ? (
          cartItems.map((item) => (
            <div key={item.cartnum} className="order-item">
              <img src={`data:image/png;base64,${item.filedata}`} alt={item.goodsname} />
              <div className="item-details">
                <h3>{item.goodsname}</h3>
                <p>수량: {item.quantity}</p>
                <p>가격: {item.price.toLocaleString()}원</p>
              </div>
            </div>
          ))
        ) : (
          <p>장바구니가 비어 있습니다.</p>
        )}
      </div>

      <div className="order-payment">
        <h2>결제수단</h2>
        <select
          value={paymentMethod}
          onChange={(e) => setPaymentMethod(e.target.value)}
          className="payment-method-dropdown"
        >
          <option value="무통장입금">무통장입금</option>
          <option value="카드결제">카드결제</option>
          <option value="휴대폰결제">휴대폰결제</option>
          <option value="계좌이체">계좌이체</option>
        </select>
      </div>

      <div className="order-summary">
        <h2>결제금액</h2>
        <div className="summary-detail">
          <p>상품금액</p>
          <p>{totalPrice.toLocaleString()}원</p>
          <p>배송비</p>
          <p>{DELIVERY_FEE.toLocaleString()}원</p>
          <p>결제예정금액</p>
          <p>{(totalPrice + DELIVERY_FEE).toLocaleString()}원</p>
        </div>
        <button className="pay-button" onClick={handleOrderSubmit}>
          결제하기
        </button>
      </div>
    </div>
  );
};

export default Order;
