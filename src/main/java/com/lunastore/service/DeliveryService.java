package com.lunastore.service;

import java.util.List;
import com.lunastore.vo.DeliveryVO;

public interface DeliveryService {
    List<DeliveryVO> get_delivery_list(int b_idx);
    List<DeliveryVO> get_delivery_by_status(int b_idx, int bos_state);
    DeliveryVO get_delivery_by_id(int bo_idx);
}