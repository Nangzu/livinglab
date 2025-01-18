import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // 스타일 파일 import
import axios from "axios";

const Login = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    userid: "", // 사용자 ID
    pw: "", // 비밀번호
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  // 입력값 처리
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // 로그인 처리
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // 마스터 계정 확인 추가!!! 나중에 삭제제
      if (formData.userid === "master" && formData.pw === "master") {
        alert("마스터 계정으로 로그인 성공!");
        const masterData = {
          user_num: 0, // 마스터 계정에 대한 고유 식별자 (예: 0)
          role: "admin", // 마스터 계정의 역할
        };
        sessionStorage.setItem("usernum", masterData.user_num);
        sessionStorage.setItem("role", masterData.role);
        onLogin(masterData);
        navigate("/main"); // 메인 페이지로 이동
        return;
      } //나중에 삭제제

      const credentials = {
        userid: formData.userid, 
        pw: formData.pw, 
      };

      // 서버로 로그인 요청
      const response = await axios.post("http://localhost:8082/api/users/login", credentials, {withCredentials: true});
      const { user_num, role } = response.data;

      // // 로컬 스토리지에 사용자 정보 저장
      // sessionStorage.setItem("userNum", user_num);
      // sessionStorage.setItem("role", role);

      alert("로그인 성공!");
      onLogin({ user_num, role }); // 로그인 콜백 호출
      sessionStorage.setItem("userid", formData.userid);
      sessionStorage.setItem("usernum", formData.user_num);
      navigate("/main"); // 메인 페이지로 이동
    } catch (error) {
      console.error("로그인 오류:", error.response || error);
      setError("로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
    }
  };

  // 회원가입 페이지로 이동
  const handleSignup = () => {
    navigate("/signup");
  };

  return (
    <div className="login-container">
      <h2 className="login-title">로그인</h2>
      <form onSubmit={handleSubmit} className="login-form">
        {/* 사용자 ID 입력 */}
        <div className="form-group">
          <input
            type="text"
            name="userid"
            value={formData.userid}
            onChange={handleChange}
            placeholder="아이디를 입력해주세요"
          />
        </div>

        {/* 비밀번호 입력 */}
        <div className="form-group">
          <input
            type="password"
            name="pw"
            value={formData.pw}
            onChange={handleChange}
            placeholder="비밀번호를 입력해주세요"
          />
        </div>

        {/* 오류 메시지 표시 */}
        {error && <p className="error-message">{error}</p>}

        {/* 아이디/비밀번호 찾기 */}
        <div className="find-links">
          <span>아이디 찾기</span>
          <span>비밀번호 찾기</span>
        </div>

        {/* 로그인 버튼 */}
        <button type="submit" className="login-button">
          로그인
        </button>

        {/* 회원가입 버튼 */}
        <button type="button" className="signup-button" onClick={handleSignup}>
          회원가입
        </button>
      </form>
    </div>
  );
};

export default Login;
