import React from "react";
import { useNavigate } from "react-router-dom"; // 페이지 이동을 위한 useNavigate 가져오기

const Header = ({ toggleSidebar, user, onLogout }) => {
  const navigate = useNavigate(); // 페이지 이동 함수 초기화

  const styles = {
    header: {
      position: "fixed",
      top: 0,
      left: 0,
      width: "100%",
      height: "60px",
      backgroundColor: "#ffffff",
      borderBottom: "1px solid #ddd",
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between", // 양쪽 정렬
      padding: "0 20px",
      zIndex: 1000,
      boxSizing: "border-box",
    },
    menuAndLogo: {
      display: "flex",
      alignItems: "center",
      flexShrink: 0, // 로고와 햄버거 메뉴 축소 방지
    },
    menuIcon: {
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-between",
      width: "30px",
      height: "25px",
      cursor: "pointer",
      marginRight: "15px", // 로고와 간격 설정
    },
    bar: {
      width: "100%",
      height: "4px",
      backgroundColor: "#333",
      borderRadius: "2px",
    },
    logo: {
      display: "flex",
      alignItems: "center",
    },
    logoImage: {
      width: "60px", // 로고 이미지 크기 조정
      height: "50px",
    },
    nav: {
      display: "flex",
      alignItems: "center",
      justifyContent: "flex-end",
      flexGrow: 1, // nav가 남은 공간을 차지하도록 설정
      flexShrink: 0, // nav 크기가 축소되지 않도록 설정
      overflow: "hidden", // 메뉴가 화면을 초과하지 않도록 설정
    },
    navItem: {
      marginLeft: "20px",
      fontSize: "24px", // 메뉴 글씨 크기 조정
      color: "#333",
      textDecoration: "none",
      cursor: "pointer",
      whiteSpace: "nowrap", // 텍스트 줄바꿈 방지
    },
  };
  // 로그인 버튼 클릭 시 로그인 페이지로 이동
  const handleLoginClick = () => {
    navigate("/login"); // /login 경로로 이동
  };

  // 회원가입 버튼 클릭 시 회원가입 페이지로 이동
  const handleSignupClick = () => {
    navigate("/signup"); // /signup 경로로 이동
  };

  return (
    <header style={styles.header}>
      {/* 왼쪽: 햄버거 메뉴와 로고 */}
      <div style={styles.menuAndLogo}>
        <div style={styles.menuIcon} onClick={toggleSidebar}>
          <div style={styles.bar}></div>
          <div style={styles.bar}></div>
          <div style={styles.bar}></div>
        </div>
        <div 
        style={styles.logo}
        onClick={() => navigate("/main")}
        >
          <img src="logo.png" alt="Logo" style={styles.logoImage} />
        </div>
      </div>
      {/* 오른쪽: 로그인 여부에 따른 메뉴 변경 */}
      <nav style={styles.nav}>
      {user ? (
        <>
          <span style={styles.navItem}>도움말</span>
          <span style={styles.navItem}>안녕하세요, {user.userid}님!</span>
          <span style={styles.navItem} onClick={onLogout}>
            로그아웃
          </span>
        </>
      ) : (
        <>
          <span style={styles.navItem} onClick={handleSignupClick}>
            회원가입
          </span>
          <span style={styles.navItem} onClick={handleLoginClick}>
            로그인
          </span>
          <span style={styles.navItem}>고객센터</span>
        </>
      )}
      </nav>
    </header>
  );
};

export default Header;
