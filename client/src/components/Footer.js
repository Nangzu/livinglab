import React from 'react';
import './footer.css';

const Footer = () => {
  return (
    <footer className="footer footer-fixed"> {/* 고정 클래스를 추가 */}
      <div className="footer-container">
        <div className="footer-left">
          <h3>고객센터</h3>
          {/* 카카오톡 문의 버튼 */}
          <a
            href="https://open.kakao.com/o/g9L5zD6"
            target="_blank"
            rel="noopener noreferrer"
            className="contact-button"
          >
            카카오톡 문의
          </a>
          <p>월-토요일 | 오전 8시~오후 6시</p>
          <p>365일 고객센터 운영시간에 순차적으로 답변드리겠습니다.</p>
          <p>비즈니스 문의: xyz@mokopo.ac.kr</p>
        </div>

        <div className="footer-right">
          <h3>결제정보</h3>
          <p>무통장 계좌이체</p>
          <p>신한은행: 435-3451-2341-4352</p>
          <p>㈜MNU</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
