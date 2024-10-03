package com.lunastore.mapper;

import com.lunastore.vo.BuyerVO;
import com.lunastore.vo.AddressVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface BuyerMapper {
    void join(BuyerVO buyer);
    void info(int b_idx);
    String getPassword(String b_email);
    BuyerVO login(String b_email);
    void lastLoginDate(int b_idx);
    void updateBuyer(BuyerVO buyer);
    BuyerVO getBuyer(int b_idx);
    List<AddressVO> getAddress(int b_idx);
    void changeDefaultAddress(int b_idx);
    void insertAddress(AddressVO address);
    void insertContact(AddressVO address);
    void infoUpdate(BuyerVO buyer);
    void cancel(int b_idx);
    int nicknameCheck(String b_nickname);
    int emailCheck(String b_email);
    int telCheck(String b_tel);
    void updatePassword(BuyerVO buyer);
    int matchTelEmail(BuyerVO buyer);
    String getPw(int b_idx);
    void verifyEmail(int b_idx);
    int getB_idx(String b_email);
    // 추가된 메서드들
    void updateBuyerName(Map<String, Object> params);
    void updateBuyerAddress(Map<String, Object> params);
    void updateBuyerTel(Map<String, Object> params);
    void updateBuyerAddressContact(Map<String, Object> params);
    void updateBuyerOrderContact(Map<String, Object> params);
}