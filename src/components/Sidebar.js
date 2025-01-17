import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";

const Sidebar = ({ isOpen }) => {
  const [expanded, setExpanded] = useState({});
  const location = useLocation();

  // 특정 카테고리 확장/축소 토글 함수
  const toggleExpand = (category) => {
    setExpanded((prev) => ({
      ...prev,
      [category]: !prev[category],
    }));
  };

  const styles = {
    sidebar: {
      position: "fixed",
      top: "60px", // 헤더 아래 배치
      left: isOpen ? "0" : "-200px", // 열고 닫기 동작
      width: "200px",
      height: "100%",
      backgroundColor: "#ffffff",
      borderRight: "1px solid #ddd",
      transition: "left 0.3s ease-in-out",
      zIndex: 999,
      overflowY: "auto", // 스크롤 가능
    },
    category: {
      padding: "10px 20px",
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      cursor: "pointer",
      fontSize: "20px",
      color: "#333",
      borderBottom: "1px solid #ddd",
    },
    subMenu: {
      paddingLeft: "40px",
      display: "flex",
      flexDirection: "column",
    },
    subMenuItem: {
      padding: "5px 0",
      fontSize: "14px",
      color: "#555",
      cursor: "pointer",
      textDecoration: "none",
      borderRadius: "4px",
    },
    activeMenuItem: {
      color: "#007bff",
      fontWeight: "bold",
    },
    icon: {
      marginRight: "10px",
      width: "30px",
      height: "25px",
    },
    arrow: {
      transform: "rotate(0deg)",
      transition: "transform 0.3s",
    },
    arrowExpanded: {
      transform: "rotate(90deg)",
    },
  };

  return (
    <div style={styles.sidebar}>
      {/* 카테고리 */}
      <div>
        {/* 상품관리 */}
        <div style={styles.category} onClick={() => toggleExpand("상품관리")}>
          <div>
            <img src="product.png" alt="상품관리" style={styles.icon} />
            상품관리
          </div>
          <span
            style={{
              ...styles.arrow,
              ...(expanded["상품관리"] ? styles.arrowExpanded : {}),
            }}
          >
            ▶
          </span>
        </div>
        {expanded["상품관리"] && (
          <div style={styles.subMenu}>
            <Link
              to="registration"
              style={{
                ...styles.subMenuItem,
                ...(location.pathname === "/registration"
                  ? styles.activeMenuItem
                  : {}),
              }}
            >
              상품등록
            </Link>
            <Link
              to="/product-edit"
              style={{
                ...styles.subMenuItem,
                ...(location.pathname === "/product-edit"
                  ? styles.activeMenuItem
                  : {}),
              }}
            >
              상품수정
            </Link>
          </div>
        )}

        {/* 직원관리 */}
        <div style={styles.category} onClick={() => toggleExpand("직원관리")}>
          <div>
            <img src="employee.png" alt="직원관리" style={styles.icon} />
            직원관리
          </div>
          <span
            style={{
              ...styles.arrow,
              ...(expanded["직원관리"] ? styles.arrowExpanded : {}),
            }}
          >
            ▶
          </span>
        </div>
        {expanded["직원관리"] && (
          <div style={styles.subMenu}>
            <Link
              to="/student-approval"
              style={{
                ...styles.subMenuItem,
                ...(location.pathname === "/student-approval"
                  ? styles.activeMenuItem
                  : {}),
              }}
            >
              학생 요청 승인
            </Link>
            <div style={styles.subMenuItem}>직원 권한 관리</div>
            <div style={styles.subMenuItem}>직원 정보 관리</div>
          </div>
        )}

        {/* 주문/배송 */}
        <div style={styles.category} onClick={() => toggleExpand("주문/배송")}>
          <div>
            <img src="delivery.png" alt="주문/배송" style={styles.icon} />
            주문/배송
          </div>
          <span
            style={{
              ...styles.arrow,
              ...(expanded["주문/배송"] ? styles.arrowExpanded : {}),
            }}
          >
            ▶
          </span>
        </div>
        {expanded["주문/배송"] && (
          <div style={styles.subMenu}>
            <div style={styles.subMenuItem}>배송 관리</div>
            <div style={styles.subMenuItem}>출고중지 요청</div>
            <div style={styles.subMenuItem}>반품 관리</div>
            <div style={styles.subMenuItem}>교환 관리</div>
            <div style={styles.subMenuItem}>주문 조회</div>
          </div>
        )}

        {/* 정산 */}
        <div style={styles.category} onClick={() => toggleExpand("정산")}>
          <div>
            <img src="adjustment.png" alt="정산" style={styles.icon} />
            정산
          </div>
          <span
            style={{
              ...styles.arrow,
              ...(expanded["정산"] ? styles.arrowExpanded : {}),
            }}
          >
            ▶
          </span>
        </div>
        {expanded["정산"] && (
          <div style={styles.subMenu}>
            <div style={styles.subMenuItem}>정산 내역</div>
            <div style={styles.subMenuItem}>매출 내역</div>
            <div style={styles.subMenuItem}>부가세 신고내역</div>
            <div style={styles.subMenuItem}>지급 내역</div>
          </div>
        )}

        {/* 고객관리 */}
        <div style={styles.category} onClick={() => toggleExpand("고객관리")}>
          <div>
            <img src="customer.png" alt="고객관리" style={styles.icon} />
            고객관리
          </div>
          <span
            style={{
              ...styles.arrow,
              ...(expanded["고객관리"] ? styles.arrowExpanded : {}),
            }}
          >
            ▶
          </span>
        </div>
        {expanded["고객관리"] && (
          <div style={styles.subMenu}>
            <div style={styles.subMenuItem}>고객문의</div>
            <div style={styles.subMenuItem}>상품평</div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Sidebar;
