package com.lunastore.mapper;

import com.lunastore.vo.ItemVO;
import com.lunastore.vo.ReviewVO;
import com.lunastore.vo.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemMapper {
    void insertItem(ItemVO item);
    List<ItemVO> indexGetItems(SearchVO search);
    void insertItemOption(ItemVO item);
    void insertItemImage(ItemVO item);
    void insertItemThumbnail(ItemVO item);
    ItemVO getItem(int i_idx);
    List<ItemVO> getItems(SearchVO search);
    int getTotalCount(SearchVO search);
    void insertReview(ReviewVO review);
    List<ReviewVO> getReviews(SearchVO search);
    int getReviewTotalCount(SearchVO search);
    void deleteItem(int i_idx);
    int getReviewCountByBosIdx(int bos_idx);
    ReviewVO getReviewById(@Param("reviewId") int reviewId);
    List<ReviewVO> getReviewsByBuyerId(@Param("buyerId") int buyerId);
    List<ReviewVO> getReviewsByItemId(@Param("itemId") int itemId);
    int getListTotalCount(SearchVO searchVO);
}