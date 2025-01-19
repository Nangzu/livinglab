import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import Card from '../components/Card';
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
      const response = await axios.get(`http://localhost:8082/api/goods/search`, {
        params: { goodsname: searchQuery },
      });
      setSearchResults(response.data);
      console.log("서치데이터:", response.data);
    } catch (error) {
      console.error('Error fetching search results:', error);
      setSearchResults([]);
    }
  };

  return (
    <div className="search-page-container">
      {/* 검색 결과 출력 */}
      <div className="search-results-container">
        <h2>"{searchQuery}" 검색 결과</h2>
        <div className="search-results">
          {searchResults.length > 0 ? (
            searchResults.map((item) => (
              <Card
                key={item.goodsnum}
                goodsnum={item.goodsnum}
                image={`data:image/png;base64,${item.firstFileData}`}
                name={item.firstGoodsname}
                description={item.marketname}
                originalPrice={item.price}
                salePrice={item.price} // 할인된 가격은 없으므로 동일하게 설정
                discount={0} // 할인율이 없다면 0으로 설정
              />
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
