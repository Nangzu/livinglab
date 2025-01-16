import React, { useState } from "react";
import "./ProductEdit.css";

const ProductEdit = ({ products }) => {
  const [editedProducts, setEditedProducts] = useState(products);

  const handleInputChange = (index, field, value) => {
    const updatedProducts = [...editedProducts];
    updatedProducts[index][field] = value;
    setEditedProducts(updatedProducts);
  };

  const handleSave = (index) => {
    const product = editedProducts[index];
    console.log("저장된 상품 정보:", product);
    alert(`${product.name}이(가) 저장되었습니다.`);
  };

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
                    <p>{product.id}</p>
                  </div>
                </div>
              </td>
              <td>{product.totalSales}개</td>
              <td>-</td>
              <td>
                <input
                  type="text"
                  value={product.price}
                  onChange={(e) =>
                    handleInputChange(index, "price", e.target.value)
                  }
                />
              </td>
              <td>{product.status}</td>
              <td style={{ color: "red" }}>{product.stock}</td>
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
