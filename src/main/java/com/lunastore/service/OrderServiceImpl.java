package com.lunastore.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lunastore.dto.*;
import com.lunastore.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lunastore.dao.OrderDAO;
import com.lunastore.dao.BuyerDAO;
import com.lunastore.dao.CartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final BuyerDAO buyerDAO;
    private final CartDAO cartDAO;
    @Value("${upload.url:/uploads/}")
    private String uploadUrl;

    public double getTotalPrice(int b_idx) {
        List<CartVO> carts = cartDAO.getCartsByBuyerId(b_idx);
        return carts.stream()
                .mapToDouble(CartVO::getSubtotal)
                .sum();
    }

    @Override
    @Transactional
    public int insertOrder(OrderVO orderVO) {
        orderDAO.insertOrder(orderVO);
        try {
            for (OrderStateVO stateVO : orderVO.getOrderStateVO()) {
                stateVO.setBo_idx(orderVO.getBo_idx());
                orderDAO.insertOrderState(stateVO);
            }
            return orderVO.getBo_idx();
        } catch (Exception e) {
            throw new RuntimeException("주문 처리 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public int deleteOrder(int bo_idx) {
        return orderDAO.deleteOrder(bo_idx);
    }

    @Override
    @Transactional
    public int successOrder(OrderVO orderVO) {
        if (orderDAO.successOrder(orderVO.getB_idx()) == 1) {
            for (OrderStateVO stateVO : orderVO.getOrderStateVO()) {
                orderDAO.removeItemCount(stateVO);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<OrderStateVO> findOrder(int b_idx, int i_idx) {
        return orderDAO.findOrder(b_idx, i_idx);
    }

    @Override
    @Transactional
    public void updateAddress(AddressVO addressVO) {
        BuyerVO buyerVO = new BuyerVO();
        buyerVO.setB_idx(addressVO.getB_idx());
        buyerVO.setB_firstname(addressVO.getBa_firstname());
        buyerVO.setB_lastname(addressVO.getBa_lastname());
        buyerDAO.updateBuyerName(buyerVO);

        buyerDAO.updateBuyerAddress(addressVO);
    }

    @Override
    @Transactional
    public void updateContact(UpdateContactDTO updateContactDTO) {
        buyerDAO.updateBuyerTel(updateContactDTO.getB_idx(), updateContactDTO.getB_tel());
        buyerDAO.updateBuyerAddressContact(updateContactDTO.getB_idx(), updateContactDTO.getB_tel());
        buyerDAO.updateBuyerOrderContact(updateContactDTO.getB_idx(), updateContactDTO.getB_tel());
    }


    @Override
    @Transactional
    public void submitNewOrder(OrderVO orderVO) {
        double calculatedTotalPrice = getTotalPrice(orderVO.getB_idx());
        orderVO.setTotalPrice(calculatedTotalPrice);
        log.debug("Calculated totalPrice: {}", calculatedTotalPrice);
        orderDAO.insertOrder(orderVO);
        log.debug("Generated bo_idx: {}", orderVO.getBo_idx());

        if (orderVO.getOrderStateVO() != null && !orderVO.getOrderStateVO().isEmpty()) {
            for (OrderStateVO stateVO : orderVO.getOrderStateVO()) {
                stateVO.setBo_idx(orderVO.getBo_idx());
                stateVO.setBos_state(1);
                orderDAO.insertOrderState(stateVO);
                log.debug("Inserted OrderStateVO: {}", stateVO);
            }
        } else {
            log.warn("OrderStateVO list is empty!");
        }
        if (orderVO.getAddressVO() != null) {
            AddressVO addressVO = orderVO.getAddressVO();
            addressVO.setB_idx(orderVO.getB_idx());
            orderDAO.insertAddress(addressVO);
        }
        if (orderVO != null && orderVO.getB_idx() != 0) {
            CartVO cartVO = new CartVO();
            cartVO.setB_idx(orderVO.getB_idx());
            log.debug("Deleted cart for b_idx: {}", orderVO.getB_idx());
        } else {
            log.warn("주문 정보가 유효하지 않아 카트 삭제를 수행할 수 없습니다.");
        }
    }

    @Override
    public void saveAddress(OrderVO orderVO) {
        AddressVO addressVO = new AddressVO();
        addressVO.setB_idx(orderVO.getB_idx());
        addressVO.setBa_firstname(orderVO.getBa_firstname());
        addressVO.setBa_lastname(orderVO.getBa_lastname());
        addressVO.setBa_zipcode(orderVO.getBa_zipcode());
        addressVO.setBa_address(orderVO.getBa_address());
        addressVO.setBa_restaddress(orderVO.getBa_restaddress());
        orderDAO.insertAddress(addressVO);
    }

    @Override
    @Transactional
    public void processPayment(PaymentDTO paymentDTO) throws Exception {
        OrderVO order = orderDAO.findOrderByBoIdx(paymentDTO.getBo_idx());
        if (order == null) {
            throw new Exception("주문을 찾을 수 없습니다.");
        }
        double expectedAmount = getTotalPrice(order.getB_idx()) + 3000;
        double actualAmount = paymentDTO.getAmount();

        double epsilon = 0.01;
        if (Math.abs(expectedAmount - actualAmount) > epsilon) {
            throw new Exception("결제 금액이 일치하지 않습니다.");
        }
        // 서비스 계층을 통해 상태 업데이트 호출
        updateOrderState(paymentDTO.getBo_idx(), 1);

        CartVO cartVO = new CartVO();
        cartVO.setB_idx(order.getB_idx());
        cartDAO.deleteCompleteCart(cartVO);
    }

    @Override
    @Transactional
    public void updateOrderState(int bo_idx, int state) {
        Map<String, Object> params = new HashMap<>();
        params.put("bo_idx", bo_idx);
        params.put("bos_state", state);
        orderDAO.updateOrderState(params);
    }

    @Override
    public int getTotalCount(int b_idx) {
        return orderDAO.countOrdersByBuyer(b_idx);
    }

    @Override
    public List<OrderDTO> getOrdersForBuyer(int b_idx, int startNum, int rowsPage) {
        List<OrderDTO> orders = orderDAO.findOrdersByBuyerIdWithItemPaged(b_idx, startNum, rowsPage);
        for (OrderDTO order : orders) {
            order.setBos_state_text(getStateText(order.getBos_state()));
            if (order.getI_saveimg0() != null && !order.getI_saveimg0().isEmpty()) {
                order.setItemImage(uploadUrl + order.getI_saveimg0());
            } else {
                order.setItemImage("/images/default.png");
            }
        }
        return orders;
    }


    public String getStateText(int state) {
        switch(state) {
            case 1: return "결제 완료";
            case 2: return "배송 준비";
            case 3: return "배송 중";
            case 4: return "배송 완료";
            case 5: return "취소";
            default: return "알 수 없는 상태";
        }
    }

    @Override
    @Transactional
    public void confirmPurchase(int bo_idx) {

        List<Integer> states = orderDAO.findOrderStateByBoIdx(bo_idx);

        boolean allCompleted = states.stream().allMatch(state -> state == 4);

        if (!allCompleted) {
            throw new RuntimeException("구매확정은 모든 상품이 배송완료 상태에서만 가능합니다.");
        }

        int updated = orderDAO.confirmPurchase(bo_idx);
        if (updated == 0) {
            throw new RuntimeException("구매확정에 실패했습니다.");
        }
    }

    @Override
    public List<OrderViewDTO> getOrderViewByBoIdx(int bo_idx) {
        List<OrderViewDTO> orders = orderDAO.findOrderViewWithStateByBoIdx(bo_idx);
        log.debug("Fetched orders: {}", orders);
        return orders.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    @Override
    public List<OrderStateVO> getOrder(int s_idx) {
        return orderDAO.getOrder(s_idx);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderStatusCountsDTO getOrderStatusCountsBySellerId(int s_idx) {
        int purchaseConfirmed = orderDAO.countPurchaseConfirmedBySeller(s_idx);
        int confirmationWaiting = orderDAO.countConfirmationWaitingBySeller(s_idx);
        return new OrderStatusCountsDTO(purchaseConfirmed, confirmationWaiting);
    }
}
