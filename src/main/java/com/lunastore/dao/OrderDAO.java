package com.lunastore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lunastore.dto.OrderDTO;
import com.lunastore.dto.OrderViewDTO;
import com.lunastore.dto.SettlementDTO;
import com.lunastore.vo.AddressVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import com.lunastore.vo.OrderStateVO;
import com.lunastore.vo.OrderVO;

@Repository
@RequiredArgsConstructor
public class OrderDAO {

    public static final String MAPPER = "com.lunastore.OrderMapper";
    private final SqlSession sqlSession;

    public int insertOrder(OrderVO vo) {return sqlSession.insert(MAPPER + ".insertOrder", vo);}
    public int insertOrderState(OrderStateVO vo) {return sqlSession.insert(MAPPER + ".insertOrderState", vo);}
    public int deleteOrder(int bo_idx) {return sqlSession.delete(MAPPER + ".deleteOrder", bo_idx);}
    public int successOrder(int b_idx) {return sqlSession.delete(MAPPER + ".successOrder", b_idx);}
    public int removeItemCount(OrderStateVO orderStateVO) {return sqlSession.update(MAPPER + ".removeItemCount", orderStateVO);}
    public OrderVO findOrderByBoIdx(int bo_idx) {return sqlSession.selectOne(MAPPER + ".findOrderByBoIdx", bo_idx);}
    public void insertAddress(AddressVO addressVO) {sqlSession.insert(MAPPER + ".insertAddress", addressVO);}
    public List<OrderDTO> findOrdersByBuyerId(int b_idx) {return sqlSession.selectList(MAPPER + ".findOrdersByBuyerId", b_idx);}
    public List<OrderDTO> findOrdersByBuyerIdWithItem(int b_idx) {return sqlSession.selectList(MAPPER + ".findOrdersByBuyerIdWithItem", b_idx);}
    public int countOrdersByBuyer(int b_idx) {return sqlSession.selectOne(MAPPER + ".countOrdersByBuyer", b_idx);}
    public List<OrderViewDTO> findOrderViewByBoIdx(int bo_idx) {return sqlSession.selectList(MAPPER + ".findOrderViewByBoIdx", bo_idx);}

    public List<OrderStateVO> findOrder(int b_idx, int i_idx) {
        Map<String, Integer> params = new HashMap<>();
        params.put("b_idx", b_idx);
        params.put("i_idx", i_idx);
        return sqlSession.selectList(MAPPER + ".findOrder", params);
    }

    public int updateOrderStatus(int bo_idx, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("bo_idx", bo_idx);
        params.put("bos_status", status);
        return sqlSession.update(MAPPER + ".updateOrderStatus", params);
    }

    public void updateOrderState(Map<String, Object> params) {
        sqlSession.update(MAPPER + ".updateOrderState", params);
    }

    public List<OrderDTO> findOrdersByBuyerIdWithItemPaged(int b_idx, int startNum, int rowsPage) {
        Map<String, Object> params = new HashMap<>();
        params.put("b_idx", b_idx);
        params.put("startNum", startNum);
        params.put("rowsPage", rowsPage);
        return sqlSession.selectList(MAPPER + ".findOrdersByBuyerIdWithItemPaged", params);
    }

    public List<Integer> findOrderStateByBoIdx(int boIdx) {
        return sqlSession.selectList(MAPPER + ".findOrderStateByBoIdx", boIdx);
    }
    public List<OrderViewDTO> findOrderViewWithStateByBoIdx(int bo_idx) {
        return sqlSession.selectList(MAPPER + ".findOrderViewWithStateByBoIdx", bo_idx);
    }

    public int confirmPurchase(int boIdx) {
        return sqlSession.update(MAPPER + ".confirmPurchase", boIdx);
    }
    public List<OrderStateVO> getOrder(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getOrder", s_idx);
    }

    public int countPurchaseConfirmedBySeller(int s_idx) {
        return sqlSession.selectOne(MAPPER + ".countPurchaseConfirmedBySeller", s_idx);
    }
    public int countConfirmationWaitingBySeller(int s_idx) {
        return sqlSession.selectOne(MAPPER + ".countConfirmationWaitingBySeller", s_idx);
    }
}
