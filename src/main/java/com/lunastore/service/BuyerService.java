package com.lunastore.service;

import jakarta.servlet.http.HttpServletResponse;
import com.lunastore.vo.AddressVO;
import com.lunastore.vo.BuyerVO;

import java.sql.SQLException;
import java.util.Locale;


public interface BuyerService {
    int join(BuyerVO buyerVO);
    int infoState(BuyerVO buyerVO);
    BuyerVO login(String b_email, String b_pw);
    BuyerVO update(BuyerVO buyerVO);
    AddressVO getAddress(int b_idx);
    int insertAddress(AddressVO addressVO);
    AddressVO insertContact(AddressVO addressVO);
    int cancel(int b_idx);
    int nicknameCheck(String b_nickname);
    int emailCheck(String b_email);
    int telCheck(String b_tel);
    void sendEmail(BuyerVO buyerVO, String div, Locale locale) throws Exception;
    public String findPw(BuyerVO vo, Locale locale) throws Exception;
    int passwordCheck(int b_idx, String b_pw);
    String joinEmail(String b_email, Locale locale);
    int verifyEmail(String b_email);
    BuyerVO getBuyerById(int b_idx) throws SQLException;
    BuyerVO updateBuyerInfo(BuyerVO buyerVO) throws SQLException;
}