import React, { useState , useEffect} from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from "react-router-dom";
import "./App.css";
import Header from "./components/Header";
import Sidebar from "./components/Sidebar";
import Sign from "./pages/Sign";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import ProductEdit from "./pages/ProductEdit";
import StudentApproval from "./pages/StudentApproval";
import EmployeeRole from "./pages/EmployeeRole";
import EmployeeForm from "./pages/EmployeeForm";
import DeliveryManager from "./pages/DeliveryManager";

// 메인 콘텐츠를 렌더링할 AppContent 컴포넌트 생성
function AppContent() {
  const location = useLocation(); // 현재 경로 확인
  const [menuOpen, setMenuOpen] = useState(false);
  const [user, setUser] = useState(null); // 사용자 상태

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    if(storedUser) {
      setUser(storedUser); // localStorage에 저장된 사용자 정보 불러오기
    }
  }, []);

  const handleLogin = (userInfo) => {
    setUser(userInfo);
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem("user");
    window.location.href = "/login"; // 로그인 페이지로 리디렉션
  };

  // 사이드바를 숨길 페이지 목록
  const hideSidebarPaths = ["/signup", "/login"];

  return (
    <div className="App">
      {/* 헤더 */}
      <Header
        toggleSidebar={() => setMenuOpen(!menuOpen)}
        user={user}
        onLogin={handleLogin}
        onLogout={handleLogout}
      />

      {/* 사이드바: 특정 경로에서는 숨김 */}
      {!hideSidebarPaths.includes(location.pathname) && <Sidebar isOpen={menuOpen} />}

      {/* 메인 콘텐츠 영역 */}
      <main
        style={{
          marginLeft: hideSidebarPaths.includes(location.pathname)
            ? "0"
            : menuOpen
            ? "300px"
            : "0",
          marginTop: "60px",
          transition: "margin-left 0.3s",
          padding: "20px",
          boxSizing: "border-box",
        }}
      >
        <Routes>
          {/* 기본 경로 리디렉션 */}
          <Route path="/" element={<Navigate to="/login" />} />
          
          {/* 로그인 페이지 */}
          <Route path="/login" element={<Login onLogin={handleLogin} />} />
          
          {/* 회원가입 페이지 */}
          <Route path="/signup" element={<Sign />} />
          
          {/* 메인 페이지 */}
          <Route
            path="/main" element = {
              user ? (
              <h1>안녕하세요, {user.userName}님!</h1>
              ) : (
                <Navigate to ="/login" />
              )
            }
          />

          {/* 상품 등록 페이지 */}
          <Route path="/registration" element={<Registration isSidebarOpen={menuOpen} />} />

          {/* 상품 수정 페이지 */}
          <Route path="/product-edit" element={<ProductEdit isSidebarOpen={menuOpen} />} />

          {/* 학생 요청 승인 페이지 */}
          <Route path="student-approval" element={<StudentApproval />} />

          {/* 직원 권한 관리 페이지 */}
          <Route path="employee-role" element={<EmployeeRole />} />

          {/* 직원 권한 관리 페이지 */}
          <Route path="employee-management" element={<EmployeeForm />} />

          {/* 배송 관리 페이지 */}
          <Route path="delivey-manager" element={<DeliveryManager />} />
        </Routes>
      </main>
    </div>
  );
}

// 최상위 App 컴포넌트
function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

export default App;
