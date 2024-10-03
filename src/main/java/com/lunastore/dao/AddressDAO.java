package com.lunastore.dao;

import com.lunastore.vo.AddressVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class AddressDAO {

    public static final String MAPPER = "com.lunastore.mapper.AddressMapper";
    private final SqlSession sqlSession;

    public List<AddressVO> getAddressesByBuyerId(int b_idx) throws SQLException {
        return sqlSession.selectList(MAPPER + ".getAddressesByBuyerId", b_idx);
    }
}