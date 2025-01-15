import React, { useState, useEffect } from "react";
import axios from "axios";
import "./recipeDetail.css";

const RecipeDetail = ({ recipeId }) => {
  const [recipe, setRecipe] = useState(null);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    // Axios 요청으로 데이터 가져오기
    const fetchRecipe = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/recipe/${recipeId}`);
        setRecipe(response.data);
      } catch (error) {
        console.error("레시피 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    fetchRecipe();
  }, [recipeId]);

  if (!recipe) {
    return <div>로딩 중...</div>;
  }

  const totalPrice = recipe.price * quantity;

  const handleQuantityChange = (type) => {
    if (type === "increment") {
      setQuantity(quantity + 1);
    } else if (type === "decrement" && quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  const handleAddToCart = async () => {
    try {
      const cartData = {
        userId: "session_user_id", // 서버 세션 사용자 ID
        recipeId: recipeId,
        quantity: quantity,
      };

      const response = await axios.post("http://localhost:8080/api/cart", cartData);

      if (response.status === 200) {
        alert("장바구니에 상품이 추가되었습니다.");
      } else {
        alert("장바구니 추가에 실패했습니다.");
      }
    } catch (error) {
      console.error("장바구니 추가 중 오류 발생:", error);
      alert("서버와의 연결에 문제가 발생했습니다.");
    }
  };

  return (
    <div className="recipe-detail-container">
      <div className="recipe-detail-content">
        <div className="recipe-image">
          <img src={recipe.image} alt={recipe.title} />
        </div>
        <div className="recipe-info">
          <h1 className="recipe-title">{recipe.title}</h1>
          <p className="recipe-description">{recipe.description}</p>
          <div className="recipe-price">
            <span className="discount">{recipe.discount}%</span>
            <span className="current-price">{totalPrice.toLocaleString()}원</span>
            <span className="original-price">{recipe.originalPrice.toLocaleString()}원</span>
          </div>
          <table className="recipe-details-table">
            <tbody>
              <tr>
                <td>판매자</td>
                <td>{recipe.seller}</td>
              </tr>
              <tr>
                <td>포장타입</td>
                <td>{recipe.packaging}</td>
              </tr>
              <tr>
                <td>판매단위</td>
                <td>{recipe.unit}</td>
              </tr>
              <tr>
                <td>중량/용량</td>
                <td>{recipe.weight}</td>
              </tr>
              <tr>
                <td>소비기한</td>
                <td>{recipe.expiry}</td>
              </tr>
              <tr>
                <td>안내사항</td>
                <td>{recipe.notice}</td>
              </tr>
            </tbody>
          </table>
          <div className="quantity-selector">
            <label>상품선택</label>
            <div className="quantity-controls">
              <button onClick={() => handleQuantityChange("decrement")}>-</button>
              <span>{quantity}</span>
              <button onClick={() => handleQuantityChange("increment")}>+</button>
            </div>
          </div>
          <div className="total-price">총 상품금액: {totalPrice.toLocaleString()}원</div>
          <button className="add-to-cart-button" onClick={handleAddToCart}>장바구니 담기</button>
        </div>
      </div>
      <div className="recipe-detail-images">
        <h2>상세 이미지</h2>
        <img src={recipe.detailImage} alt="상세 이미지" />
      </div>
    </div>
  );
};

export default RecipeDetail;
