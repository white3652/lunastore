package com.lunastore.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.lunastore.dto.DailySalesDTO;
import com.lunastore.dto.StoreStatusDTO;
import com.lunastore.vo.*;
import jakarta.servlet.http.HttpServletResponse;

import com.lunastore.common.PageNav;

public interface SellerService {
    int join(SellerVO sellerVO);
    int infoState(SellerVO sellerVO);
    SellerVO login(String s_businessnum, String s_pw);
    SellerVO update(SellerVO sellerVO);
    int cancel(int s_idx);
    int storenameCheck(String s_storename);
    int telCheck(String s_tel);
    int businessnumCheck(String s_businessnum);
    int emailCheck(String s_email);
    void sendEmail(SellerVO sellerVO, String div) throws Exception;
    void findPw(HttpServletResponse response, SellerVO sellerVO) throws Exception;
    int passwordCheck(int s_idx, String s_pw);
    String joinEmail(String s_email);
    int verifyEmail(String s_email);
    List<ItemVO> getItem(int s_idx);
    List<ItemVO> getItems(SearchVO vo);
    int getItemTotalCount(SearchVO vo);
    PageNav setPageNav(PageNav pageNav, int pageNum, int pageBlock);
    List<OrderStateVO> getOrder(int s_idx);
    List<OrderStateVO> getOrders(SearchVO vo);
    int getOrderTotalCount(SearchVO vo);
    int updateOrderState(OrderStateVO orderStateVO);
    int updateItemState(ItemVO itemVO);
    List<ReviewVO> getReviews(SearchVO vo);
    int getReviewTotalCount(SearchVO vo);
    SellerVO getSellerBySIdx(int s_idx);
    SellerVO infoUpdate(SellerVO sellerVO) throws SQLException;
    SellerVO getSellerWithStoreInfo(int s_idx);
    List<ReviewVO> getReviewsBySellerId(int s_idx);
    List<QnaVO> getInquiriesBySellerId(int s_idx);
    List<ItemVO> getItemsWithCounts(int s_idx);
    // 기존 메서드
    int getNewOrderCount(int sellerId);

    // 추가 메서드
    int getPreparingShipmentCount(int sellerId);

    int getShippingCount(int sellerId);

    int getDeliveredCount(int sellerId);

    int getRefundRequestCount(int sellerId);

    int getReturnRequestCount(int sellerId);

    int getExchangeRequestCount(int sellerId);

    int getTodaySettlement(int sellerId);

    int getUpcomingSettlement(int sellerId);

    int getConfirmedPurchaseCount(int sellerId);

    int getPendingConfirmationCount(int sellerId);

    int getStoreWishlistCount(int sellerId);

    int getItemWishlistCount(int sellerId);

    int getModificationRequestCount(int sellerId);

    List<DailySalesDTO> getDailySalesData(int s_idx);

    List<ReviewVO> getReviewsByPage(int s_idx, int offset, int limit);
    int getTotalReviewCount(int s_idx);
    List<StoreStatusDTO> getStoreStatusList(int s_idx, LocalDate startDate, LocalDate endDate, int page, int pageSize);

    int getTotalStoreStatusCount(int s_idx, LocalDate startDate, LocalDate endDate);
}