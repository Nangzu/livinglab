import React, { useState, useEffect } from "react";
import "./ProductEdit.css";
import axios from "axios";

const ProductEdit = () => {
  const [products, setProducts] = useState([]); // 모든 상품 데이터
  const [editedProducts, setEditedProducts] = useState([]); // 수정된 상품 데이터
  const [loading, setLoading] = useState(true); // 로딩 상태
  const [error, setError] = useState(""); // 오류 메시지

  // 상품 데이터 불러오기
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/goods/all"); // 백엔드에서 모든 상품 가져오기
        setProducts(response.data);
        setEditedProducts(response.data); // 초기 데이터 설정
      } catch (err) {
        console.error("상품 데이터를 불러오는 중 오류 발생:", err);
        setError("상품 데이터를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false); // 로딩 종료
      }
    };

    fetchProducts();
  }, []);

  // 입력값 변경 처리
  const handleInputChange = (index, field, value) => {
    const updatedProducts = [...editedProducts];
    updatedProducts[index][field] = value;
    setEditedProducts(updatedProducts);
  };

  // 상품 수정 저장 처리
  const handleSave = async (index) => {
    const product = editedProducts[index];

    try {
      const response = await axios.put(
        `http://localhost:8082/api/goods/update/${product.id}`,
        {
          goodsDTO: {
            name: product.name,
            price: product.price,
            stock: product.stock,
          },
        }
      );
      alert(`${product.name}이(가) 성공적으로 저장되었습니다.`);
      console.log("저장된 상품 데이터:", response.data);
    } catch (err) {
      console.error("상품 수정 중 오류 발생:", err);
      alert("상품 저장 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  };

  // 로딩 상태 표시
  if (loading) {
    return <div className="loading">상품 데이터를 불러오는 중...</div>;
  }

  // 오류 상태 표시
  if (error) {
    return <div className="error-message">{error}</div>;
  }

  return (
    <div className="product-edit-container">
      <h2>상품 조회/수정</h2>
      <table className="product-edit-table">
        <thead>
          <tr>
            <th>등록상품명/등록상품 ID</th>
            <th>전체 판매량</th>
            <th>노출상태</th>
            <th>판매가</th>
            <th>판매/승인상태</th>
            <th>재고수량</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {editedProducts.map((product, index) => (
            <tr key={product.id}>
              <td>
                <div className="product-info">
                  <img src={product.image} alt={product.name} />
                  <div>
                    <p>{product.name}</p>
                    <p>ID: {product.id}</p>
                  </div>
                </div>
              </td>
              <td>{product.totalSales}개</td>
              <td>{product.visible ? "노출" : "숨김"}</td>
              <td>
                <input
                  type="number"
                  value={product.price}
                  onChange={(e) =>
                    handleInputChange(index, "price", e.target.value)
                  }
                />
              </td>
              <td>{product.status || "승인 대기"}</td>
              <td
                style={{
                  color: product.stock > 10 ? "green" : "red",
                  fontWeight: "bold",
                }}
              >
                {product.stock}
              </td>
              <td>
                <button
                  className="edit-button"
                  onClick={() => handleSave(index)}
                >
                  상품수정
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductEdit;
