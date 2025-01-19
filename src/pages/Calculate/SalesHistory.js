import React, { useState, useEffect } from "react";
import "./SalesHistory.css";
import axios from "axios";

const SalesHistory = () => {
  const [salesData, setSalesData] = useState([]);
  const [totalSales, setTotalSales] = useState(0);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  useEffect(() => {
    // 매출 데이터 가져오기 (샘플 데이터 + 백엔드 연동 준비)
    const fetchSalesData = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/sales");
        if (response.data.length === 0) {
          // 샘플 데이터
          const sampleData = [
            {
              id: "S2023111501",
              companyName: "업체 A", // 업체명
              depositDate: "2024-11-20", // 기준 입금일
              salesAmount: 55000, // 상품 판매금액
              deliveryFee: 2500, // 배송비
              settlementStatus: "정산 완료", // 정산 상태
              fee: 2000, // 수수료
              netSettlementAmount: 50500, // 정산 입금액
              registrationDate: "2024-11-15", // 등록일
              approvalDate: "2024-11-16", // 승인일
              finalSettlementStatus: "완료", // 최종 정산 상태
            },
            {
              id: "S2023111502",
              companyName: "업체 B",
              depositDate: "2024-11-25",
              salesAmount: 60000,
              deliveryFee: 3000,
              settlementStatus: "정산 대기",
              fee: 2500,
              netSettlementAmount: 57500,
              registrationDate: "2024-11-20",
              approvalDate: "2024-11-22",
              finalSettlementStatus: "대기",
            },
            {
              id: "S2023111503",
              companyName: "업체 C",
              depositDate: "2024-12-01",
              salesAmount: 70000,
              deliveryFee: 3500,
              settlementStatus: "정산 완료",
              fee: 3000,
              netSettlementAmount: 66500,
              registrationDate: "2024-11-25",
              approvalDate: "2024-11-28",
              finalSettlementStatus: "완료",
            },
          ];
          setSalesData(sampleData);
          setTotalSales(sampleData.reduce((acc, cur) => acc + cur.salesAmount, 0));
        } else {
          setSalesData(response.data);
          setTotalSales(response.data.reduce((acc, cur) => acc + cur.salesAmount, 0));
        }
      } catch (error) {
        console.error("매출 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터로 초기화
        const sampleData = [
          {
            id: "S2023111501",
            companyName: "[청계]단단한 양파",
            depositDate: "2024-11-20",
            salesAmount: 55000,
            deliveryFee: 2500,
            settlementStatus: "정산 완료",
            fee: 2000,
            netSettlementAmount: 50500,
            registrationDate: "2024-11-15",
            approvalDate: "2024-11-16",
            finalSettlementStatus: "완료",
          },
          {
            id: "S2023111502",
            companyName: "[청계]새빨간 사과",
            depositDate: "2024-11-25",
            salesAmount: 60000,
            deliveryFee: 3000,
            settlementStatus: "정산 대기",
            fee: 2500,
            netSettlementAmount: 57500,
            registrationDate: "2024-11-20",
            approvalDate: "2024-11-22",
            finalSettlementStatus: "대기",
          },
          {
            id: "S2023111503",
            companyName: "[청계]푸릇한 파]",
            depositDate: "2024-12-01",
            salesAmount: 70000,
            deliveryFee: 3500,
            settlementStatus: "정산 완료",
            fee: 3000,
            netSettlementAmount: 66500,
            registrationDate: "2024-11-25",
            approvalDate: "2024-11-28",
            finalSettlementStatus: "완료",
          },
        ];
        setSalesData(sampleData);
        setTotalSales(sampleData.reduce((acc, cur) => acc + cur.salesAmount, 0));
      }
    };

    fetchSalesData();
  }, []);

  const handleSearch = () => {
    // 매출 데이터 필터링
    const filteredData = salesData.filter(
      (item) =>
        (!startDate || new Date(item.depositDate) >= new Date(startDate)) &&
        (!endDate || new Date(item.depositDate) <= new Date(endDate))
    );
    setSalesData(filteredData);
    setTotalSales(filteredData.reduce((acc, cur) => acc + cur.salesAmount, 0));
  };

  return (
    <div className="sales-history-container">
      <h2 className="sales-title">매출 내역</h2>
      <div className="sales-summary">
        <span>기간테스크</span>
        <span>2024-11-15 ~ 2024-12-15</span>
        <span>총 매출액</span>
        <span>{totalSales.toLocaleString()}원</span>
      </div>
      <div className="sales-filter">
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
      <table className="sales-table">
        <thead>
          <tr>
            <th>업체명</th>
            <th>기준입금일</th>
            <th>상품판매금액</th>
            <th>배송비</th>
            <th>정산상태</th>
            <th>수수료</th>
            <th>정산입금액</th>
            <th>등록일</th>
            <th>승인일</th>
            <th>최종정산상태</th>
          </tr>
        </thead>
        <tbody>
          {salesData.map((item) => (
            <tr key={item.id}>
              <td>{item.companyName}</td>
              <td>{item.depositDate}</td>
              <td>{item.salesAmount.toLocaleString()}원</td>
              <td>{item.deliveryFee.toLocaleString()}원</td>
              <td>{item.settlementStatus}</td>
              <td>{item.fee.toLocaleString()}원</td>
              <td>{item.netSettlementAmount.toLocaleString()}원</td>
              <td>{item.registrationDate}</td>
              <td>{item.approvalDate}</td>
              <td>{item.finalSettlementStatus}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SalesHistory;
