package com.lunastore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.lunastore.dao.DeliveryDAO;
import com.lunastore.vo.DeliveryVO;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryDAO deliveryDAO;

    @Override
    public List<DeliveryVO> get_delivery_list(int b_idx) {
        return deliveryDAO.get_delivery_list(b_idx);
    }

    @Override
    public List<DeliveryVO> get_delivery_by_status(int b_idx, int bos_state) {
        return deliveryDAO.get_delivery_by_status(b_idx, bos_state);
    }

    @Override
    public DeliveryVO get_delivery_by_id(int bo_idx) {
        return deliveryDAO.get_delivery_by_id(bo_idx);
    }
}