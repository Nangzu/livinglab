import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';  // useNavigate 추가
import axios from 'axios';
import "./SearchBar.css";

const SearchBar = ({ setSearchResults = () => {} }) => { // 기본값 추가
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();  // navigate 훅 추가

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (searchQuery) {
        handleSearchChange();
      } else {
        setSearchResults([]); // 빈 값으로 초기화
      }
    }, 500);

    return () => clearTimeout(timeoutId);
  }, [searchQuery]);

  const handleSearchChange = async () => {
    if (searchQuery) {
      try {
        const response = await axios.get(`http://localhost:8082/api/goods/search`, {
          params: {
            goodsname: searchQuery
          },
        });

        setSearchResults(response.data);
        console.log("서치데이터:", response.data);
      } catch (error) {
        console.error('Error searching for recipes:', error);
      }
    }
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    handleSearchChange();
    // 검색 후 SearchPage로 이동
    navigate(`/search?query=${searchQuery}`);
  };

  return (
    <form className="search-container" onSubmit={handleSearchSubmit}>
      <div className="search-wrapper">
        <div className="search-input-wrapper">
          <input
            type="text"
            className="search-input"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="상품, 가게명 검색"
          />
          <button type="submit" className="search-button">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
          </button>
        </div>
      </div>
    </form>
  );
};

export default SearchBar;
