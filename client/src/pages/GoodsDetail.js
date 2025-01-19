import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "./goodsDetail.css";

const GoodsDetail = () => {
  const [goods, setGoods] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const { goodsnum } = useParams();
  useEffect(() => {
    const fetchGoods = async () => {
      try {
        const response = await axios.get(`http://localhost:8082/api/goods/${goodsnum}`);
        setGoods(response.data);
        console.log("디테일 데이터:", response.data);
      } catch (error) {
        console.error("상품 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    fetchGoods();
  }, [goodsnum]);

  if (!goods) {
    return <div>로딩 중...</div>;
  }

  const totalPrice = goods.price * quantity;

  const handleQuantityChange = (type) => {
    if (type === "increment") {
      setQuantity(quantity + 1);
    } else if (type === "decrement" && quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  const handleAddToCart = async () => {
    try {
      // 서버 세션에서 가져온 사용자 정보
      const sessionUser = JSON.parse(sessionStorage.getItem("user"));
      if (!sessionUser || !sessionUser.usernum) {
        alert("사용자 정보가 없습니다. 로그인을 확인해주세요.");
        return;
      }
  
      const cartData = {
        goodsnum: goods.goodsnum, // 상품 번호
        quantity: quantity      // 선택한 수량
      };
      console.log("카트데이터:", cartData);
      const response = await axios.post("http://localhost:8082/api/cart/add", cartData, {
        withCredentials: true,
      });
  
      if (response.status === 201) {
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
    <div className="goods-detail-container">
      <div className="goods-detail-content">
        <div className="goods-image">
          <img src={`data:${goods.filetype};base64,${goods.filedata1}`} alt={goods.goodsname} />
        </div>
        <div className="goods-info">
          <h1 className="goods-title">{goods.goodsname}</h1>
          <p className="goods-description">{goods.details}</p>
          <div className="goods-price">
            <span className="current-price">{goods.price.toLocaleString()}원</span>
          </div>
          <table className="goods-details-table">
            <tbody>
              <tr>
                <td>판매자</td>
                <td>{goods.marketname}</td>
              </tr>
              <tr>
                <td>포장타입</td>
                <td>{goods.packagingtype}</td>
              </tr>
              <tr>
                <td>판매단위</td>
                <td>{goods.salesunit}</td>
              </tr>
              <tr>
                <td>중량/용량</td>
                <td>{goods.weightcapacity}</td>
              </tr>
              <tr>
                <td>소비기한</td>
                <td>{goods.expirydate}</td>
              </tr>
              <tr>
                <td>안내사항</td>
                <td>{goods.notes}</td>
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
      <div className="goods-detail-images">
        <h2>상세 이미지</h2>
        <img src={`data:${goods.filetype2};base64,${goods.filedata2}`} alt="상세 이미지" />
      </div>
    </div>
  );
};

export default GoodsDetail;
