package com.lunastore.dto;

import com.lunastore.vo.ReviewVO;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDetailsDTO {
    private int buyerId;
    private int itemId;
    private String buyerNickname;
    private String buyerProfileUrl;
    private String reviewPostDate;
    private String reviewContent;
    private int reviewStar;
    private List<ReviewVO> userReviews;
    private List<ReviewVO> productReviews;
}