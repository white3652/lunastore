package com.lunastore.dto;

import com.lunastore.vo.ReviewVO;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDetailsDTO {
    private int buyerId; // 구매자 ID 추가
    private int itemId;  // 상품 ID 추가
    private String buyerNickname;
    private String buyerProfileUrl; // 프로필 이미지 URL
    private String reviewPostDate;
    private String reviewContent;
    private int reviewStar;
    private List<ReviewVO> userReviews; // 리뷰 작성자에 대한 다른 리뷰들
    private List<ReviewVO> productReviews; // 해당 상품에 대한 리뷰들
}