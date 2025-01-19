import React, { useState, useEffect } from "react";
import "./DeliveryStop.css";
import axios from "axios";

const DeliveryStop = () => {
  const [stops, setStops] = useState([]);

  useEffect(() => {
    // 배송 중지 요청 데이터 가져오기 (샘플 데이터 + 백엔드 연동 준비)
    const fetchStops = async () => {
      try {
        // 나중에 백엔드 API 연동
        const response = await axios.get("http://localhost:8082/api/delivery-stop");
        if (response.data.length === 0) {
          setStops([
            {
              id: "중지1",
              productName: "무안 양파(10KG)",
              productCode: "AMS1257",
              productImage: "https://via.placeholder.com/50",
              requestDate: "2024-11-01 01:22:50",
              customerEmail: "parkjw2000@gmail.com",
              orderNumber: "ORD12345",
              reason: "오배송 요청",
              status: "처리중",
            },
            {
              id: "중지2",
              productName: "제주 감귤(5KG)",
              productCode: "AMS5678",
              productImage: "https://via.placeholder.com/50",
              requestDate: "2024-11-02 14:35:12",
              customerEmail: "testuser@example.com",
              orderNumber: "ORD67890",
              reason: "고객 요청",
              status: "처리중",
            },
          ]);
        } else {
          setStops(response.data); // 백엔드 데이터 사용
        }
      } catch (error) {
        console.error("배송 중지 요청 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터 표시
        setStops([
          {
            id: "중지1",
            productName: "무안 양파(10KG)",
            productCode: "AMS1257",
            productImage: "https://via.placeholder.com/50",
            requestDate: "2024-11-01 01:22:50",
            customerEmail: "parkjw2000@gmail.com",
            orderNumber: "ORD12345",
            reason: "오배송 요청",
            status: "처리중",
          },
          {
            id: "중지2",
            productName: "제주 감귤(5KG)",
            productCode: "AMS5678",
            productImage: "https://via.placeholder.com/50",
            requestDate: "2024-11-02 14:35:12",
            customerEmail: "testuser@example.com",
            orderNumber: "ORD67890",
            reason: "고객 요청",
            status: "처리중",
          },
        ]);
      }
    };

    fetchStops();
  }, []);

  const handleAction = async (stopId, action) => {
    try {
      // 백엔드 API 호출 준비
      const response = await axios.post(`http://localhost:8082/api/delivery-stop/${stopId}/${action}`);
      if (response.status === 200) {
        alert(`요청 ${action} 작업이 완료되었습니다.`);
        // 데이터 상태 업데이트
        setStops((prevStops) =>
          prevStops.map((stop) =>
            stop.id === stopId ? { ...stop, status: action === "approve" ? "승인됨" : "거절됨" } : stop
          )
        );
      }
    } catch (error) {
      console.error("상태 변경 오류:", error);
      alert("상태 변경 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="delivery-stop-container">
      <h2 className="delivery-stop-title">출고 중지 요청</h2>
      <table className="delivery-stop-table">
        <thead>
          <tr>
            <th>상품정보</th>
            <th>요청일자</th>
            <th>주문고객</th>
            <th>주문번호</th>
            <th>중지 사유</th>
            <th>처리 상태</th>
          </tr>
        </thead>
        <tbody>
          {stops.map((stop) => (
            <tr key={stop.id}>
              <td>
                <img src={stop.productImage} alt={stop.productName} className="product-image" />
                <div>{stop.productName}</div>
                <div>({stop.productCode})</div>
              </td>
              <td>{stop.requestDate}</td>
              <td>{stop.customerEmail}</td>
              <td>{stop.orderNumber}</td>
              <td>{stop.reason}</td>
              <td>
                <div className="button-group">
                  <button
                    className="approve-stop"
                    onClick={() => handleAction(stop.id, "approve")}
                  >
                    승인
                  </button>
                  <button
                    className="reject-stop"
                    onClick={() => handleAction(stop.id, "reject")}
                  >
                    거절
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

export default DeliveryStop;
