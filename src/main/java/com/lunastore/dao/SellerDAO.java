package com.lunastore.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lunastore.dto.DailySalesDTO;
import com.lunastore.dto.StoreStatusDTO;
import com.lunastore.vo.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SellerDAO {
    public static final String MAPPER = "com.lunastore.SellerMapper";
    private final SqlSession sqlSession;

    public int join(SellerVO vo) throws SQLException {return sqlSession.insert(MAPPER + ".join", vo);}
    public int info(int s_idx) throws SQLException {return sqlSession.insert(MAPPER + ".info", s_idx);}
    public int state(int s_idx) throws SQLException {return sqlSession.insert(MAPPER + ".state", s_idx);}
    public String getPassword(String s_businessnum) throws SQLException {return sqlSession.selectOne(MAPPER + ".getPassword", s_businessnum);}
    public SellerVO login(String s_businessnum) throws SQLException {return sqlSession.selectOne(MAPPER + ".login", s_businessnum);}
    public void lastLoginDate(int s_idx) throws SQLException {sqlSession.update(MAPPER + ".lastLoginDate", s_idx);}
    public SellerVO getSeller(int s_idx) throws SQLException {return sqlSession.selectOne(MAPPER + ".getSeller", s_idx);}
    public int cancel(int s_idx) throws SQLException {return sqlSession.update(MAPPER + ".cancel", s_idx);}
    public int storenameCheck(String s_storename) throws SQLException {return sqlSession.selectOne(MAPPER + ".storenameCheck", s_storename);}
    public int telCheck(String s_tel) throws SQLException {return sqlSession.selectOne(MAPPER + ".telCheck", s_tel);}
    public int businessnumCheck(String s_businessnum) throws SQLException {return sqlSession.selectOne(MAPPER + ".businessnumCheck", s_businessnum);}
    public int emailCheck(String s_email) throws SQLException {return sqlSession.selectOne(MAPPER + ".emailCheck", s_email);}
    public int updatePassword(SellerVO vo) throws Exception {return sqlSession.update(MAPPER + ".updatePassword", vo);}
    public int matchBusinessnumEmail(SellerVO vo) throws SQLException {return sqlSession.selectOne(MAPPER + ".matchBusinessnumEmail", vo);}
    public int passwordCheck(String s_pw) throws SQLException {return sqlSession.selectOne(MAPPER + ".passwordCheck", s_pw);}
    public String getPw(int s_idx) throws SQLException {return sqlSession.selectOne(MAPPER + ".getPw", s_idx);}
    public int verifyEmail(int s_idx) {
        return sqlSession.update(MAPPER + ".verifyEmail", s_idx);
    }
    public int getS_idx(String s_email) {
        return sqlSession.selectOne(MAPPER + ".getS_idx", s_email);
    }
    public List<ItemVO> getItem(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getItem", s_idx);
    }
    public List<ItemVO> getItems(SearchVO vo) {
        return sqlSession.selectList(MAPPER + ".getItems", vo);
    }
    public int getItemTotalCount(SearchVO vo) {
        return sqlSession.selectOne(MAPPER + ".getItemTotalCount", vo);
    }
    public List<OrderStateVO> getOrder(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getOrder", s_idx);
    }
    public List<OrderStateVO> getOrders(SearchVO vo) {
        return sqlSession.selectList(MAPPER + ".getOrders", vo);
    }
    public int getOrderTotalCount(SearchVO vo) {
        return sqlSession.selectOne(MAPPER + ".getOrderTotalCount", vo);
    }
    public int updateItemState(ItemVO itemVO) {
        return sqlSession.update(MAPPER + ".updateItemState", itemVO);
    }
    public List<ReviewVO> getReviews(SearchVO vo) {
        return sqlSession.selectList(MAPPER + ".getReviews", vo);
    }
    public int getReviewTotalCount(SearchVO vo) {
        return sqlSession.selectOne(MAPPER + ".getReviewTotalCount", vo);
    }
    public SellerVO getSellerBySIdx(int s_idx) {
        return sqlSession.selectOne(MAPPER + ".getSellerBySIdx", s_idx);
    }
    public int updateOrderState(OrderStateVO orderStateVO) {return sqlSession.update(MAPPER + ".updateOrderState", orderStateVO);}
    public SellerVO getSellerWithStoreInfo(int s_idx) {return sqlSession.selectOne(MAPPER + ".getSellerWithStoreInfo", s_idx);}

    public SellerVO update(SellerVO vo) throws SQLException {
        SellerVO newVO = null;
        if (sqlSession.update(MAPPER + ".update", vo) == 1) {
            newVO = getSeller(vo.getS_idx());
        }
        return newVO;
    }

    public SellerVO infoUpdate(SellerVO vo) throws SQLException {
        SellerVO newVO = null;
        if (sqlSession.update(MAPPER + ".infoUpdate", vo) == 2) {
            newVO = getSeller(vo.getS_idx());
        }
        return newVO;
    }
    public List<ReviewVO> getReviewsBySellerId(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getReviewsBySellerId", s_idx);
    }

    public List<QnaVO> getInquiriesBySellerId(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getInquiriesBySellerId", s_idx);
    }
    public List<ItemVO> getItemsWithCounts(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getItemsWithCounts", s_idx);
    }
    // 새로운 메서드 추가
    public int getNewOrderCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getNewOrderCount", s_idx);
    }

    public int getPreparingShipmentCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getPreparingShipmentCount", s_idx);
    }

    public int getShippingCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getShippingCount", s_idx);
    }

    public int getDeliveredCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getDeliveredCount", s_idx);
    }

    public int getRefundRequestCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getRefundRequestCount", s_idx);
    }

    public int getReturnRequestCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getReturnRequestCount", s_idx);
    }

    public int getExchangeRequestCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getExchangeRequestCount", s_idx);
    }

    public int getTodaySettlement(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getTodaySettlement", s_idx);
    }

    public int getUpcomingSettlement(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getUpcomingSettlement", s_idx);
    }

    public int getConfirmedPurchaseCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getConfirmedPurchaseCount", s_idx);
    }

    public int getPendingConfirmationCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getPendingConfirmationCount", s_idx);
    }

    public int getStoreWishlistCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getStoreWishlistCount", s_idx);
    }

    public int getItemWishlistCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getItemWishlistCount", s_idx);
    }

    public int getModificationRequestCount(int s_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getModificationRequestCount", s_idx);
    }
    public List<DailySalesDTO> getDailySalesData(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getDailySalesData", s_idx);
    }




    public List<ReviewVO> getReviewsByPage(int s_idx, int offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("s_idx", s_idx);
        params.put("offset", offset);
        params.put("limit", limit);
        return sqlSession.selectList(MAPPER + ".getReviewsByPage", params);
    }

    public int getTotalReviewCount(int s_idx) {
        return sqlSession.selectOne(MAPPER + ".getTotalReviewCount", s_idx);
    }
    public List<StoreStatusDTO> getStoreStatusList(int s_idx, LocalDate startDate, LocalDate endDate, int offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("s_idx", s_idx);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("offset", offset);
        params.put("limit", limit);
        return sqlSession.selectList(MAPPER + ".getStoreStatusList", params);
    }

    public int getTotalStoreStatusCount(int s_idx, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("s_idx", s_idx);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return sqlSession.selectOne(MAPPER + ".getTotalStoreStatusCount", params);
    }
}