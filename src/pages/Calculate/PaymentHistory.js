import React, { useState, useEffect } from "react";
import "./PaymentHistory.css";
import axios from "axios";

const PaymentHistory = () => {
  const [paymentData, setPaymentData] = useState([]);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [filter, setFilter] = useState("");
  const [role, setRole] = useState("");

  useEffect(() => {
    const sampleData = [
      {
        status: "-",
        paymentRequestDate: "2024-11-15",
        paymentCompletionDate: "2024-11-16 15:00:00",
        role: "직원",
        roleName: "김수현",
        employeeID: "asd1",
        employeeName: "김수현",
        contactNumber: "123456789",
        paymentAmount: 123450,
        paymentStatus: "완료",
      },
      {
        status: "-",
        paymentRequestDate: "2024-11-15",
        paymentCompletionDate: "2024-11-16 15:00:00",
        role: "직원",
        roleName: "박준수",
        employeeID: "asd2",
        employeeName: "박준수",
        contactNumber: "987654321",
        paymentAmount: 234560,
        paymentStatus: "완료",
      },
    ];

    const fetchPaymentData = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/payment-history");
        if (response.data && response.data.length > 0) {
          setPaymentData(response.data);
        } else {
          setPaymentData(sampleData);
        }
      } catch (error) {
        console.error("지급 내역 데이터 가져오기 오류:", error);
        setPaymentData(sampleData);
      }
    };

    // 초기 상태로 샘플 데이터 표시
    setPaymentData(sampleData);

    fetchPaymentData();
  }, []);

  const handleSearch = () => {
    const filteredData = paymentData.filter(
      (item) =>
        (!startDate || new Date(item.paymentRequestDate) >= new Date(startDate)) &&
        (!endDate || new Date(item.paymentRequestDate) <= new Date(endDate)) &&
        (filter ? item.paymentStatus.includes(filter) : true) &&
        (role ? item.role.includes(role) : true)
    );
    setPaymentData(filteredData);
  };

  return (
    <div className="payment-history-container">
      <h2 className="payment-title">지급내역</h2>

      <div className="payment-filter">
        <label>
          조회기간
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
        </label>
        <label>
          상태
          <select value={filter} onChange={(e) => setFilter(e.target.value)}>
            <option value="">전체</option>
            <option value="완료">완료</option>
            <option value="대기">대기</option>
          </select>
        </label>
        <label>
          직책
          <select value={role} onChange={(e) => setRole(e.target.value)}>
            <option value="">전체</option>
            <option value="직원">직원</option>
            <option value="관리자">관리자</option>
          </select>
        </label>
        <button onClick={handleSearch}>조회</button>
      </div>

      <table className="payment-table">
        <thead>
          <tr>
            <th>상태</th>
            <th>지급 예정 일시</th>
            <th>지급 완료 일시</th>
            <th>직책</th>
            <th>직원 이름</th>
            <th>직원 ID</th>
            <th>연락처</th>
            <th>지급 금액</th>
            <th>상태</th>
          </tr>
        </thead>
        <tbody>
          {paymentData.length > 0 ? (
            paymentData.map((item, index) => (
              <tr key={index}>
                <td>{item.status}</td>
                <td>{item.paymentRequestDate}</td>
                <td>{item.paymentCompletionDate}</td>
                <td>{item.role}</td>
                <td>{item.roleName}</td>
                <td>{item.employeeID}</td>
                <td>{item.contactNumber}</td>
                <td>{item.paymentAmount.toLocaleString()}원</td>
                <td>{item.paymentStatus}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="9">지급 내역이 없습니다.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default PaymentHistory;
