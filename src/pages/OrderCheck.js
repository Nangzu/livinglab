import React, { useState, useEffect } from "react";
import "./OrderCheck.css";
import axios from "axios";

const OrderCheck = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    // 주문 데이터 가져오기 (샘플 데이터 + 백엔드 연동 준비)
    const fetchOrders = async () => {
      try {
        // 나중에 백엔드 API 경로로 변경
        const response = await axios.get("http://localhost:8082/api/orders");
        if (response.data.length === 0) {
          // 샘플 데이터로 초기화
          setOrders([
            {
              id: "ORD001",
              productName: "무안 양파(10KG)",
              productCode: "AMS1257",
              productImage: "https://via.placeholder.com/50",
              orderDate: "2024-11-01 01:22:50",
              customerEmail: "sample1@example.com",
              orderNumber: "ORD12345",
              deliveryStatus: "배송 중",
            },
            {
              id: "ORD002",
              productName: "제주 감귤(5KG)",
              productCode: "AMS5678",
              productImage: "https://via.placeholder.com/50",
              orderDate: "2024-11-02 14:35:12",
              customerEmail: "sample2@example.com",
              orderNumber: "ORD67890",
              deliveryStatus: "배송 완료",
            },
          ]);
        } else {
          setOrders(response.data); // 백엔드 데이터 사용
        }
      } catch (error) {
        console.error("주문 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터로 초기화
        setOrders([
          {
            id: "ORD001",
            productName: "무안 양파(10KG)",
            productCode: "AMS1257",
            productImage: "https://via.placeholder.com/50",
            orderDate: "2024-11-01 01:22:50",
            customerEmail: "sample1@example.com",
            orderNumber: "ORD12345",
            deliveryStatus: "배송 중",
          },
          {
            id: "ORD002",
            productName: "제주 감귤(5KG)",
            productCode: "AMS5678",
            productImage: "https://via.placeholder.com/50",
            orderDate: "2024-11-02 14:35:12",
            customerEmail: "sample2@example.com",
            orderNumber: "ORD67890",
            deliveryStatus: "배송 완료",
          },
        ]);
      }
    };

    fetchOrders();
  }, []);

  const handleAction = async (orderId, action) => {
    try {
      // 배송 상태 변경 API 호출
      const response = await axios.post(`http://localhost:8082/api/orders/${orderId}/${action}`);
      if (response.status === 200) {
        alert(`주문 상태가 ${action}으로 변경되었습니다.`);
        // 데이터 상태 업데이트
        setOrders((prevOrders) =>
          prevOrders.map((order) =>
            order.id === orderId ? { ...order, deliveryStatus: action } : order
          )
        );
      }
    } catch (error) {
      console.error("주문 상태 변경 오류:", error);
      alert("상태 변경 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="order-check-container">
      <h2 className="order-check-title">주문 조회</h2>
      <table className="order-check-table">
        <thead>
          <tr>
            <th>상품정보</th>
            <th>요청일자</th>
            <th>주문고객</th>
            <th>주문번호</th>
            <th>배송 상태</th>
            <th>배송 상태 변경</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
            <tr key={order.id}>
              <td>
                <img src={order.productImage} alt={order.productName} className="product-image" />
                <div>{order.productName}</div>
                <div>({order.productCode})</div>
              </td>
              <td>{order.orderDate}</td>
              <td>{order.customerEmail}</td>
              <td>{order.orderNumber}</td>
              <td>{order.deliveryStatus}</td>
              <td>
                <div className="button-group">
                  <button
                    className="track-delivery"
                    onClick={() => handleAction(order.id, "배송 추적")}
                  >
                    배송 추적
                  </button>
                  <button
                    className="end-delivery"
                    onClick={() => handleAction(order.id, "배송 완료")}
                  >
                    배송 완료
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OrderCheck;
