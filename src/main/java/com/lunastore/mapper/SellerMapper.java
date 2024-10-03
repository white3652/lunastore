package com.lunastore.mapper;

import com.lunastore.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerMapper {
    void join(SellerVO seller);
    void info(int s_idx);
    void state(int s_idx);
    String getPassword(String s_businessnum);
    SellerVO login(String s_businessnum);
    void lastLoginDate(int s_idx);
    void update(SellerVO seller);
    void infoUpdate(SellerVO seller);
    SellerVO getSeller(int s_idx);
    void cancel(int s_idx);
    int storenameCheck(String s_storename);
    int telCheck(String s_tel);
    int businessnumCheck(String s_businessnum);
    int emailCheck(String s_email);
    void updatePassword(SellerVO seller);
    int matchBusinessnumEmail(SellerVO seller);
    int passwordCheck(String s_pw);
    String getPw(int s_idx);
    void verifyEmail(int s_idx);
    int getS_idx(String s_email);
    List<ItemVO> getItem(int s_idx);
    List<ItemVO> getItems(SearchVO search);
    int getItemTotalCount(SearchVO search);
    List<OrderStateVO> getOrder(int s_idx);
    List<OrderStateVO> getOrders(SearchVO search);
    int getOrderTotalCount(SearchVO search);
    void updateOrderState(OrderStateVO orderState);
    void updateItemState(ItemVO item);
    List<ReviewVO> getReviews(SearchVO search);
    int getReviewTotalCount(SearchVO search);
    SellerVO getSellerBySIdx(int s_idx);
    List<ReviewVO> getReviewsBySellerId(int s_idx);
    List<QnaVO> getInquiriesBySellerId(int s_idx);
    List<ItemVO> getItemsWithCounts(int s_idx);

    int getNewOrderCount(@Param("s_idx") int sellerId);

    // 추가 메서드
    int getPreparingShipmentCount(@Param("s_idx") int sellerId);

    int getShippingCount(@Param("s_idx") int sellerId);

    int getDeliveredCount(@Param("s_idx") int sellerId);

    int getRefundRequestCount(@Param("s_idx") int sellerId);

    int getReturnRequestCount(@Param("s_idx") int sellerId);

    int getExchangeRequestCount(@Param("s_idx") int sellerId);

    int getTodaySettlement(@Param("s_idx") int sellerId);

    int getUpcomingSettlement(@Param("s_idx") int sellerId);


}