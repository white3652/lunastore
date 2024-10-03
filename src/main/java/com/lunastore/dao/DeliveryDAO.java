package com.lunastore.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import com.lunastore.vo.DeliveryVO;

@Repository
@RequiredArgsConstructor
public class DeliveryDAO {
    public static final String MAPPER = "com.lunastore.DeliveryMapper";
    private final SqlSession sqlSession;

    public List<DeliveryVO> get_delivery_list(int b_idx) {
        return sqlSession.selectList(MAPPER + ".get_delivery_list", b_idx);
    }

    public List<DeliveryVO> get_delivery_by_status(int b_idx, int bos_state) {
        return sqlSession.selectList(MAPPER + ".get_delivery_by_status", new HashMap<String, Object>() {{
            put("b_idx", b_idx);
            put("bos_state", bos_state);
        }});
    }
    public DeliveryVO get_delivery_by_id(int bo_idx) {
        return sqlSession.selectOne(MAPPER + ".get_delivery_by_id", bo_idx);
    }
}