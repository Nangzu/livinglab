import React from 'react';
import { useNavigate } from 'react-router-dom';
import './card.css';

const Card = ({ goodsnum, image, name, description, originalPrice, salePrice, discount }) => {
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/goods/${goodsnum}`); // 레시피 번호를 URL에 전달
  };

  return (
    <div className="card" onClick={handleCardClick} style={{ cursor: 'pointer' }}>
      <img src={image} alt={name} className="card-image" />
      <div className="card-content">
        <h3 className="card-name">{name}</h3>
        <p className="card-description">{description}</p>
        <div className="card-price">
          <span className="card-original-price">{originalPrice}원</span>
          <span className="card-sale-price">{salePrice}원</span>
          <span className="card-discount">{discount}%</span>
        </div>
      </div>
    </div>
  );
};

export default Card;
