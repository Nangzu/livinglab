import React from 'react';
import './main.css';

const Main = () => {
  const products = [
    { id: 1, name: '특수버섯', price: '23,900원', discount: '10%', img: 'product1.png' },
    { id: 2, name: '황태 껍질', price: '5,000원', discount: '28%', img: 'product2.png' },
    { id: 3, name: '우드 칼 거치대', price: '4,800원', discount: '9%', img: 'product3.png' },
    { id: 4, name: '1인 식사 회 세트', price: '38,000원', discount: '16%', img: 'product4.png' },
  ];

  return (
    <main className="main-content">
      <div className="banner">
        <img src="banner.png" alt="할인 가득한 가을" />
      </div>
      <section className="products">
        <h2>추천 상품</h2>
        <div className="product-list">
          {products.map((product) => (
            <div key={product.id} className="product-card">
              <img src={product.img} alt={product.name} />
              <p>{product.name}</p>
              <p>
                <span className="discount">{product.discount}</span> {product.price}
              </p>
            </div>
          ))}
        </div>
        <button className="more-products">더 많은 상품 보러 가기</button>
      </section>
    </main>
  );
};

export default Main;
