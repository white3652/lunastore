package com.lunastore.dao;

import com.lunastore.dto.ItemDTO;
import com.lunastore.vo.ItemVO;
import com.lunastore.vo.ReviewVO;
import com.lunastore.vo.SearchVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemDAO {
    public static final String MAPPER = "com.lunastore.mapper.ItemMapper";
    private final SqlSession sqlSession;

    public int insert(ItemVO vo){
        return sqlSession.insert(MAPPER + ".insertItem", vo);
    }
    public List<ItemVO> indexGetItems(SearchVO vo) {
        return sqlSession.selectList(MAPPER + ".indexGetItems", vo);
    }
    public int insertItemOption(ItemVO vo){
        return sqlSession.insert(MAPPER + ".insertItemOption", vo);
    }
    public int insertItemImage(ItemVO vo){
        return sqlSession.insert(MAPPER + ".insertItemImage", vo);
    }
    public int insertItemThumbnail(ItemVO vo){
        return sqlSession.insert(MAPPER + ".insertItemThumbnail", vo);
    }
    public ItemVO getItem(int i_idx){
        return sqlSession.selectOne(MAPPER + ".getItem", i_idx);
    }
    public List<ItemVO> getItems(SearchVO vo){
        return sqlSession.selectList(MAPPER + ".getItems", vo);
    }
    public int getTotalCount(SearchVO vo) {
        return sqlSession.selectOne(MAPPER + ".getTotalCount", vo);
    }
    public List<ReviewVO> getReviews(SearchVO vo) {
        return sqlSession.selectList(MAPPER + ".getReviews", vo);
    }
    public int getReviewTotalCount(SearchVO vo) {
        return sqlSession.selectOne(MAPPER + ".getReviewTotalCount", vo);
    }
    public int insertReview(ReviewVO vo) {
        return sqlSession.insert(MAPPER + ".insertReview", vo);
    }
    public int deleteItem(int i_idx) {return sqlSession.delete(MAPPER + ".deleteItem", i_idx);}
    public List<ItemVO> findRandomItems(int limit){return sqlSession.selectList(MAPPER + ".findRandomItems", limit);}
    public int deleteBuyerOrderStateByItemIdx(int i_idx) {
        return sqlSession.delete(MAPPER + ".deleteBuyerOrderStateByItemIdx", i_idx);
    }
    public int deleteItemImgByItemIdx(int i_idx) {
        return sqlSession.delete(MAPPER + ".deleteItemImgByItemIdx", i_idx);
    }
    // 자식 테이블(tb_item_option)에서 레코드 삭제
    public int deleteItemOptionByItemIdx(int i_idx) {
        return sqlSession.delete(MAPPER + ".deleteItemOptionByItemIdx", i_idx);
    }

    // 자식 테이블(tb_item_thumbnail)에서 레코드 삭제
    public int deleteItemThumbnailByItemIdx(int i_idx) {
        return sqlSession.delete(MAPPER + ".deleteItemThumbnailByItemIdx", i_idx);
    }
    public int getReviewCountByBosIdx(int bos_idx) {
        return sqlSession.selectOne(MAPPER + ".getReviewCountByBosIdx", bos_idx);
    }

    public int getExistingReviewCountByBosIdx(int bos_idx) {
        return sqlSession.selectOne(MAPPER + ".getExistingReviewCountByBosIdx", bos_idx);
    }
    public ReviewVO getReviewById(int reviewId) {
        return sqlSession.selectOne(MAPPER + ".getReviewById", reviewId);
    }

    public List<ReviewVO> getReviewsByBuyerId(int buyerId) {
        return sqlSession.selectList(MAPPER + ".getReviewsByBuyerId", buyerId);
    }

    public List<ReviewVO> getReviewsByItemId(int itemId) {
        return sqlSession.selectList(MAPPER + ".getReviewsByItemId", itemId);
    }
    public List<ItemDTO> getItemsBySeller(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getItemsBySeller", s_idx);
    }

    public int getListTotalCount(SearchVO vo) { // 새로운 메서드 추가
        return sqlSession.selectOne(MAPPER + ".getListTotalCount", vo);
    }
}
