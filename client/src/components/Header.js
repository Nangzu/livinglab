import React, { useState, useEffect } from 'react';
import './header.css';
import SearchBar from './SearchBar';
import logo from "../images/logo.png";
import { Link, useNavigate } from 'react-router-dom';
import like from "../images/like.png";
import basket from "../images/basket.png";
import categories from "../images/categories.png";
import Collaboration from "./Collaboration";

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  // 컴포넌트 마운트 시와 세션스토리지 변경 시 로그인 상태 확인
  useEffect(() => {
    const checkLoginStatus = () => {
      const user = sessionStorage.getItem('user');
      setIsLoggedIn(!!user);
    };

    checkLoginStatus();
    // 세션스토리지 변경 이벤트 리스너
    window.addEventListener('storage', checkLoginStatus);

    return () => {
      window.removeEventListener('storage', checkLoginStatus);
    };
  }, []);

  const handleLogout = () => {
    // 세션스토리지에서 사용자 정보 삭제
    sessionStorage.removeItem('user');
    setIsLoggedIn(false);
    alert('로그아웃 되었습니다.');
    navigate('/main');  // 메인 페이지로 이동
  };

  return (
    <header className="header">
      <div className="blue-bar"></div>
      <div className="top-bar">
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
        <div className="search-bar-container">
          <SearchBar />
        </div>
        <div className="header-icons-container">
          <Collaboration />
          {isLoggedIn ? (
            // 로그인 상태일 때
            <>
              <Link to="/mypage" className="icon">마이페이지</Link>
              <span className="icon" onClick={handleLogout} style={{ cursor: 'pointer' }}>
                로그아웃
              </span>
            </>
          ) : (
            // 로그아웃 상태일 때
            <>
              <Link to="/signup" className="icon">회원가입</Link>
              <Link to="/login" className="icon">로그인</Link>
            </>
          )}
        </div>
      </div>
      
      <div className="nav-bar">
        <div className="category">
          <div className="category-icons">
            <Link to="/mypage" className="icon">
              <img src={like} alt="favorite" className="like-icon" />
            </Link>
            <Link to="/cart" className="icon">
              <img src={basket} alt="cart" className="basket-icon" />
            </Link>
          </div>
          <ul>
            <li>
              <Link to="/categories" className="icon">
                <img src={categories} alt="category" style={{ height: "20px", marginRight: "5px" }} />
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