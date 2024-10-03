package com.lunastore.service;

import java.sql.SQLException;
import java.util.List;

import com.lunastore.dto.*;
import com.lunastore.vo.AddressVO;
import com.lunastore.vo.BuyerVO;
import com.lunastore.vo.OrderStateVO;
import com.lunastore.vo.OrderVO;

public interface OrderService {
    int insertOrder(OrderVO orderVO);
    int deleteOrder(int bo_idx);
    int successOrder(OrderVO orderVO);
    List<OrderStateVO> findOrder(int b_idx, int i_idx);
    void updateAddress(AddressVO addressVO);
    void updateContact(UpdateContactDTO updateContactDTO) throws SQLException;
    void processPayment(PaymentDTO paymentDTO) throws Exception;
    void updateOrderState(int bo_idx, int state);
    void submitNewOrder(OrderVO orderVO);
    void saveAddress(OrderVO orderVO);
    List<OrderDTO> getOrdersForBuyer(int b_idx, int startNum, int rowsPage);
    String getStateText(int state);
    int getTotalCount(int b_idx);
    List<OrderStateVO> getOrder(int s_idx);
    void confirmPurchase(int bo_idx);
    List<OrderViewDTO> getOrderViewByBoIdx(int bo_idx);
    OrderStatusCountsDTO getOrderStatusCountsBySellerId(int s_idx);
}