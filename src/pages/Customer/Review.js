import React, { useState, useEffect } from "react";
import "./Review.css";
import axios from "axios";

const Review = () => {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await axios.get("http://localhost:8082/api/reviews"); // 백엔드 API 경로
        setReviews(response.data);
      } catch (error) {
        console.error("리뷰 데이터를 가져오는 데 실패했습니다:", error);

        // 샘플 데이터
        const sampleData = [
          {
            id: 1,
            registrationTime: "2024-11-15 14:20:32",
            productId: "346214241515",
            productName: "무안 대파",
            score: "7/10",
            comment: "대파가 아쉬워요.",
            author: "박준희",
          },
          {
            id: 2,
            registrationTime: "2024-11-15 20:15:19",
            productId: "346214241515",
            productName: "무안 대파",
            score: "8/10",
            comment: "대파가 좋습니다.",
            author: "김석윤",
          },
          {
            id: 3,
            registrationTime: "2024-11-16 12:20:00",
            productId: "138571306315",
            productName: "무안 햇양파",
            score: "9/10",
            comment: "양파가 커요.",
            author: "장우영",
          },
          {
            id: 4,
            registrationTime: "2024-11-17 15:10:22",
            productId: "138571306315",
            productName: "무안 햇양파",
            score: "8/10",
            comment: "너무 상세해요.",
            author: "김강현",
          },
        ];

        setReviews(sampleData);
      }
    };

    fetchReviews();
  }, []);

  return (
    <div className="review-container">
      <h2 className="review-title">상품평</h2>
      <table className="review-table">
        <thead>
          <tr>
            <th>등록일시</th>
            <th>노출상품ID</th>
            <th>노출 상품명</th>
            <th>별점</th>
            <th>상품평 코멘트</th>
            <th>작성자</th>
          </tr>
        </thead>
        <tbody>
          {reviews.map((review) => (
            <tr key={review.id}>
              <td>{review.registrationTime}</td>
              <td>{review.productId}</td>
              <td>{review.productName}</td>
              <td>{review.score}</td>
              <td>{review.comment}</td>
              <td>{review.author}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Review;
