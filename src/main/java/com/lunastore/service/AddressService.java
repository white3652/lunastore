package com.lunastore.service;

import com.lunastore.vo.AddressVO;

import java.sql.SQLException;
import java.util.List;

public interface AddressService {
    List<AddressVO> getAddressesByBuyerId(int b_idx) throws SQLException;
}