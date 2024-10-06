package com.lunastore.dao;


import com.lunastore.dto.SettlementDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SettlementDAO {
    public static final String MAPPER = "com.lunastore.mapper.SettlementMapper";
    private final SqlSession sqlSession;

    public List<SettlementDTO> getSettlementData(String startDate, String endDate, int s_idx) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("s_idx", s_idx);
        return sqlSession.selectList(MAPPER + ".getSettlementData", params);
    }

    public List<SettlementDTO> getSettlementDataPaged(String startDate, String endDate, int offset, int limit, int s_idx) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("s_idx", s_idx);
        return sqlSession.selectList(MAPPER + ".getSettlementDataPaged", params);
    }

    public int countSettlementData(String startDate, String endDate, int s_idx) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("s_idx", s_idx);
        return sqlSession.selectOne(MAPPER + ".countSettlementData", params);
    }

    public int updateSettlement(SettlementDTO settlementDTO) {
        return sqlSession.update(MAPPER + ".updateSettlement", settlementDTO);
    }
}