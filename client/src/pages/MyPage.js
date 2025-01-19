import React, { useState, useEffect } from 'react';
import './mypage.css';
import profile from '../images/profile.png';
import axios from 'axios';

const MyPage = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showSuccess, setShowSuccess] = useState(false);
  const [userName, setUserName] = useState('');
  const [orders, setOrders] = useState([]);
  const [orderCount, setOrderCount] = useState({
    pending: 0,
    processing: 0,
    shipping: 0,
    completed: 0
  });

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        // sessionStorage에서 사용자 정보 가져오기
        const userString = sessionStorage.getItem('user');
        if (userString) {
          const userInfo = JSON.parse(userString);
          setUserName(userInfo.username);  // UserDTO의 username 필드 사용

          // 사용자 주문 정보 가져오기
          const response = await axios.get(`http://localhost:8082/api/orders/user/${userInfo.usernum}`);
          setOrders(response.data);

          // 배송 상태별 주문 수 계산
          const counts = response.data.reduce(
            (acc, order) => {
              switch (order.deliveryStatus) {
                case 'PENDING':
                  acc.pending += 1;
                  break;
                case 'PROCESSING':
                  acc.processing += 1;
                  break;
                case 'SHIPPING':
                  acc.shipping += 1;
                  break;
                case 'COMPLETED':
                  acc.completed += 1;
                  break;
                default:
                  break;
              }
              return acc;
            },
            { pending: 0, processing: 0, shipping: 0, completed: 0 }
          );
          setOrderCount(counts);
        } else {
          // 로그인 정보가 없는 경우, 로그인 페이지로 리디렉션
          window.location.href = '/login';
        }
      } catch (error) {
        console.error('Failed to fetch user data:', error);
      }
    };

    fetchUserData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      const userInfo = JSON.parse(sessionStorage.getItem('user'));
      
      // 비밀번호 변경 요청
      await axios.put(`http://localhost:8082/api/users/change-password/${userInfo.usernum}`, {
        currentPassword,
        newPassword
      });
      
      setShowSuccess(true);

      setTimeout(() => {
        setShowSuccess(false);
        setIsModalOpen(false);
        setCurrentPassword('');
        setNewPassword('');
        setConfirmPassword('');
      }, 3000);
    } catch (error) {
      console.error('Failed to change password:', error);
      alert('비밀번호 변경에 실패했습니다. 다시 확인해주세요.');
    }
  };

  return (
    <div className="profile-container">
      <h1>마이쇼핑</h1>
      
      <div className="profile-info">
        <div className="profile-section">
          <div className="profile-image">
            <img src={profile} alt="프로필" />
          </div>
          <div 
            className="password-change-button"
            onClick={() => setIsModalOpen(true)}
          >
            비밀번호 수정
          </div>
        </div>
        <p className="greeting">안녕하세요. {userName} 님</p>
        <p className="status-text">나의 주문처리 현황</p>
      </div>

      <div className="order-status">
        <div className="status-box">
          <span className="number">{orderCount.pending}</span>
          <span className="label">입금전</span>
        </div>
        <div className="status-box">
          <span className="number">{orderCount.processing}</span>
          <span className="label">배송준비중</span>
        </div>
        <div className="status-box">
          <span className="number">{orderCount.shipping}</span>
          <span className="label">배송중</span>
        </div>
        <div className="status-box">
          <span className="number">{orderCount.completed}</span>
          <span className="label">배송완료</span>
        </div>
      </div>

      <div className="activity-status">
        <div className="activity-box">
          <span>취소 : </span>
          <span className="count">0</span>
        </div>
        <div className="activity-box">
          <span>교환 : </span>
          <span className="count">0</span>
        </div>
        <div className="activity-box">
          <span>반품 : </span>
          <span className="count">0</span>
        </div>
      </div>

      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button 
              className="modal-close"
              onClick={() => setIsModalOpen(false)}
            >
              ×
            </button>
            {!showSuccess ? (
              <>
                <h2>비밀번호 변경</h2>
                <form onSubmit={handleSubmit} className="password-form">
                  <div className="form-group">
                    <label>현재 비밀번호</label>
                    <input
                      type="password"
                      placeholder="현재 비밀번호를 입력하세요"
                      value={currentPassword}
                      onChange={(e) => setCurrentPassword(e.target.value)}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>새 비밀번호</label>
                    <input
                      type="password"
                      placeholder="새로운 비밀번호를 입력하세요"
                      value={newPassword}
                      onChange={(e) => setNewPassword(e.target.value)}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>비밀번호 확인</label>
                    <input
                      type="password"
                      placeholder="새로운 비밀번호를 다시 입력하세요"
                      value={confirmPassword}
                      onChange={(e) => setConfirmPassword(e.target.value)}
                      required
                    />
                  </div>
                  <button type="submit" className="submit-button">
                    변경하기
                  </button>
                </form>
              </>
            ) : (
              <div className="success-message">
                비밀번호가 성공적으로 변경되었습니다.
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default MyPage;