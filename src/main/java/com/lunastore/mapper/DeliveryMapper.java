package com.lunastore.mapper;

import com.lunastore.vo.DeliveryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    List<DeliveryVO> get_delivery_list(@Param("b_idx") int b_idx);

    List<DeliveryVO> get_delivery_by_status(@Param("b_idx") int b_idx, @Param("bos_state") int bos_state);

    DeliveryVO get_delivery_by_id(@Param("bo_idx") int bo_idx);

    int count_deliveries(@Param("b_idx") int b_idx);

    void update_delivery_status(@Param("bo_idx") int bo_idx, @Param("bos_state") int bos_state);

}