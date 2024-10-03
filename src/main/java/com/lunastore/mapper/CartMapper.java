package com.lunastore.mapper;

import com.lunastore.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CartMapper {
    int searchCart(CartVO cart);

    void insertCart(CartVO cart);

    List<CartVO> getCarts(int b_idx);

    void deleteCart(CartVO cart);
    void deleteCompleteCart(CartVO cart);

    List<CartVO> getCartsByBuyerId(int b_idx);
}