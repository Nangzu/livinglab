import React, { useState, useEffect } from "react";
import "./DeliveryManager.css";
import axios from "axios";

const DeliveryManager = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    // 주문 목록 가져오기 (백엔드 연동)
    const fetchOrders = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/orders"); // 나중에 백엔드에 맞는 URL로 변경
        if (response.data.length === 0) {
          // 주문이 없으면 샘플 데이터 추가
          setOrders([
            {
              id: "샘플1",
              productName: "샘플 상품 1",
              productCode: "SAMPLE-001",
              productImage: "https://via.placeholder.com/50",
              orderDate: "2025-01-01",
              customerEmail: "sample1@example.com",
              price: 10000,
              status: "결제완료",
            },
            {
              id: "샘플2",
              productName: "샘플 상품 2",
              productCode: "SAMPLE-002",
              productImage: "https://via.placeholder.com/50",
              orderDate: "2025-01-02",
              customerEmail: "sample2@example.com",
              price: 20000,
              status: "배송시작",
            },
          ]);
        } else {
          setOrders(response.data);
        }
      } catch (error) {
        console.error("주문 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터 표시
        setOrders([
          {
            id: "샘플1",
            productName: "샘플 상품 1",
            productCode: "SAMPLE-001",
            productImage: "https://via.placeholder.com/50",
            orderDate: "2025-01-01",
            customerEmail: "sample1@example.com",
            price: 10000,
            status: "결제완료",
          },
          {
            id: "샘플2",
            productName: "샘플 상품 2",
            productCode: "SAMPLE-002",
            productImage: "https://via.placeholder.com/50",
            orderDate: "2025-01-02",
            customerEmail: "sample2@example.com",
            price: 20000,
            status: "배송시작",
          },
        ]);
      }
    };

    fetchOrders();
  }, []);

  const handleAction = async (orderId, action) => {
    try {
      // 주문 상태 변경 API 호출
      const response = await axios.post(`http://localhost:8082/api/orders/${orderId}/${action}`);
      if (response.status === 200) {
        alert("상태 변경 완료");
        // 데이터 갱신
        setOrders((prevOrders) =>
          prevOrders.map((order) =>
            order.id === orderId ? { ...order, status: action } : order
          )
        );
      }
    } catch (error) {
      console.error("상태 변경 오류:", error);
      alert("상태 변경 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="delivery-manager-container">
      <h2 className="delivery-manager-title">배송 관리</h2>
      <table className="delivery-table">
        <thead>
          <tr>
            <th>상품정보</th>
            <th>주문일자</th>
            <th>주문고객</th>
            <th>주문번호</th>
            <th>주문금액</th>
            <th>주문상태</th>
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
              <td>{order.id}</td>
              <td>{order.price.toLocaleString()}원</td>
              <td>
                <div className="button-group">
                  {order.status === "결제완료" && (
                    <button
                      className="start-delivery"
                      onClick={() => handleAction(order.id, "배송시작")}
                    >
                      배송시작
                    </button>
                  )}
                  {order.status === "배송시작" && (
                    <button
                      className="review-completed"
                      onClick={() => handleAction(order.id, "리뷰완료")}
                    >
                      리뷰보기
                    </button>
                  )}
                  <button
                    className="cancel-order"
                    onClick={() => handleAction(order.id, "주문취소")}
                  >
                    주문취소
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

export default DeliveryManager;
