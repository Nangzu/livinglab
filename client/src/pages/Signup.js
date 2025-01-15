import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import './signup.css';

const Signup = () => {
  const [formData, setFormData] = useState({
    id: '',
    password: '',
    confirmPassword: '',
    name: '',
    email: '',
    phone: '',
    address: '',
    gender: '',
    birthDate: '',
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmpw) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    const data = {
      id: formData.id,
      password: formData.pw,
      name: formData.name,
      email: formData.email,
      phone: formData.phone,
      address: formData.address,
      gender: formData.gender,
      birthDate: formData.birthDate,
    };

    try {
      const response = await axios.post(`http://localhost:8080/api/signup`, data);

      if (response.status === 200) {
        alert('회원가입 성공');
        setTimeout(() => navigate("/login"), 2000);
      } else {
        alert('회원가입 실패');
      }
    } catch (error) {
      console.error('회원가입 중 오류 발생:', error);
      alert('서버와의 연결에 문제가 발생했습니다.');
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-header">
        <h2>회원가입</h2>
      </div>

      {/* 일자바 추가 */}
      <div className="divider-bar"></div>

      <form className="signup-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="id">아이디<span className="required">*</span></label>
          <input
            type="text"
            id="id"
            name="id"
            placeholder="아이디를 입력해주세요"
            value={formData.userid}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">비밀번호<span className="required">*</span></label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호를 입력해주세요"
            value={formData.pw}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">비밀번호 확인<span className="required">*</span></label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            placeholder="비밀번호를 확인해주세요"
            value={formData.confirmpw}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="name">이름<span className="required">*</span></label>
          <input
            type="text"
            id="name"
            name="name"
            placeholder="이름을 입력해주세요"
            value={formData.username}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">이메일<span className="required">*</span></label>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="예: example@mail.com"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="phone">휴대폰<span className="required">*</span></label>
          <input
            type="text"
            id="phone"
            name="phone"
            placeholder="예: 010-1234-1234"
            value={formData.phone}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="address">주소<span className="required">*</span></label>
          <input
            type="text"
            id="address"
            name="address"
            placeholder="주소"
            value={formData.address}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>성별<span className="required">*</span></label>
          <div className="gender-options">
            <input
              type="radio"
              id="male"
              name="gender"
              value="남자"
              onChange={handleChange}
              required
            />
            <label htmlFor="male">남자</label>
            <input
              type="radio"
              id="female"
              name="gender"
              value="여자"
              onChange={handleChange}
              required
            />
            <label htmlFor="female">여자</label>
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="birthDate">생년월일<span className="required">*</span></label>
          <input
            type="date"
            id="birthDate"
            name="birthDate"
            value={formData.birthDate}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" className="submit-button">회원가입</button>
      </form>

      {/* 필수 입력사항 위치 */}
      <div className="required-notice">필수입력사항</div>
    </div>
  );
};

export default Signup;
