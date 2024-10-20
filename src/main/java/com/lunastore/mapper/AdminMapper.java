package com.lunastore.mapper;

import com.lunastore.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    AdminVO adminlogin(AdminVO adminVO);
    AdminVO adminFindById(String a_id);
    List<BuyerVO> getTotalBuyer(BuyerVO vo);
    List<BuyerVO> getSearchBuyer(SearchVO searchVO);
    int getTotalList(SearchVO searchVO);
    List<SellerVO> getSearchSeller(SearchVO searchVO);
    List<ItemVO> getSearchItem(SearchVO searchVO);
    int getItemTotal(SearchVO searchVO);
    SellerVO findBySeller(int s_idx);
    BuyerVO findByBuyer(int b_idx);
    AddressVO getBAddress(int b_idx);
    AddressVO getSAddress(int s_idx);
    void updateBuyer(BuyerVO buyerVO);
    BuyerVO getBuyer(int b_idx);
    void updateSeller(SellerVO sellerVO);
    SellerVO getSeller(int s_idx);
    List<String> getAllBuyerMail(BuyerVO vo);
    void cancelB(int b_idx);
    void cancelS(int s_idx);
    AdminBuyerDetailsVO getAdminBuyerDetails(int b_idx);
    AdminBuyerDetailsVO findAdminBuyerDetails(int b_idx);
}