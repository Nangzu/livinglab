import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import SearchBar from '../components/SearchBar';
import './searchPage.css';

const SearchPage = () => {
  const [searchResults, setSearchResults] = useState([]);
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const searchQuery = queryParams.get('query'); // URL에서 'query' 파라미터 추출

  useEffect(() => {
    if (searchQuery) {
      fetchSearchResults();
    }
  }, [searchQuery]);

  const fetchSearchResults = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/goods/search`, {
        params: { query: searchQuery },
      });
      setSearchResults(response.data);
    } catch (error) {
      console.error('Error fetching search results:', error);
      setSearchResults([]);
    }
  };

  return (
    <div className="search-page-container">
      {/* SearchBar는 SearchPage 상단에 위치 */}
      <div className="search-bar-container">
        <SearchBar setSearchResults={setSearchResults} />
      </div>

      {/* 검색 결과 출력 */}
      <div className="search-results-container">
        <h2>
          "{searchQuery}" 검색 결과
        </h2>
        <div className="search-results">
          {searchResults.length > 0 ? (
            searchResults.map((item, index) => (
              <div key={index} className="search-result-item">
                <p>{item.name}</p>
                <p>{item.price}</p>
              </div>
            ))
          ) : (
            <p>검색 결과가 없습니다.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default SearchPage;
