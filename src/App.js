import React, { useState , useEffect} from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from "react-router-dom";
import "./App.css";
import Header from "./components/Header";
import Sidebar from "./components/Sidebar";
import Sign from "./pages/User/Sign";
import Login from "./pages/User/Login";
import Registration from "./pages/Product/Registration";
import ProductEdit from "./pages/Product/ProductEdit";
import StudentApproval from "./pages/Employee/StudentApproval";
import EmployeeRole from "./pages/Employee/EmployeeRole";
import EmployeeForm from "./pages/Employee/EmployeeForm";
import DeliveryManager from "./pages/Delivery/DeliveryManager";
import DeliveryStop from "./pages/Delivery/DeliveryStop";
import ReturnManager from "./pages/Delivery/ReturnManager";
import ChangeManager from "./pages/Delivery/ChangeManager";
import OrderCheck from "./pages/Delivery/OrderCheck";
import SettlementHistory from "./pages/Calculate/SettlementHistory";
import SalesHistory from "./pages/Calculate/SalesHistory";
import TaxHistory from "./pages/Calculate/TaxHistory";
import PaymentHistory from "./pages/Calculate/PaymentHistory";
import CustomerQuery from "./pages/Customer/CustomerQuery";
import Review from "./pages/Customer/Review";

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
          <Route path="delivery-manager" element={<DeliveryManager />} />

          {/* 출고 중지 요청 페이지 */}
          <Route path="delivery-stop" element={<DeliveryStop />} />

          {/* 반품 관리 페이지 */}
          <Route path="return-manager" element={<ReturnManager />} />

          {/* 교환 관리 페이지 */}
          <Route path="change-manager" element={<ChangeManager />} />

          {/* 주문 조회 페이지 */}
          <Route path="order-check" element={<OrderCheck />} />

          {/* 정산 내역 페이지 */}
          <Route path="settlement-history" element={<SettlementHistory />} />

          {/* 매출 내역 페이지 */}
          <Route path="sales-history" element={<SalesHistory />} />

          {/* 부가세 신고 내역 페이지 */}
          <Route path="tax-history" element={<TaxHistory />} />

          {/* 지급 내역 페이지 */}
          <Route path="payment-history" element={<PaymentHistory />} />

          {/* 고객 문의 페이지 */}
          <Route path="customer-query" element={<CustomerQuery />} />

          {/* 상품평 페이지 */}
          <Route path="review" element={<Review />} />
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
