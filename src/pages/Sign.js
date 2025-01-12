import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Sign.css";

const Sign = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    name: "",
    email: "",
    phone: "",
    address: "",
  });

  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // 유효성 검사
  const validate = () => {
    const newErrors = {};

    if (!formData.username) newErrors.username = "아이디를 입력해주세요.";
    if (!formData.password) newErrors.password = "비밀번호를 입력해주세요.";
    if (formData.password !== formData.confirmPassword)
      newErrors.confirmPassword = "비밀번호가 일치하지 않습니다.";
    if (!formData.name) newErrors.name = "가게명을 입력해주세요.";
    if (!formData.email) newErrors.email = "이메일을 입력해주세요.";
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email))
      newErrors.email = "유효한 이메일 형식이 아닙니다.";
    if (!formData.phone) newErrors.phone = "휴대폰 번호를 입력해주세요.";
    if (!/^\d+$/.test(formData.phone))
      newErrors.phone = "휴대폰 번호는 숫자만 입력해주세요.";
    if (!formData.address) newErrors.address = "주소를 입력해주세요.";

    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      setSuccess(false);
    } else {
      setErrors({});
      setSuccess(true);
      // 회원가입 정보 저장 (localStorge 사용)
      localStorage.setItem("user", JSON.stringify(formData));
      alert("회원가입이 완료되었습니다! 로그인 페이지로 이동합니다.");
      navigate("/login");
      console.log("회원가입 데이터:", formData);
      // TODO: 서버로 데이터 전송
    }
  };

  return (
    <div className="signup-container">
      <h2 className="signup-title">회원가입</h2>
      {success && (
        <p className="success-message">회원가입이 성공적으로 완료되었습니다!</p>
      )}
      <form onSubmit={handleSubmit} className="signup-form">
        {/* 아이디 */}
        <div className="form-group">
          <label>아이디 *</label>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
          {errors.username && <p className="error-message">{errors.username}</p>}
        </div>

        {/* 비밀번호 */}
        <div className="form-group">
          <label>비밀번호 *</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          {errors.password && <p className="error-message">{errors.password}</p>}
        </div>

        {/* 비밀번호 확인 */}
        <div className="form-group">
          <label>비밀번호 확인 *</label>
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
          />
          {errors.confirmPassword && <p className="error-message">{errors.confirmPassword}</p>}
        </div>

        {/* 가게명 */}
        <div className="form-group">
          <label>가게명 *</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
          {errors.name && <p className="error-message">{errors.name}</p>}
        </div>

        {/* 이메일 */}
        <div className="form-group">
          <label>이메일 *</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && <p className="error-message">{errors.email}</p>}
        </div>

        {/* 휴대폰 */}
        <div className="form-group">
          <label>휴대폰 *</label>
          <input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
          {errors.phone && <p className="error-message">{errors.phone}</p>}
        </div>

        {/* 주소 */}
        <div className="form-group address-group">
          <label>주소 *</label>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
          <button type="button" className="address-button">주소 검색</button>
          {errors.address && <p className="error-message">{errors.address}</p>}
        </div>

        {/* 제출 버튼 */}
        <button type="submit" className="submit-button">회원가입</button>
      </form>
    </div>
  );
};

export default Sign;
