import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Sign.css";
import axios from "axios";

const Sign = () => {
  const [formData, setFormData] = useState({
    userid: "", // 사용자 ID
    pw: "", // 비밀번호
    phone: "", // 전화번호
    email: "", // 이메일
    address: "", // 주소
    username: "", // 사용자 이름
    role: "", 
    marketName: "",
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const data = {
        userid: formData.userid,
        pw: formData.pw,
        phone: formData.phone,
        email: formData.email,
        address: formData.address,
        username: formData.username,
        role: formData.role, // 선택한 역할을 전달
        marketName: formData.marketName,
      };

      const response = await axios.post("http://localhost:8082/api/users/sellerregister", data);

      if (response.status === 200) {
        alert("회원가입이 완료되었습니다!");
        navigate("/login");
      }
    } catch (error) {
      console.error("회원가입 중 오류 발생:", error.response || error);
      setError(error.response?.data?.message || "회원가입에 실패했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <div className="signup-container">
      <h2 className="signup-title">회원가입</h2>
      <form onSubmit={handleSubmit} className="signup-form">
        {/* 사용자 ID */}
        <div className="form-group">
          <label htmlFor="userid">아이디</label>
          <input
            type="text"
            name="userid"
            value={formData.userid}
            onChange={handleChange}
            placeholder="아이디를 입력하세요."
          />
        </div>

        {/* 비밀번호 */}
        <div className="form-group">
          <label htmlFor="pw">비밀번호</label>
          <input
            type="password"
            name="pw"
            value={formData.pw}
            onChange={handleChange}
            placeholder="비밀번호를 입력하세요."
          />
        </div>

        {/* 전화번호 */}
        <div className="form-group">
          <label htmlFor="phone">전화번호</label>
          <input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            placeholder="전화번호를 입력하세요."
          />
        </div>

        {/* 이메일 */}
        <div className="form-group">
          <label htmlFor="email">이메일</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="이메일을 입력하세요."
          />
        </div>

        {/* 주소 */}
        <div className="form-group">
          <label htmlFor="address">주소</label>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
            placeholder="주소를 입력하세요."
          />
        </div>

        {/* 사용자 이름 */}
        <div className="form-group">
          <label htmlFor="username">이름</label>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            placeholder="이름을 입력하세요."
          />
        </div>

        {/* 마켓 이름 */}
        <div className="form-group">
          <label htmlFor="marketName">마켓 이름</label>
          <input
            type="text"
            name="marketName"
            value={formData.marketName}
            onChange={handleChange}
            placeholder="마켓 이름을 입력하세요."
          />
        </div>

        {/* 오류 메시지 */}
        {error && <p className="error-message">{error}</p>}

        {/* 회원가입 버튼 */}
        <button type="submit" className="submit-button">
          회원가입
        </button>
      </form>
    </div>
  );
};

export default Sign;
