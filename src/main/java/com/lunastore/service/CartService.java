package com.lunastore.service;

import java.util.List;

import com.lunastore.vo.CartVO;

public interface CartService {
    int insertCart(CartVO cartVO);
    List<CartVO> cartList(int b_idx);
    int deleteCart(CartVO cartVO);
    int likeItem(CartVO cartVO);
    int updateCartQuantity(CartVO cartVO);
    boolean updateCartQuantity(int i_idx, int i_count);
    int calculateTotalPrice(int i_idx, int i_count);
    int deleteCompleteCart(CartVO cartVO);
}