package com.lunastore.service;

import java.util.List;

import com.lunastore.dto.ItemDTO;
import com.lunastore.dto.ReviewDetailsDTO;
import com.lunastore.vo.ItemVO;
import com.lunastore.vo.ReviewVO;
import com.lunastore.vo.SearchVO;

public interface ItemService {
    int insert(ItemVO vo);
    ItemVO view(int i_idx);
    List<ItemVO> getItems(SearchVO vo);
    List<ItemVO> indexGetItems(SearchVO vo);
    int getTotalCount(SearchVO vo);
    List<ReviewVO> reviewList(SearchVO searchVO);
    int getReviewTotalCount(SearchVO vo);
    boolean insertReview(ReviewVO reviewVO);
    int deleteItem(int i_idx);
    List<ItemVO> getRandomItems(int limit);
    ReviewDetailsDTO getReviewDetails(int reviewId) throws Exception;
    List<ItemDTO> getItemsBySeller(int s_idx);
}