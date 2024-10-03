package com.lunastore.service;

import com.lunastore.dao.AddressDAO;
import com.lunastore.mapper.AddressMapper;
import com.lunastore.vo.AddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDAO addressDAO;
    private final AddressMapper addressMapper;
    @Override
    public List<AddressVO> getAddressesByBuyerId(int b_idx) throws SQLException {
        return addressMapper.getAddressesByBuyerId(b_idx);
    }
}