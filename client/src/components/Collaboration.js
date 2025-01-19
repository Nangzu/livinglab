import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './collaboration.css';

const Collaboration = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedShop, setSelectedShop] = useState('');
  const [pdfFile, setPdfFile] = useState(null);
  const [markets, setMarkets] = useState([]);
  const [isStudent, setIsStudent] = useState(false);

  // 사용자 정보 확인
  useEffect(() => {
    const userString = sessionStorage.getItem('user');
    if (userString) {
      const user = JSON.parse(userString);
      setIsStudent(user.role === 3);
    }
  }, []);

  // 상점 목록 가져오기
  useEffect(() => {
    const fetchMarkets = async () => {
      try {
        const response = await axios.get('http://localhost:8082/api/cbsend/market');
        console.log('Markets data:', response.data);
        setMarkets(response.data);
      } catch (error) {
        console.error('상점 목록을 불러오는데 실패했습니다:', error);
      }
    };
    fetchMarkets();
  }, []);

  const handleOpen = () => {
    const userString = sessionStorage.getItem('user');
    if (!userString) {
      alert('로그인이 필요한 서비스입니다.');
      window.location.href = '/login';
      return;
    }

    const user = JSON.parse(userString);
    if (user.role !== 3) {
      alert('학생만 이용 가능한 서비스입니다.');
      return;
    }
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
    setSelectedShop('');
    setPdfFile(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userString = sessionStorage.getItem('user');
    if (!userString) {
      alert('로그인이 필요한 서비스입니다.');
      window.location.href = '/login';
      return;
    }

    try {
      const selectedMarket = markets.find((market) => market.id === parseInt(selectedShop));
      if (!selectedMarket) {
        alert('올바른 상점을 선택하세요.');
        return;
      }

      const user = JSON.parse(userString);
      const cbsendData = {
        marketcode: selectedMarket.id,
        oneliner: `협업 신청 for ${selectedMarket.name}`,
      };

      const formData = new FormData();
      formData.append('cbsend', JSON.stringify(cbsendData)); // JSON 형태로 cbsend 추가
      formData.append('file', pdfFile); // PDF 파일 추가
      console.log("협업데이터:", formData);
      const response = await axios.post('http://localhost:8082/api/cbsend/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        withCredentials: true,
      });

      if (response.status === 201) {
        alert('협업 신청이 완료되었습니다.');
        handleClose();
      }
    } catch (error) {
      if (error.response?.status === 401) {
        alert('권한이 없습니다. 학생만 협업 신청이 가능합니다.');
      } else {
        alert('협업 신청 중 오류가 발생했습니다.');
      }
      console.error('협업 신청 실패:', error);
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      setPdfFile(file);
    } else {
      alert('PDF 파일만 업로드 가능합니다.');
      event.target.value = null;
    }
  };

  const handleShopSelect = (event) => {
    const selectedValue = event.target.value;
    setSelectedShop(selectedValue);
    console.log('선택된 상점:', selectedValue);
  };

  return (
    <>
      <button
        className="collaboration-icon"
        onClick={handleOpen}
        disabled={!isStudent}
        title={!isStudent ? '학생만 이용 가능한 서비스입니다.' : ''}
      >
        협업신청
      </button>
      {isOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button className="modal-close" onClick={handleClose}>×</button>
            <h2>협업 신청</h2>
            <form onSubmit={handleSubmit} className="collaboration-form">
              <div className="form-group">
                <label>상점 선택</label>
                <select value={selectedShop} onChange={handleShopSelect} required>
                  <option value="">상점을 선택하세요</option>
                  {Array.isArray(markets) && markets.length > 0 ? (
                    markets.map((market) => (
                      <option key={market.id} value={market.id}>
                        {market.name}
                      </option>
                    ))
                  ) : (
                    <option disabled>상점 데이터를 불러오는 중입니다...</option>
                  )}
                </select>
              </div>
              <div className="form-group">
                <label>PDF 파일 업로드</label>
                <input
                  type="file"
                  accept="application/pdf"
                  onChange={handleFileChange}
                  required
                />
              </div>
              <button type="submit" className="submit-button">
                신청하기
              </button>
            </form>
          </div>
        </div>
      )}
    </>
  );
};

export default Collaboration;
