import React, { useState, useEffect } from "react";
import "./Registration.css";
import axios from "axios";

const Registration = ({ isSidebarOpen }) => {
  const [formData, setFormData] = useState({
    goodsname: "", // 상품명
    price: "", // 가격
    tag: "", // 태그
    details: "", // 상세 설명
    goodsOption: "", // 상품 옵션
    userNum: "", // 사용자 번호
    marketCode: "", // 마켓 코드
    packagingtype: "",
    salesunit: "",
    weightcapacity: "",
    expirydate: "",
    notes: "",
    images: [],
    detailImages: [],
  });

  useEffect(() => {
    // 로컬 스토리지에서 사용자 번호와 마켓 코드 가져오기
    const userNum = sessionStorage.getItem("userNum");
    const marketCode = sessionStorage.getItem("marketCode");

    if (userNum && marketCode) {
      setFormData((prevState) => ({
        ...prevState,
        userNum,
        marketCode,
      }));
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleImageUpload = (e, type) => {
    const files = Array.from(e.target.files);
    if (type === "images") {
      setFormData({ ...formData, images: files });
    } else if (type === "detailImages") {
      setFormData({ ...formData, detailImages: files });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // FormData 객체 생성
    const formDataToSend = new FormData();


    formDataToSend.append(
      "goodsDTO",
      JSON.stringify({
        goodsname: formData.goodsname,
        price: formData.price,
        tag: formData.tag,
        details: formData.details,
        goodsOption: formData.goodsOption,
        userNum: formData.userNum
      })
    );

    formDataToSend.append(
      "goodsdetailDTO",
      JSON.stringify({
        packagingtype: formData.packagingtype,
        salesunit: formData.salesunit,
        weightcapacity: formData.weightcapacity,
        expirydate: formData.expirydate,
        notes: formData.notes
      })
    );


    // 일반 이미지 추가
    formData.images.forEach((image) => {
      formDataToSend.append("file", image); // "file"은 백엔드의 키 이름
    });

    // 상세 이미지 추가
    formData.detailImages.forEach((image) => {
      formDataToSend.append("detailFile", image); // "detailFile"은 상세 이미지 키 이름
    });

    try {
      const response = await axios.post("http://localhost:8082/api/goods/add", formDataToSend, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        withCredentials: true, // 세션/쿠키 사용 시 필요
      });

      if(response.status === 200) {
        alert("상품이 성공적으로 등록되었습니다!");
      }
      console.log("등록된 상품 데이터:", response.data);
    } catch (error) {
      console.error("상품 등록 중 오류 발생:", error.response || error);
      alert("상품 등록 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  };

  // 동적 스타일 설정
  const containerStyle = {
    position: "absolute",
    top: "60px",
    left: isSidebarOpen ? "200px" : "0", // 사이드바가 열렸을 때와 닫혔을 때의 위치
    width: isSidebarOpen ? "calc(100vw - 200px)" : "100vw", // 사이드바 너비를 뺀 화면 너비
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
            {/* 상품명 */}
            <tr>
              <td><label>상품명</label></td>
              <td>
                <input
                  type="text"
                  name="goodsname"
                  value={formData.goodsname}
                  onChange={handleChange}
                  placeholder="상품명을 입력하세요."
                />
              </td>
            </tr>

            {/* 가격 */}
            <tr>
              <td><label>가격</label></td>
              <td>
                <input
                  type="number"
                  name="price"
                  value={formData.price}
                  onChange={handleChange}
                  placeholder="가격을 입력하세요."
                />
              </td>
            </tr>

            {/* 태그 */}
            <tr>
              <td><label>태그</label></td>
              <td>
                <input
                  type="text"
                  name="tag"
                  value={formData.tag}
                  onChange={handleChange}
                  placeholder="태그를 입력하세요."
                />
              </td>
            </tr>

            {/* 상세 설명 */}
            <tr>
              <td><label>상세 설명</label></td>
              <td>
                <textarea
                  name="details"
                  value={formData.details}
                  onChange={handleChange}
                  placeholder="상세 설명을 입력하세요."
                />
              </td>
            </tr>

            {/* 상품 옵션 */}
            <tr>
              <td><label>상품 옵션</label></td>
              <td>
                <input
                  type="text"
                  name="goodsOption"
                  value={formData.goodsOption}
                  onChange={handleChange}
                  placeholder="상품 옵션을 입력하세요."
                />
              </td>
            </tr>

            {/* 포장 타입 */}
            <tr>
              <td><label>포장 타입</label></td>
              <td>
                <input
                  type="text"
                  name="packagingtype"
                  value={formData.packagingtype}
                  onChange={handleChange}
                  placeholder="포장 타입을 입력하세요."
                />
              </td>
            </tr>

            {/* 판매 단위 */}
            <tr>
              <td><label>판매 단위</label></td>
              <td>
                <input
                  type="text"
                  name="salesunit"
                  value={formData.salesunit}
                  onChange={handleChange}
                  placeholder="판매 단위를 입력하세요."
                />
              </td>
            </tr>

            {/* 중량 또는 용량 */}
            <tr>
              <td><label>중량(용량)</label></td>
              <td>
                <input
                  type="text"
                  name="weightcapacity"
                  value={formData.weightcapacity}
                  onChange={handleChange}
                  placeholder="중량을 입력하세요."
                />
              </td>
            </tr>

            {/* 소비 기한 */}
            <tr>
              <td><label>소비 기한</label></td>
              <td>
                <input
                  type="text"
                  name="expirydate"
                  value={formData.expirydate}
                  onChange={handleChange}
                  placeholder="소비 기한을 입력하세요."
                />
              </td>
            </tr>

            {/* 안내 사항 */}
            <tr>
              <td><label>안내 사항</label></td>
              <td>
                <textarea
                  type="text"
                  name="notes"
                  value={formData.notes}
                  onChange={handleChange}
                  placeholder="안내 사항을 입력하세요."
                />
              </td>
            </tr>

            {/* 상품 이미지 */}
            <tr>
              <td><label>상품 이미지</label></td>
              <td>
                <input
                  type="file"
                  multiple
                  onChange={(e) => handleImageUpload(e, "images")}
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

            {/* 상세 이미지 */}
            <tr>
              <td><label>상세 이미지</label></td>
              <td>
                <input
                  type="file"
                  multiple
                  onChange={(e) => handleImageUpload(e, "detailImages")}
                  accept="image/*"
                />
                <div className="image-preview">
                  {formData.detailImages.map((image, index) => (
                    <img
                      key={index}
                      src={URL.createObjectURL(image)}
                      alt={`detail-preview-${index}`}
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
