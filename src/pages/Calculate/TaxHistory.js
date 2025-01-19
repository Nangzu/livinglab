import React, { useState, useEffect } from "react";
import "./TaxHistory.css";
import axios from "axios";

const TaxHistory = () => {
  const [taxData, setTaxData] = useState([]);
  const [totalSales, setTotalSales] = useState(0);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  useEffect(() => {
    // 부가세 신고 자료 가져오기 (백엔드 연동 준비)
    const fetchTaxData = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/tax-history");
        if (response.data.length === 0) {
          // 샘플 데이터
          const sampleData = [
            {
              date: "2024-11-15",
              totalSalesAmount: 60000,
              taxableAmount: 55000,
              taxAmount: 5000,
              netAmount: 55000,
              dailySettlementDownload: "0원",
              itemSettlementDownload: "0원",
              additionalInfo: "-",
            },
            {
              date: "2024-11-16",
              totalSalesAmount: 70000,
              taxableAmount: 63000,
              taxAmount: 7000,
              netAmount: 63000,
              dailySettlementDownload: "0원",
              itemSettlementDownload: "0원",
              additionalInfo: "-",
            },
            {
              date: "2024-11-17",
              totalSalesAmount: 80000,
              taxableAmount: 72000,
              taxAmount: 8000,
              netAmount: 72000,
              dailySettlementDownload: "0원",
              itemSettlementDownload: "0원",
              additionalInfo: "-",
            },
          ];
          setTaxData(sampleData);
          setTotalSales(sampleData.reduce((acc, cur) => acc + cur.totalSalesAmount, 0));
        } else {
          setTaxData(response.data);
          setTotalSales(response.data.reduce((acc, cur) => acc + cur.totalSalesAmount, 0));
        }
      } catch (error) {
        console.error("부가세 데이터 가져오기 오류:", error);
        // 오류 시 샘플 데이터로 초기화
        const sampleData = [
          {
            date: "2024-11-15",
            totalSalesAmount: 60000,
            taxableAmount: 55000,
            taxAmount: 5000,
            netAmount: 55000,
            dailySettlementDownload: "0원",
            itemSettlementDownload: "0원",
            additionalInfo: "-",
          },
          {
            date: "2024-11-16",
            totalSalesAmount: 70000,
            taxableAmount: 63000,
            taxAmount: 7000,
            netAmount: 63000,
            dailySettlementDownload: "0원",
            itemSettlementDownload: "0원",
            additionalInfo: "-",
          },
          {
            date: "2024-11-17",
            totalSalesAmount: 80000,
            taxableAmount: 72000,
            taxAmount: 8000,
            netAmount: 72000,
            dailySettlementDownload: "0원",
            itemSettlementDownload: "0원",
            additionalInfo: "-",
          },
        ];
        setTaxData(sampleData);
        setTotalSales(sampleData.reduce((acc, cur) => acc + cur.totalSalesAmount, 0));
      }
    };

    fetchTaxData();
  }, []);

  const handleSearch = () => {
    // 날짜 필터링
    const filteredData = taxData.filter(
      (item) =>
        (!startDate || new Date(item.date) >= new Date(startDate)) &&
        (!endDate || new Date(item.date) <= new Date(endDate))
    );
    setTaxData(filteredData);
    setTotalSales(filteredData.reduce((acc, cur) => acc + cur.totalSalesAmount, 0));
  };

  return (
    <div className="tax-history-container">
      <h2 className="tax-title">부가세 신고 내역</h2>
      <div className="tax-summary">
        <span>매출기간</span>
        <span>2024-11-15 ~ 2024-12-15</span>
        <span>총 매출액</span>
        <span>{totalSales.toLocaleString()}원</span>
      </div>
      <div className="tax-filter">
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
      <table className="tax-table">
        <thead>
          <tr>
            <th>일자</th>
            <th>전체 매출액</th>
            <th>과세 매출액</th>
            <th>부가세액</th>
            <th>정산금액</th>
            <th>추가정보</th>
          </tr>
        </thead>
        <tbody>
          {taxData.map((item, index) => (
            <tr key={index}>
              <td>{item.date}</td>
              <td>{item.totalSalesAmount.toLocaleString()}원</td>
              <td>{item.taxableAmount.toLocaleString()}원</td>
              <td>{item.taxAmount.toLocaleString()}원</td>
              <td>{item.netAmount.toLocaleString()}원</td>
              <td>{item.additionalInfo}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TaxHistory;
