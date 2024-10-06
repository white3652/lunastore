package com.lunastore.mapper;


import com.lunastore.dto.SettlementDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SettlementMapper {
    List<SettlementDTO> getSettlementData(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("s_idx") int s_idx);

    List<SettlementDTO> getSettlementDataPaged(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("offset") int offset, @Param("limit") int limit, @Param("s_idx") int s_idx);

    int countSettlementData(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("s_idx") int s_idx);

    int updateSettlement(SettlementDTO settlementDTO);
}