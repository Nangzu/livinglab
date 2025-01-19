import React, { useEffect, useState } from "react";
import "./EmployeeForm.css";
import axios from "axios";

const EmployeeForm = () => {
  const [employees, setEmployees] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  useEffect(() => {
    // 백엔드에서 직원 정보 가져오기
    axios
      .get("http://localhost:8082/api/employees") // 백엔드의 API 경로에 맞게 수정
      .then((response) => {
        setEmployees(response.data);
      })
      .catch((error) => {
        console.error("직원 정보를 가져오는 중 오류 발생:", error);
      });
  }, []);

  const handleDelete = (id) => {
    // 직원 삭제 요청
    axios
      .delete(`http://localhost:8082/api/employees/${id}`) // 백엔드의 API 경로에 맞게 수정
      .then(() => {
        setEmployees(employees.filter((employee) => employee.id !== id));
        alert("직원이 삭제되었습니다.");
      })
      .catch((error) => {
        console.error("직원 삭제 중 오류 발생:", error);
      });
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // 현재 페이지 데이터 계산
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentEmployees = employees.slice(indexOfFirstItem, indexOfLastItem);

  const totalPages = Math.ceil(employees.length / itemsPerPage);

  return (
    <div className="employee-form-container">
      <h2 className="employee-form-title">직원 정보 관리</h2>
      <table className="employee-form-table">
        <thead>
          <tr>
            <th>회원 번호</th>
            <th>학생명</th>
            <th>Email</th>
            <th>전화번호</th>
            <th>직책</th>
            <th>승인 날짜</th>
            <th>삭제</th>
          </tr>
        </thead>
        <tbody>
          {currentEmployees.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.id}</td>
              <td>{employee.name}</td>
              <td>{employee.email}</td>
              <td>{employee.phone}</td>
              <td>{employee.role}</td>
              <td>{employee.approvalDate}</td>
              <td>
                <button
                  className="delete-button"
                  onClick={() => handleDelete(employee.id)}
                >
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        {[...Array(totalPages).keys()].map((number) => (
          <button
            key={number + 1}
            onClick={() => handlePageChange(number + 1)}
            disabled={currentPage === number + 1}
          >
            {number + 1}
          </button>
        ))}
      </div>
    </div>
  );
};

export default EmployeeForm;
