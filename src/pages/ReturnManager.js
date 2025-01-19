import React, { useState, useEffect } from "react";
import "./ReturnManager.css";
import axios from "axios";

const ReturnManager = () => {
  const [returns, setReturns] = useState([]);

  useEffect(() => {
    // 반품 요청 데이터 가져오기 (샘플 데이터 + 백엔드 연동 준비)
    const fetchReturns = async () => {
      try {
        // 나중에 백엔드 API 연동
        const response = await axios.get("http://localhost:8082/api/returns");
        if (response.data.length === 0) {
          // 샘플 데이터로 초기화
          setReturns([
            {
              id: "반품1",
              productName: "무안 양파(10KG)",
              productCode: "AMS1257",
              productImage: "https://via.placeholder.com/50",
              requestDate: "2024-11-01 01:22:50",
              customerEmail: "sample1@example.com",
              orderNumber: "ORD12345",
              reason: "상품 하자",
              status: "처리중",
            },
            {
              id: "반품2",
              productName: "제주 감귤(5KG)",
              productCode: "AMS5678",
              productImage: "https://via.placeholder.com/50",
              requestDate: "2024-11-02 14:35:12",
              customerEmail: "sample2@example.com",
              orderNumber: "ORD67890",
              reason: "고객 요청",
              status: "처리중",
            },
          ]);
        } else {
          setReturns(response.data); // 백엔드 데이터 사용
        }
      } catch (error) {
        console.error("반품 요청 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터로 초기화
        setReturns([
          {
            id: "반품1",
            productName: "무안 양파(10KG)",
            productCode: "AMS1257",
            productImage: "https://via.placeholder.com/50",
            requestDate: "2024-11-01 01:22:50",
            customerEmail: "sample1@example.com",
            orderNumber: "ORD12345",
            reason: "상품 하자",
            status: "처리중",
          },
          {
            id: "반품2",
            productName: "제주 감귤(5KG)",
            productCode: "AMS5678",
            productImage: "https://via.placeholder.com/50",
            requestDate: "2024-11-02 14:35:12",
            customerEmail: "sample2@example.com",
            orderNumber: "ORD67890",
            reason: "고객 요청",
            status: "처리중",
          },
        ]);
      }
    };

    fetchReturns();
  }, []);

  const handleAction = async (returnId, action) => {
    try {
      // 백엔드 API 호출 준비
      const response = await axios.post(`http://localhost:8082/api/returns/${returnId}/${action}`);
      if (response.status === 200) {
        alert(`반품 요청 ${action === "approve" ? "승인" : "거절"} 처리 완료`);
        // 데이터 상태 업데이트
        setReturns((prevReturns) =>
          prevReturns.map((ret) =>
            ret.id === returnId ? { ...ret, status: action === "approve" ? "승인됨" : "거절됨" } : ret
          )
        );
      }
    } catch (error) {
      console.error("반품 상태 변경 오류:", error);
      alert("상태 변경 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="return-manager-container">
      <h2 className="return-manager-title">반품 관리</h2>
      <table className="return-manager-table">
        <thead>
          <tr>
            <th>상품정보</th>
            <th>요청일자</th>
            <th>주문고객</th>
            <th>주문번호</th>
            <th>반품 사유</th>
            <th>반품 상태</th>
          </tr>
        </thead>
        <tbody>
          {returns.map((ret) => (
            <tr key={ret.id}>
              <td>
                <img src={ret.productImage} alt={ret.productName} className="product-image" />
                <div>{ret.productName}</div>
                <div>({ret.productCode})</div>
              </td>
              <td>{ret.requestDate}</td>
              <td>{ret.customerEmail}</td>
              <td>{ret.orderNumber}</td>
              <td>{ret.reason}</td>
              <td>
                <div className="button-group">
                  <button
                    className="approve-return"
                    onClick={() => handleAction(ret.id, "approve")}
                  >
                    승인
                  </button>
                  <button
                    className="reject-return"
                    onClick={() => handleAction(ret.id, "reject")}
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

export default ReturnManager;
