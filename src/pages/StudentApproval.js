import React, { useState, useEffect } from "react";
import "./StudentApproval.css";
import axios from "axios"; 

const StudentApproval = () => {
  const [students, setStudents] = useState([]); // 학생 데이터 상태

  // 페이지 로드 시 데이터 불러오기
  useEffect(() => {
    const fetchStudents = async () => {
      try {
        // 학생 데이터 가져오기
        const response = await axios.get("http://localhost:8082/api/students");
        setStudents(response.data); // 상태 업데이트
      } catch (error) {
        console.error("학생 데이터를 불러오는 중 오류 발생:", error);
      }
    };

    fetchStudents();
  }, []);

  // 승인 처리
  const handleApprove = (studentId) => {
    alert(`학생 ID ${studentId} 승인 처리`);
    axios.post(`http://localhost:8082/api/students/approve/${studentId}`);
  };

  // 거절 처리
  const handleReject = (studentId) => {
    alert(`학생 ID ${studentId} 거절 처리`);
    axios.post(`http://localhost:8082/api/students/reject/${studentId}`);
  };

  return (
    <div className="student-approval-container">
      <h2>학생 요청 승인</h2>
      <table className="student-approval-table">
        <thead>
          <tr>
            <th>회원 번호</th>
            <th>학생명</th>
            <th>학과</th>
            <th>E-mail</th>
            <th>전화번호</th>
            <th>포트폴리오</th>
            <th>승인 여부</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.id}>
              <td>{student.id}</td>
              <td>{student.name}</td>
              <td>{student.department}</td>
              <td>{student.email}</td>
              <td>{student.phone}</td>
              <td>
                <a href={student.portfolio} target="_blank" rel="noopener noreferrer">
                  {student.portfolio}
                </a>
              </td>
              <td>
                <button
                  className="approve-button"
                  onClick={() => handleApprove(student.id)}
                >
                  승인
                </button>
                <button
                  className="reject-button"
                  onClick={() => handleReject(student.id)}
                >
                  거절
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StudentApproval;
