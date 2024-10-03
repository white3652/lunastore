package com.lunastore.mapper;

import com.lunastore.vo.AddressVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<AddressVO> getAddressesByBuyerId(int b_idx);
}
