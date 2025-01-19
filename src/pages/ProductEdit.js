import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ProductEdit.css';

function ProductEdit() {
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [popupVisible, setPopupVisible] = useState(false);
  const [localData, setLocalData] = useState({});

  useEffect(() => {
    // 모든 상품 목록 불러오기
    axios.get('http://localhost:8082/api/goods/all')
      .then(response => {
        setProducts(response.data);

        // 로컬 데이터 초기화
        const initialLocalData = {};
        response.data.forEach(product => {
          initialLocalData[product.goodsnum] = {
            sales: product.sales || 0,
            visibility: product.visibility || '노출',
            status: product.status || '승인대기',
            stock: product.stock || '품절',
          };
        });
        setLocalData(initialLocalData);
      })
      .catch(error => {
        console.error(error);
        alert('상품 목록을 불러오는 데 실패했습니다.');
      });
  }, []);

  const handleLocalChange = (goodsnum, field, value) => {
    setLocalData(prev => ({
      ...prev,
      [goodsnum]: {
        ...prev[goodsnum],
        [field]: value,
      },
    }));
  };

  const handleEditClick = (product) => {
    setSelectedProduct(product);
    setPopupVisible(true);
  };

  const handleSave = () => {
    if (!selectedProduct.price || selectedProduct.price <= 0) {
      alert('판매가는 양수로 입력해야 합니다.');
      return;
    }

    const formData = new FormData();
    formData.append('goodsDTO', JSON.stringify({
      goodsname: selectedProduct.goodsname,
      price: selectedProduct.price,
      tag: selectedProduct.tag,
      details: selectedProduct.details,
      goodsOption: selectedProduct.goodsOption,
      marketCode: selectedProduct.marketCode,
      usernum: selectedProduct.usernum,
    }));

    formData.append('goodsdetailDTO', JSON.stringify({
      packagingtype: selectedProduct.packagingtype,
      salesunit: selectedProduct.salesunit,
      weightcapacity: selectedProduct.weightcapacity,
      expirydate: selectedProduct.expirydate,
      notes: selectedProduct.notes,
    }));

    axios.put(`http://localhost:8082/api/goods/update/${selectedProduct.goodsnum}`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
      .then(response => {
        alert('상품이 수정되었습니다.');
        setProducts(prevProducts =>
          prevProducts.map(product =>
            product.goodsnum === selectedProduct.goodsnum ? response.data : product
          )
        );
        setPopupVisible(false);
        setSelectedProduct(null);
      })
      .catch(error => {
        console.error(error);
        alert('상품 수정에 실패했습니다.');
      });
  };

  const handleDelete = (goodsnum) => {
    if (window.confirm('정말로 이 상품을 삭제하시겠습니까?')) {
      axios.delete(`http://localhost:8082/api/goods/delete/${goodsnum}`)
        .then(() => {
          alert('상품이 삭제되었습니다.');
          setProducts(prevProducts => prevProducts.filter(product => product.goodsnum !== goodsnum));
        })
        .catch(error => {
          console.error(error);
          alert('상품 삭제에 실패했습니다.');
        });
    }
  };

  const handleChange = (field, value) => {
    setSelectedProduct(prev => ({
      ...prev,
      [field]: field === 'price' ? parseInt(value, 10) || '' : value,
    }));
  };

  return (
    <div className="product-edit">
      <h1>상품 조회/수정</h1>
      <table>
        <thead>
          <tr>
            <th>상품명/ID</th>
            <th>전체 판매량</th>
            <th>판매가</th>
            <th>노출 상태</th>
            <th>판매/승인 상태</th>
            <th>재고수량</th>
            <th>작업</th>
          </tr>
        </thead>
        <tbody>
          {products.map(product => (
            <tr key={product.goodsnum}>
              <td>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  {product.firstFileData && (
                    <img
                      src={`data:image/png;base64,${product.firstFileData}`}
                      alt={`상품 이미지 ${product.goodsnum}`}
                      style={{ width: '50px', height: '50px', marginRight: '10px', objectFit: 'cover', borderRadius: '5px' }}
                    />
                  )}
                  <div>
                    {product.firstGoodsname} <br />
                    (ID: {product.goodsnum})
                  </div>
                </div>
              </td>
              <td>{localData[product.goodsnum]?.sales || '0'}개</td>
              <td>{product.price || '0'}원</td>
              <td>{localData[product.goodsnum]?.visibility || '노출'}</td>
              <td>{localData[product.goodsnum]?.status || '승인대기'}</td>
              <td>{localData[product.goodsnum]?.stock || '품절'}</td>
              <td>
                <button onClick={() => handleEditClick(product)}>수정</button>
                <button 
                className="delete-button"
                onClick={() => handleDelete(product.goodsnum)} style={{ marginLeft: '5px' }}>삭제</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {popupVisible && selectedProduct && (
        <div className="popup">
          <div className="popup-content">
            <h2>상품 수정</h2>
            <label>
              전체 판매량:
              <input
                type="number"
                value={localData[selectedProduct.goodsnum]?.sales || ''}
                onChange={(e) => handleLocalChange(selectedProduct.goodsnum, 'sales', e.target.value)}
              />
            </label>
            <label>
              노출 상태:
              <select
                value={localData[selectedProduct.goodsnum]?.visibility || ''}
                onChange={(e) => handleLocalChange(selectedProduct.goodsnum, 'visibility', e.target.value)}
              >
                <option value="노출">노출</option>
                <option value="숨김">숨김</option>
              </select>
            </label>
            <label>
              판매가:
              <input
                type="number"
                value={selectedProduct.price || ''}
                onChange={(e) => handleChange('price', e.target.value)}
              />
            </label>
            <label>
              판매/승인 상태:
              <select
                value={localData[selectedProduct.goodsnum]?.status || ''}
                onChange={(e) => handleLocalChange(selectedProduct.goodsnum, 'status', e.target.value)}
              >
                <option value="승인대기">승인대기</option>
                <option value="판매중">판매중</option>
              </select>
            </label>
            <label>
              재고수량:
              <input
                type="text"
                value={localData[selectedProduct.goodsnum]?.stock || ''}
                onChange={(e) => handleLocalChange(selectedProduct.goodsnum, 'stock', e.target.value)}
              />
            </label>
            <div className="popup-actions">
              <button onClick={handleSave}>저장</button>
              <button onClick={() => setPopupVisible(false)}>취소</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ProductEdit;
