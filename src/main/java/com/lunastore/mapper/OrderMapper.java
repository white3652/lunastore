package com.lunastore.mapper;

import com.lunastore.dto.OrderDTO;
import com.lunastore.dto.OrderViewDTO;
import com.lunastore.dto.SettlementDTO;
import com.lunastore.vo.AddressVO;
import com.lunastore.vo.OrderVO;
import com.lunastore.vo.OrderStateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderVO order);
    void insertOrderState(OrderStateVO orderState);
    void deleteOrder(int bo_idx);
    void successOrder(int b_idx);
    void removeItemCount(OrderStateVO orderState);
    OrderStateVO findOrder(Map<String, Object> params);
    void insertAddress(AddressVO addressVO);
    List<OrderDTO> findOrdersByBuyerId(int b_idx);
    List<OrderDTO> findOrdersByBuyerIdWithItem(@Param("b_idx") int b_idx);
    List<OrderDTO> findOrdersByBuyerIdWithItemPaged(Map<String, Object> params);
    int countOrdersByBuyer(int b_idx);
    OrderDTO getOrderByBoIdx(@Param("boIdx") int boIdx);
    List<OrderDTO> getOrderItemsByBoIdx(@Param("boIdx") int boIdx);
    List<OrderStateVO> getOrder(@Param("s_idx") int s_idx);
    int confirmPurchase(@Param("bo_idx") int bo_idx);
    List<OrderViewDTO> findOrderViewWithStateByBoIdx(int bo_idx);
    void updateOrderState(Map<String, Object> params);

    int countPurchaseConfirmedBySeller(int s_idx);
    int countConfirmationWaitingBySeller(int s_idx);
}