import React from "react";
import "./App.css";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import Mainpage from "./pages/Main";
import Headerbar from './components/Header';
import SearchPage from './pages/SearchPage';
import SignupPage from './pages/Signup';
import Loginpage from './pages/Login';
import Mypage from './pages/MyPage';
import Footer from './components/Footer';
import GoodsDetail from "./pages/GoodsDetail";
import CartPage from './pages/Cart';
import OrderPage from './pages/Order';
import OrderConfirmation from "./pages/OrderConfirmation";

function App() {
  return (
    <div className="App">
      <Router>
        <Headerbar />
        <Routes>
          <Route path="/" element={<Navigate to="/Main" />} />
          <Route path="Main" element={<Mainpage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/login" element={<Loginpage />} />
          <Route path="/mypage" element={<Mypage />} />
          <Route path="/goods/:goodsnum" element={<GoodsDetail />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/order" element={<OrderPage />} />
          <Route path="/order-confirmation" element={<OrderConfirmation />} />  
        </Routes>
        <Footer />
      </Router>
    </div>
  );
}

export default App;