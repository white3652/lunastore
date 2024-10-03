package com.lunastore.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import com.lunastore.vo.AddressVO;
import com.lunastore.vo.BuyerVO;

@Repository
@RequiredArgsConstructor
public class BuyerDAO {
    public static final String MAPPER = "com.lunastore.BuyerMapper";
    private final SqlSession sqlSession;

    public int join(BuyerVO vo) throws SQLException {
        return sqlSession.insert(MAPPER + ".join", vo);
    }

    public int info(int b_idx) throws SQLException {
        int result1 = sqlSession.insert(MAPPER + ".info", b_idx);
        int result2 = sqlSession.insert(MAPPER + ".insertBuyerAddress", b_idx);
        return (result1 > 0 && result2 > 0) ? 1 : 0;

    }

    public BuyerVO getBuyerById(int b_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getBuyerById", b_idx);
    }

    public int state(int b_idx) throws SQLException {
        return sqlSession.insert(MAPPER + ".state", b_idx);
    }

    public String getPassword(String b_email) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getPassword", b_email);
    }

    public BuyerVO login(String b_email) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".login", b_email);
    }

    public void lastLoginDate(int b_idx) throws SQLException {
        sqlSession.update(MAPPER + ".lastLoginDate", b_idx);
    }

    public BuyerVO updateBuyer(BuyerVO vo) throws SQLException {
        BuyerVO newVO = null;
        if (sqlSession.update(MAPPER + ".updateBuyer", vo) == 1) {
            newVO = getBuyer(vo.getB_idx());
        }
        return newVO;
    }

    public BuyerVO getBuyer(int b_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getBuyer", b_idx);
    }

    public AddressVO getAddress(int b_idx) {
        return sqlSession.selectOne(MAPPER + ".getAddress", b_idx);
    }

    public int changeDefaultAddress(int b_idx) {
        return sqlSession.update(MAPPER + ".changeDefaultAddress", b_idx);
    }

    public int insertAddress(AddressVO vo) throws SQLException {
        return sqlSession.insert(MAPPER + ".insertAddress", vo);
    }

    public AddressVO insertContact(AddressVO vo) throws SQLException {
        AddressVO newVO = null;
        if (sqlSession.update(MAPPER + ".insertContact", vo) == 1) {
            newVO = getAddress(vo.getB_idx());
        }
        return newVO;
    }

    public BuyerVO infoUpdate(BuyerVO vo) throws SQLException {
        BuyerVO newVO = null;
        if (sqlSession.update(MAPPER + ".infoUpdate", vo) == 2) {
            newVO = getBuyer(vo.getB_idx());
        }
        return newVO;
    }

    public int cancel(int b_idx) throws SQLException {
        return sqlSession.update(MAPPER + ".cancel", b_idx);
    }

    public int nicknameCheck(String b_nickname) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".nicknameCheck", b_nickname);
    }

    public int emailCheck(String b_email) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".emailCheck", b_email);
    }

    public int telCheck(String b_tel) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".telCheck", b_tel);
    }

    public int updatePassword(BuyerVO vo) throws SQLException {
        return sqlSession.update(MAPPER + ".updatePassword", vo);
    }

    public int matchTelEmail(BuyerVO vo) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".matchTelEmail", vo);
    }

    public String getPw(int b_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getPw", b_idx);
    }

    public int verifyEmail(int b_idx) throws SQLException {
        return sqlSession.update(MAPPER + ".verifyEmail", b_idx);
    }

    public int getB_idx(String b_email) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".getB_idx", b_email);
    }
    /**
     * tb_buyer 테이블의 이름 업데이트
     */
    public void updateBuyerName(BuyerVO buyerVO) {
        sqlSession.update(MAPPER + ".updateBuyerName", buyerVO);
    }

    /**
     * tb_buyer_address 테이블의 주소 업데이트
     */
    public void updateBuyerAddress(AddressVO addressVO) {
        sqlSession.update(MAPPER + ".updateBuyerAddress", addressVO);
    }

    /**
     * tb_buyer_address 테이블의 연락처 업데이트
     */
    public void updateBuyerAddressContact(int b_idx, String ba_contact) {
        Map<String, Object> params = new HashMap<>();
        params.put("b_idx", b_idx);
        params.put("ba_contact", ba_contact);
        sqlSession.update(MAPPER + ".updateBuyerAddressContact", params);
    }

    /**
     * tb_buyer_order 테이블의 연락처 업데이트
     */
    public void updateBuyerOrderContact(int b_idx, String bo_contact) {
        Map<String, Object> params = new HashMap<>();
        params.put("b_idx", b_idx);
        params.put("bo_contact", bo_contact);
        sqlSession.update(MAPPER + ".updateBuyerOrderContact", params);
    }
    /**
     * tb_buyer 테이블의 연락처 업데이트
     */
    public void updateBuyerTel(int b_idx, String b_tel) {
        Map<String, Object> params = new HashMap<>();
        params.put("b_idx", b_idx);
        params.put("b_tel", b_tel);
        sqlSession.update(MAPPER + ".updateBuyerTel", params);
    }
}