import React from 'react';
import './header.css';
import SearchBar from './SearchBar'; // SearchBar 컴포넌트 임포트
import logo from "../images/logo.png";
import { Link } from 'react-router-dom'; // react-router-dom을 사용하여 페이지 간 이동
import like from "../images/like.png"; // 좋아요 이미지 임포트
import basket from "../images/basket.png"; // 장바구니 이미지 임포트
import categories from "../images/categories.png"; // 카테고리 이미지 임포트

const Header = () => {
  return (
    <header className="header">
      {/* 파란색 일자바 */}
      <div className="blue-bar"></div>

      {/* 로고, 서치바, 회원가입, 로그인*/}
      <div className="top-bar">
        {/* 로고 컨테이너 */}
        <div className="logo-container">
          <a href="/main" className="d-flex align-items-center">
            <img
              src={logo}
              alt="logo"
              className="d-inline-block align-top me-2"
              style={{ height: "70px" }}
            />
          </a>
        </div>

        {/* 서치바 컨테이너 */}
        <div className="search-bar-container">
          <SearchBar />
        </div>

        {/* 회원가입, 로그인 컨테이너 */}
        <div className="header-icons-container">
          <Link to="/signup" className="icon">회원가입</Link>
          <Link to="/login" className="icon">로그인</Link>
        </div>
      </div>

      {/* 카테고리와 다른 메뉴 */}
      <div className="nav-bar">
        <div className="category">
          <div className="category-icons">
            <Link to="/like" className="icon">
              <img src={like} alt="favorite" className="like-icon" /> {/* 좋아요 아이콘 */}
            </Link>
            <Link to="/cart" className="icon">
              <img src={basket} alt="cart" className="basket-icon" /> {/* 장바구니 아이콘 */}
            </Link>
          </div>
          <ul>
            <li>
              <Link to="/categories" className="icon">
                <img src={categories} alt="category" style={{ height: "20px", marginRight: "5px" }} /> {/* 카테고리 아이콘 */}
                카테고리
              </Link>
            </li>
            <li>
              <Link to="/best" className="icon">베스트</Link>
            </li>
            <li>
              <Link to="/processed-food" className="icon">가공식품</Link>
            </li>
            <li>
              <Link to="/agriculture" className="icon">농산물</Link>
            </li>
            <li>
              <Link to="/seafood" className="icon">수산물</Link>
            </li>
            <li>
              <Link to="/shopping" className="icon">쇼핑</Link>
            </li>
          </ul>
        </div>
      </div>
    </header>
  );
};

export default Header;
