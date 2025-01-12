import React, { useState } from "react";
import "./Registration.css";

const Registration = ({ isSidebarOpen }) => {
  const [formData, setFormData] = useState({
    category: "",
    productName: "",
    price: "",
    images: [],
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files);
    setFormData({ ...formData, images: files });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("상품 정보:", formData);
    alert("상품이 등록되었습니다!");
  };

  // 동적 스타일 설정
  const containerStyle = {
    position: "absolute",
    top: "60px",
    left: isSidebarOpen ? "300px" : "0", // 사이드바가 열렸을 때와 닫혔을 때의 위치
    width: isSidebarOpen ? "calc(100vw - 300px)" : "100vw", // 사이드바 너비를 뺀 화면 너비
    height: "100vh",
    backgroundColor: "#f9f9f9",
    padding: "20px",
    boxSizing: "border-box",
    overflowY: "auto", // 스크롤 가능
  };

  return (
    <div style={containerStyle}>
      <h2>상품 등록</h2>
      <form onSubmit={handleSubmit} className="registration-form">
        <table>
          <tbody>
            {/* 카테고리 */}
            <tr>
              <td><label>카테고리명</label></td>
              <td>
                <select
                  name="category"
                  value={formData.category}
                  onChange={handleChange}
                >
                  <option value="">카테고리 분류</option>
                  <option value="채소">채소</option>
                  <option value="과일">과일</option>
                  <option value="잡화">잡화</option>
                </select>
              </td>
            </tr>

            {/* 제품명 */}
            <tr>
              <td><label>제품명</label></td>
              <td>
                <input
                  type="text"
                  name="productName"
                  value={formData.productName}
                  onChange={handleChange}
                  placeholder="제품명을 입력하세요."
                />
              </td>
            </tr>

            {/* 제품 가격 */}
            <tr>
              <td><label>제품 가격</label></td>
              <td>
                <input
                  type="number"
                  name="price"
                  value={formData.price}
                  onChange={handleChange}
                  placeholder="제품 가격을 입력하세요."
                />
              </td>
            </tr>

            {/* 제품 이미지 */}
            <tr>
              <td><label>제품 이미지</label></td>
              <td>
                <input
                  type="file"
                  multiple
                  onChange={handleImageUpload}
                  accept="image/*"
                />
                <div className="image-preview">
                  {formData.images.map((image, index) => (
                    <img
                      key={index}
                      src={URL.createObjectURL(image)}
                      alt={`preview-${index}`}
                    />
                  ))}
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        {/* 등록 버튼 */}
        <div className="submit-container">
          <button type="submit" className="submit-button">등록</button>
        </div>
      </form>
    </div>
  );
};

export default Registration;
