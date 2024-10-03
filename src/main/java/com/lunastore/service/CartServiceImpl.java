package com.lunastore.service;


import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.lunastore.dao.CartDAO;
import com.lunastore.vo.CartVO;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartDAO dao;

    @Override
    public int insertCart(CartVO cartVO) {
        if (dao.searchCart(cartVO) != 1) {
            return dao.insertCart(cartVO);
        }
        return 0;
    }

    @Override
    public List<CartVO> cartList(int b_idx) {
        return dao.getCarts(b_idx);
    }

    @Override
    public int deleteCart(CartVO cartVO) {
        return dao.deleteCart(cartVO);
    }
    @Override
    public int deleteCompleteCart(CartVO cartVO) {
        return dao.deleteCompleteCart(cartVO);
    }

    @Override
    public int likeItem(CartVO cartVO) {
        if (dao.searchLike(cartVO) != 1) {
            return dao.likeItem(cartVO);
        }
        return 0;
    }
    public int updateCartQuantity(CartVO cartVO) {
        return dao.updateCartQuantity(cartVO);
    }


    @Override
    public boolean updateCartQuantity(int i_idx, int i_count) {
        CartVO cartVO = new CartVO();
        cartVO.setI_idx(i_idx);
        cartVO.setI_count(i_count);
        return dao.updateCartQuantity(cartVO) > 0;
    }

    @Override
    public int calculateTotalPrice(int i_idx, int i_count) {
        int itemPrice = dao.getItemPrice(i_idx);
        return itemPrice * i_count;
    }

}