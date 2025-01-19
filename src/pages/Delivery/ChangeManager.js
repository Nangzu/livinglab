import React, { useState, useEffect } from "react";
import "./ChangeManager.css";
import axios from "axios";

const ChangeManager = () => {
  const [changes, setChanges] = useState([]);

  useEffect(() => {
    // 교환 요청 데이터 가져오기 (샘플 데이터 + 백엔드 연동 준비)
    const fetchChanges = async () => {
      try {
        // 나중에 백엔드 API 경로로 변경
        const response = await axios.get("http://localhost:8082/api/changes");
        if (response.data.length === 0) {
          // 샘플 데이터로 초기화
          setChanges([
            {
              id: "교환1",
              productName: "무안 양파(10KG)",
              productCode: "AMS1257",
              productImage: "https://via.placeholder.com/50",
              requestDate: "2024-11-01 01:22:50",
              customerEmail: "sample1@example.com",
              orderNumber: "ORD12345",
              reason: "교환 요청",
              status: "처리중",
            },
            {
              id: "교환2",
              productName: "제주 감귤(5KG)",
              productCode: "AMS5678",
              productImage: "https://via.placeholder.com/50",
              requestDate: "2024-11-02 14:35:12",
              customerEmail: "sample2@example.com",
              orderNumber: "ORD67890",
              reason: "상품 하자",
              status: "처리중",
            },
          ]);
        } else {
          setChanges(response.data); // 백엔드 데이터 사용
        }
      } catch (error) {
        console.error("교환 요청 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터로 초기화
        setChanges([
          {
            id: "교환1",
            productName: "무안 양파(10KG)",
            productCode: "AMS1257",
            productImage: "https://via.placeholder.com/50",
            requestDate: "2024-11-01 01:22:50",
            customerEmail: "sample1@example.com",
            orderNumber: "ORD12345",
            reason: "교환 요청",
            status: "처리중",
          },
          {
            id: "교환2",
            productName: "제주 감귤(5KG)",
            productCode: "AMS5678",
            productImage: "https://via.placeholder.com/50",
            requestDate: "2024-11-02 14:35:12",
            customerEmail: "sample2@example.com",
            orderNumber: "ORD67890",
            reason: "상품 하자",
            status: "처리중",
          },
        ]);
      }
    };

    fetchChanges();
  }, []);

  const handleAction = async (changeId, action) => {
    try {
      // 교환 상태 변경 API 호출
      const response = await axios.post(`http://localhost:8082/api/changes/${changeId}/${action}`);
      if (response.status === 200) {
        alert(`교환 요청 ${action === "approve" ? "승인" : "거절"} 처리 완료`);
        // 데이터 상태 업데이트
        setChanges((prevChanges) =>
          prevChanges.map((chg) =>
            chg.id === changeId ? { ...chg, status: action === "approve" ? "승인됨" : "거절됨" } : chg
          )
        );
      }
    } catch (error) {
      console.error("교환 상태 변경 오류:", error);
      alert("상태 변경 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="change-manager-container">
      <h2 className="change-manager-title">교환 관리</h2>
      <table className="change-manager-table">
        <thead>
          <tr>
            <th>상품정보</th>
            <th>요청일자</th>
            <th>주문고객</th>
            <th>주문번호</th>
            <th>교환 사유</th>
            <th>교환 상태</th>
          </tr>
        </thead>
        <tbody>
          {changes.map((chg) => (
            <tr key={chg.id}>
              <td>
                <img src={chg.productImage} alt={chg.productName} className="product-image" />
                <div>{chg.productName}</div>
                <div>({chg.productCode})</div>
              </td>
              <td>{chg.requestDate}</td>
              <td>{chg.customerEmail}</td>
              <td>{chg.orderNumber}</td>
              <td>{chg.reason}</td>
              <td>
                <div className="button-group">
                  <button
                    className="approve-change"
                    onClick={() => handleAction(chg.id, "approve")}
                  >
                    승인
                  </button>
                  <button
                    className="reject-change"
                    onClick={() => handleAction(chg.id, "reject")}
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

export default ChangeManager;
