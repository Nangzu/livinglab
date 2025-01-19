// Login.js
import React, { useState } from 'react';
import './login.css';
import axios from 'axios';

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

  const credentials = {
    userid: formData.userid, 
    pw: formData.pw, 
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8082/api/users/login", credentials, {withCredentials: true});
      
      // 로그인 성공 시 사용자 정보를 세션스토리지에 저장
      // response.data에는 UserDTO 정보가 들어있음 (user_num, role, username 등)
      sessionStorage.setItem('user', JSON.stringify(response.data));
      
      console.log('로그인 성공:', response.data);
      alert('로그인 성공!');
      window.location.href = '/mypage';
    } catch (error) {
      console.error('로그인 실패:', error);
      alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-content">
        <div className="login-left">
          <div className="welcome-text">
            <h1>Welcome Back!</h1>
            <p>서비스를 이용하시려면 로그인해주세요.</p>
          </div>
        </div>
        <div className="login-right">
          <div className="login-box">
            <h2 className="login-title">로그인</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="userid">아이디</label>
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
                <label htmlFor="pw">비밀번호</label>
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
                <span className="divider">|</span>
                <a href="/find-password">비밀번호 찾기</a>
              </div>
              <button type="submit" className="login-button">
                로그인
              </button>
              <div className="signup-section">
                <p>아직 계정이 없으신가요?</p>
                <button
                  type="button"
                  className="signup-button"
                  onClick={() => window.location.href = '/signup'}
                >
                  회원가입
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;