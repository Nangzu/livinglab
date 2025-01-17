import React, { useState, useEffect } from "react";
import axios from "axios";
import "./EmployeeRole.css";

const EmployeeRole = () => {
  const [employees, setEmployees] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  // 직원 목록 가져오기
  const fetchEmployees = async (page) => {
    try {
      const response = await axios.get(
        `http://localhost:8082/api/employees?page=${page}`
      );
      setEmployees(response.data.employees);
      setCurrentPage(response.data.currentPage);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("직원 목록을 가져오는 중 오류 발생:", error);
    }
  };

  // 권한 업데이트
  const updateRole = async (employeeId, role, value) => {
    try {
      await axios.put(
        `http://localhost:8082/api/employees/${employeeId}/roles`,
        {
          [role]: value,
        }
      );
      alert("권한이 성공적으로 업데이트되었습니다.");
      fetchEmployees(currentPage);
    } catch (error) {
      console.error("권한 업데이트 중 오류 발생:", error);
      alert("권한 업데이트 중 오류가 발생했습니다.");
    }
  };

  // 체크박스 변경 처리
  const handleCheckboxChange = (employeeId, role, checked) => {
    updateRole(employeeId, role, checked);
  };

  // 페이지 변경
  const handlePageChange = (page) => {
    if (page >= 1 && page <= totalPages) {
      fetchEmployees(page);
    }
  };

  useEffect(() => {
    fetchEmployees(currentPage);
  }, [currentPage]);

  return (
    <div className="employee-role-container">
      <h2 className="employee-role-title">직원 권한 관리</h2>
      <table className="employee-role-table">
        <thead>
          <tr>
            <th>회원 번호</th>
            <th>학생명</th>
            <th>상품 관리</th>
            <th>주문/배송</th>
            <th>고객 관리</th>
            <th>마이샵</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.id}</td>
              <td>{employee.name}</td>
              <td>
                <input
                  type="checkbox"
                  className="role-checkbox"
                  checked={employee.productManagement}
                  onChange={(e) =>
                    handleCheckboxChange(
                      employee.id,
                      "productManagement",
                      e.target.checked
                    )
                  }
                />
              </td>
              <td>
                <input
                  type="checkbox"
                  className="role-checkbox"
                  checked={employee.orderDelivery}
                  onChange={(e) =>
                    handleCheckboxChange(
                      employee.id,
                      "orderDelivery",
                      e.target.checked
                    )
                  }
                />
              </td>
              <td>
                <input
                  type="checkbox"
                  className="role-checkbox"
                  checked={employee.customerManagement}
                  onChange={(e) =>
                    handleCheckboxChange(
                      employee.id,
                      "customerManagement",
                      e.target.checked
                    )
                  }
                />
              </td>
              <td>
                <input
                  type="checkbox"
                  className="role-checkbox"
                  checked={employee.myShop}
                  onChange={(e) =>
                    handleCheckboxChange(employee.id, "myShop", e.target.checked)
                  }
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* 페이지 네비게이션 */}
      <div className="pagination">
        <button
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 1}
        >
          이전
        </button>
        <span>
          {currentPage} / {totalPages}
        </span>
        <button
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default EmployeeRole;
