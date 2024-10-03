package com.lunastore.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import com.lunastore.vo.CartVO;

@Repository
@RequiredArgsConstructor
public class CartDAO {
    public static final String MAPPER = "com.lunastore.CartMapper";
    private final SqlSession sqlSession;

    public int searchCart(CartVO vo) {
        return sqlSession.selectOne(MAPPER + ".searchCart", vo);
    }
    public int insertCart(CartVO vo) {
        return sqlSession.insert(MAPPER + ".insertCart", vo);
    }
    public List<CartVO> getCarts(int b_idx) {
        return sqlSession.selectList(MAPPER + ".getCarts", b_idx);
    }
    public int deleteCart(CartVO vo) {
        return sqlSession.delete(MAPPER + ".deleteCart", vo);
    }
    public int deleteCompleteCart(CartVO vo) {return sqlSession.delete(MAPPER + ".deleteCompleteCart", vo);}
    public int searchLike(CartVO vo) {
        return sqlSession.selectOne(MAPPER + ".searchLike", vo);
    }
    public int likeItem(CartVO vo) {
        return sqlSession.insert(MAPPER + ".likeItem", vo);
    }
    public int updateCartQuantity(CartVO vo) {return sqlSession.update(MAPPER + ".updateCartQuantity", vo);}
    public int getItemPrice(int i_idx) {return sqlSession.selectOne(MAPPER + ".getItemPrice", i_idx);}
    public List<CartVO> getCartListTotalPrice(int b_idx) {return sqlSession.selectList(MAPPER + ".getCartListTotalPrice", b_idx);}
    public List<CartVO> getCartsByBuyerId(int b_idx) {return sqlSession.selectList(MAPPER + ".getCartsByBuyerId",b_idx);}

    }