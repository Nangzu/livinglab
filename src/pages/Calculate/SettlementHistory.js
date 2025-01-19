import React, { useState, useEffect } from "react";
import "./SettlementHistory.css";
import axios from "axios";

const SettlementHistory = () => {
  const [settlementData, setSettlementData] = useState([]);
  const [totalAmount, setTotalAmount] = useState(0);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  useEffect(() => {
    // 정산 데이터 가져오기 (샘플 데이터 + 백엔드 연동 준비)
    const fetchSettlementData = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/settlements");
        if (response.data.length === 0) {
          // 샘플 데이터
          const sampleData = [
            {
              id: "P2023111501",
              productOrderId: "ORD-001", // 상품 주문번호
              userId: "user1", // 유저 ID
              tag: "판매", // 태그 구분
              productName: "상품 A", // 상품명
              amount: 55000,
              fee: 2500,
              status: "정산 완료",
              orderDate: "2024-11-15",
              settlementDate: "2024-12-15",
            },
            {
              id: "P2023111502",
              productOrderId: "ORD-002", // 상품 주문번호
              userId: "user2", // 유저 ID
              tag: "환불", // 태그 구분
              productName: "상품 B", // 상품명
              amount: 60000,
              fee: 3000,
              status: "정산 대기",
              orderDate: "2024-11-20",
              settlementDate: "2024-12-20",
            },
            {
              id: "P2023111503",
              productOrderId: "ORD-003", // 상품 주문번호
              userId: "user3", // 유저 ID
              tag: "교환", // 태그 구분
              productName: "상품 C", // 상품명
              amount: 70000,
              fee: 3500,
              status: "정산 완료",
              orderDate: "2024-11-25",
              settlementDate: "2024-12-25",
            },
          ];
          setSettlementData(sampleData);
          setTotalAmount(sampleData.reduce((acc, cur) => acc + cur.amount, 0));
        } else {
          setSettlementData(response.data);
          setTotalAmount(response.data.reduce((acc, cur) => acc + cur.amount, 0));
        }
      } catch (error) {
        console.error("정산 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터로 초기화
        const sampleData = [
          {
            id: "P2023111501",
            productOrderId: "ORD-001", // 상품 주문번호
            userId: "user1", // 유저 ID
            tag: "야채", // 태그 구분
            productName: "[청계]양파", // 상품명
            amount: 55000,
            fee: 2500,
            status: "정산 완료",
            orderDate: "2024-11-15",
            settlementDate: "2024-12-15",
          },
          {
            id: "P2023111502",
            productOrderId: "ORD-002", // 상품 주문번호
            userId: "user2", // 유저 ID
            tag: "과일", // 태그 구분
            productName: "[청계]사과", // 상품명
            amount: 60000,
            fee: 3000,
            status: "정산 대기",
            orderDate: "2024-11-20",
            settlementDate: "2024-12-20",
          },
          {
            id: "P2023111503",
            productOrderId: "ORD-003", // 상품 주문번호
            userId: "user3", // 유저 ID
            tag: "야채", // 태그 구분
            productName: "[청계]파", // 상품명
            amount: 70000,
            fee: 3500,
            status: "정산 완료",
            orderDate: "2024-11-25",
            settlementDate: "2024-12-25",
          },
        ];
        setSettlementData(sampleData);
        setTotalAmount(sampleData.reduce((acc, cur) => acc + cur.amount, 0));
      }
    };

    fetchSettlementData();
  }, []);

  const handleSearch = () => {
    // 정산 데이터 필터링
    const filteredData = settlementData.filter(
      (item) =>
        (!startDate || new Date(item.orderDate) >= new Date(startDate)) &&
        (!endDate || new Date(item.orderDate) <= new Date(endDate))
    );
    setSettlementData(filteredData);
    setTotalAmount(filteredData.reduce((acc, cur) => acc + cur.amount, 0));
  };

  return (
    <div className="settlement-history-container">
      <h2 className="settlement-title">정산 내역</h2>
      <div className="settlement-summary">
        <span>정산 예정일</span>
        <span>2024-11-15 ~ 2024-12-15</span>
        <span>총 정산금액</span>
        <span>{totalAmount.toLocaleString()}원</span>
      </div>
      <div className="settlement-filter">
        <span>조회기간</span>
        <input
          type="date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
        />
        <button onClick={handleSearch}>조회</button>
      </div>
      <table className="settlement-table">
        <thead>
          <tr>
            <th>정산번호</th>
            <th>상품주문번호</th>
            <th>유저ID</th>
            <th>구분</th>
            <th>상품명</th>
            <th>정산금액</th>
            <th>배송비</th>
            <th>정산상태</th>
            <th>주문일자</th>
            <th>정산일자</th>
          </tr>
        </thead>
        <tbody>
          {settlementData.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.productOrderId}</td>
              <td>{item.userId}</td>
              <td>{item.tag}</td>
              <td>{item.productName}</td>
              <td>{item.amount.toLocaleString()}원</td>
              <td>{item.fee.toLocaleString()}원</td>
              <td>{item.status}</td>
              <td>{item.orderDate}</td>
              <td>{item.settlementDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SettlementHistory;
