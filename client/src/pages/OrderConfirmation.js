import React, { useState, useEffect } from "react";
import axios from "axios";
import "./orderConfirmation.css";

const OrderConfirmation = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const user = JSON.parse(sessionStorage.getItem("user"));
        if (!user || !user.userid) {
          throw new Error("사용자 정보를 찾을 수 없습니다.");
        }

        // 서버에서 사용자 주문 내역 가져오기
        const response = await axios.get(
          `http://localhost:8082/api/orders/user/${user.userid}`,
          { withCredentials: true }
        );
        console.log("주문 데이터:", response.data);
        setOrders(response.data);
      } catch (error) {
        console.error("주문 데이터를 가져오는 중 오류 발생:", error);
        setError("주문 데이터를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, []);

  if (loading) {
    return <div className="order-confirmation-loading">로딩 중...</div>;
  }

  if (error) {
    return <div className="order-confirmation-error">{error}</div>;
  }

  if (orders.length === 0) {
    return <div className="order-confirmation-empty">주문 내역이 없습니다.</div>;
  }

  return (
    <div className="order-confirmation-page">
      <h1>주문 내역</h1>
      {orders.map((order) => (
        <div key={order.ordernum} className="order-item">
          <h2>주문 번호: {order.ordernum}</h2>
          <p>결제 방법: {order.pymethod}</p>
          <p>주문 상태: {order.state}</p>
          <p>총 결제 금액: {order.totalprice.toLocaleString()}원</p>

          <div className="order-products">
            {order.cartnums.map((cartnum, index) => (
              <div key={cartnum} className="product-item">
                <img
                  src={`data:image/png;base64,${order.filedatas[index]}`}
                  alt={order.goodsnames[index]}
                  className="product-thumbnail"
                />
                <p className="product-name">{order.goodsnames[index]}</p>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default OrderConfirmation;
