import React, { useState } from 'react';
import axios from 'axios';
import './login.css';

const Login = () => {
  const [formData, setFormData] = useState({
    userid: '',
    pw: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8082/api/users/login', formData);
      console.log('로그인 성공:', response.data);
      // 세션에 사용자 정보 저장 후 페이지 이동
      alert('로그인 성공!');
      window.location.href = '/main';
    } catch (error) {
      console.error('로그인 실패:', error);
      alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2 className="login-title">로그인</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              id="userid"
              name="userid"
              className="form-input"
              placeholder="아이디를 입력해주세요"
              value={formData.userid}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <input
              type="password"
              id="pw"
              name="pw"
              className="form-input"
              placeholder="비밀번호를 입력해주세요"
              value={formData.pw}
              onChange={handleChange}
              required
            />
          </div>

          <div className="links">
            <a href="/find-username">아이디 찾기</a>
            <a href="/find-password">비밀번호 찾기</a>
          </div>

          <button type="submit" className="login-button">
            로그인
          </button>

          {/* 회원가입을 버튼 형식으로 변경 */}
          <button type="button" className="signup-button" onClick={() => window.location.href = '/signup'}>
            계정이 없으신가요? 회원가입
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;