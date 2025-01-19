import React, { useState, useEffect } from "react";
import "./CustomerQuery.css";
import axios from "axios";

const CustomerQuery = () => {
  const [queries, setQueries] = useState([]);
  const [stats, setStats] = useState({
    within24Hours: 0,
    within72Hours: 0,
    after72Hours: 0,
  });

  useEffect(() => {
    const fetchQueries = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/customer-queries"); // 백엔드 URL
        const data = response.data;

        // 미답변 현황 계산
        const currentTime = new Date();
        const updatedStats = {
          within24Hours: 0,
          within72Hours: 0,
          after72Hours: 0,
        };

        data.forEach((query) => {
          const queryTime = new Date(query.registrationTime);
          const hoursDiff = (currentTime - queryTime) / (1000 * 60 * 60);

          if (hoursDiff <= 24) updatedStats.within24Hours++;
          else if (hoursDiff <= 72) updatedStats.within72Hours++;
          else updatedStats.after72Hours++;
        });

        setQueries(data);
        setStats(updatedStats);
      } catch (error) {
        console.error("고객 문의 데이터를 가져오는 데 실패했습니다:", error);

        // 샘플 데이터
        const sampleData = [
          {
            id: 1,
            registrationTime: "2024-11-15 14:20:32",
            customerName: "박준희",
            queryContent: "상품 언제 도착하나요?",
            queryType: "주문문의",
            orderNumber: "130008754123",
            responseStatus: "답변완료",
          },
          {
            id: 2,
            registrationTime: "2024-11-15 20:15:19",
            customerName: "김석윤",
            queryContent: "어디서 재확인할 수 있나요?",
            queryType: "주문문의",
            orderNumber: "130008754123",
            responseStatus: "답변완료",
          },
          {
            id: 3,
            registrationTime: "2024-11-16 12:20:00",
            customerName: "장우영",
            queryContent: "오프라인 매장은 어디죠?",
            queryType: "상품문의",
            orderNumber: "130008754123",
            responseStatus: "답변하기",
          },
          {
            id: 4,
            registrationTime: "2024-11-17 15:10:22",
            customerName: "김강현",
            queryContent: "상품 언제 도착하나요?",
            queryType: "상품문의",
            orderNumber: "130008754123",
            responseStatus: "답변하기",
          },
        ];

        setQueries(sampleData);

        // 샘플 데이터를 기반으로 미답변 현황 계산
        const sampleStats = {
          within24Hours: 1,
          within72Hours: 1,
          after72Hours: 2,
        };
        setStats(sampleStats);
      }
    };

    fetchQueries();
  }, []);

  return (
    <div className="customer-query-container">
      <h2 className="customer-query-title">고객 문의</h2>

      <div className="query-stats">
        <div className="stat-box">
          <div className="stat-number">{stats.within24Hours}건</div>
          <div className="stat-label">24시간 이내</div>
        </div>
        <div className="stat-box">
          <div className="stat-number">{stats.within72Hours}건</div>
          <div className="stat-label">24~72시간 이내</div>
        </div>
        <div className="stat-box">
          <div className="stat-number">{stats.after72Hours}건</div>
          <div className="stat-label">72시간 이후</div>
        </div>
      </div>

      <table className="query-table">
        <thead>
          <tr>
            <th>등록일시</th>
            <th>고객이름</th>
            <th>문의내용</th>
            <th>문의유형</th>
            <th>주문번호</th>
            <th>답변여부</th>
          </tr>
        </thead>
        <tbody>
          {queries.map((query) => (
            <tr key={query.id}>
              <td>{query.registrationTime}</td>
              <td>{query.customerName}</td>
              <td>{query.queryContent}</td>
              <td>{query.queryType}</td>
              <td>{query.orderNumber}</td>
              <td>
                <button
                  className={`response-button ${
                    query.responseStatus === "답변완료" ? "completed" : "pending"
                  }`}
                >
                  {query.responseStatus}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CustomerQuery;
