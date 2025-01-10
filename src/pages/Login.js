import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // 스타일 파일 import

const Login = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // localStorage에서 저장된 사용자 정보 가져오기
    const storedUser = JSON.parse(localStorage.getItem("user"));
    console.log("로그인 정보:", formData);
    // 여기에 로그인 검증 로직 추가 가능
  
  
  if (
    storedUser &&
    storedUser.username === formData.username &&
    storedUser.password === formData.password
  ) {
    alert("로그인 성공!");
    onLogin(storedUser); // 사용자 정보 전달
    navigate("/main");
  } else {
    setError("아이디 또는 비밀번호가 일치하지 않습니다.");
  }
};

  const handleSignup = () => {
    navigate("/signup");
  };

  return (
    <div className="login-container">
      <h2 className="login-title">로그인</h2>
      <form onSubmit={handleSubmit} className="login-form">
        {/* 아이디 입력 */}
        <div className="form-group">
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            placeholder="아이디를 입력해주세요"
          />
        </div>

        {/* 비밀번호 입력 */}
        <div className="form-group">
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="비밀번호를 입력해주세요"
          />
        </div>

        {/*오류 메시지 표시 */}
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
        <button
          type="button"
          className="signup-button"
          onClick={handleSignup}
        >
          회원가입
        </button>
      </form>
    </div>
  );
};

export default Login;
