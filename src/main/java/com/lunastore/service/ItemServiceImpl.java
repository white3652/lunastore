package com.lunastore.service;


import java.util.List;

import com.lunastore.dto.ItemDTO;
import com.lunastore.dto.ReviewDetailsDTO;
import com.lunastore.mapper.ItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.lunastore.dao.ItemDAO;
import com.lunastore.vo.ItemVO;
import com.lunastore.vo.ReviewVO;
import com.lunastore.vo.SearchVO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDAO dao;
    @Value("${upload.url:/uploads/}")
    private String uploadUrl;
    @Override
    public int insert(ItemVO vo) {
        int result = 0;
        if (dao.insert(vo) == 1) {
            if (dao.insertItemOption(vo) == 1 && dao.insertItemImage(vo) == 1 && dao.insertItemThumbnail(vo) == 1) {
                result = 1;
            }
        }
        return result;
    }

    @Override
    public ItemVO view(int i_idx) {
        return dao.getItem(i_idx);
    }

    @Override
    public List<ItemVO> getItems(SearchVO vo) {
        vo.setStartIdx((vo.getPageNum() - 1) * vo.getViewNum());
        return dao.getItems(vo);
    }

    @Override
    public List<ItemVO> indexGetItems(SearchVO vo) {
        return dao.getItems(vo);
    }

    @Override
    public int getTotalCount(SearchVO vo) {
        return dao.getTotalCount(vo);
    }

    @Override
    public List<ReviewVO> reviewList(SearchVO searchVO) {
        return dao.getReviews(searchVO);
    }

    @Override
    public int getReviewTotalCount(SearchVO vo) {
        return dao.getReviewTotalCount(vo);
    }

    @Override
    public int deleteItem(int i_idx) {
        dao.deleteBuyerOrderStateByItemIdx(i_idx);
        dao.deleteItemImgByItemIdx(i_idx);
        // 자식 테이블(tb_item_option)의 레코드 삭제
        dao.deleteItemOptionByItemIdx(i_idx);
        // 자식 테이블(tb_item_thumbnail)의 레코드 삭제
        dao.deleteItemThumbnailByItemIdx(i_idx);
        return dao.deleteItem(i_idx);
    }

    @Override
    public List<ItemVO> getRandomItems(int limit) {
        return dao.findRandomItems(limit);
    }

    @Override
    public boolean insertReview(ReviewVO reviewVO) {
        // bos_idx가 유효한지 확인
        int reviewCount = dao.getReviewCountByBosIdx(reviewVO.getBos_idx());
        if (reviewCount == 0) {
            log.warn("유효하지 않은 bos_idx: " + reviewVO.getBos_idx());
            return false;
        }

        // 동일한 bos_idx에 리뷰가 이미 존재하는지 확인
        int existingCount = dao.getExistingReviewCountByBosIdx(reviewVO.getBos_idx());
        if (existingCount > 0) {
            log.warn("이미 리뷰가 존재하는 bos_idx: " + reviewVO.getBos_idx());
            return false;
        }

        int rows = dao.insertReview(reviewVO);
        return rows > 0;
    }

    @Override
    public ReviewDetailsDTO getReviewDetails(int reviewId) throws Exception {
        // 특정 리뷰 가져오기
        ReviewVO mainReview = dao.getReviewById(reviewId);
        if (mainReview == null) {
            throw new Exception("리뷰를 찾을 수 없습니다.");
        }

        // 리뷰 작성자 프로필 URL 설정
        String buyerProfileUrl = (mainReview.getB_profile() != null) ? uploadUrl + mainReview.getB_profile() : "/css/img/defaultProfile.png";

        // 리뷰 작성자에 대한 다른 리뷰 가져오기
        List<ReviewVO> userReviews = dao.getReviewsByBuyerId(mainReview.getBuyerId());

        // 해당 상품에 대한 리뷰 가져오기
        List<ReviewVO> productReviews = dao.getReviewsByItemId(mainReview.getItemId());

        // DTO로 묶어서 반환
        ReviewDetailsDTO dto = new ReviewDetailsDTO();
        dto.setBuyerId(mainReview.getBuyerId());
        dto.setItemId(mainReview.getItemId());
        dto.setBuyerNickname(mainReview.getB_nickname());
        dto.setBuyerProfileUrl(buyerProfileUrl);
        dto.setReviewPostDate(mainReview.getBr_postdate());
        dto.setReviewContent(mainReview.getBr_content());
        dto.setReviewStar(mainReview.getBr_star());
        dto.setUserReviews(userReviews);
        dto.setProductReviews(productReviews);

        return dto;
    }
    @Override
    public List<ItemDTO> getItemsBySeller(int s_idx) {
        return dao.getItemsBySeller(s_idx);
    }
}