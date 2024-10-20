package com.lunastore.service;


import com.lunastore.dto.StoreSalesDTO;
import com.lunastore.vo.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;

public interface AdminService {
    AdminVO login(String a_id, String a_pw) throws SQLException;
    AdminVO adminFindById(AdminVO adminVO) throws SQLException;
    List<BuyerVO> searchBuyer(SearchVO searchVO) throws SQLException;
    List<SellerVO> searchSeller(SearchVO searchVO) throws SQLException;
    List<ItemVO> searchItem(SearchVO searchVO) throws SQLException;
    int totalList(SearchVO searchVO) throws SQLException;
    BuyerVO findByBuyer(int b_idx) throws SQLException;
    SellerVO findBySeller(int s_idx) throws SQLException;
    BuyerVO updateBuyer(BuyerVO buyerVO) throws SQLException;
    SellerVO updateSeller(SellerVO sellerVO) throws SQLException;
    int cancelB(int b_idx) throws SQLException;
    int cancelS(int s_idx) throws SQLException;
    List<String> allBuyerMail(BuyerVO buyerVO) throws SQLException;
    List<BuyerVO> totalBuyer(BuyerVO buyerVO) throws SQLException;
    int itemTotalList(SearchVO searchVO) throws SQLException;
    AddressVO getBAddress(int b_idx) throws SQLException;
    AddressVO getSAddress(int s_idx) throws SQLException;
    void updateAddress(AddressVO addressVO) throws SQLException;
    void uploadProfileImage(int b_idx, MultipartFile file) throws SQLException;
    boolean sendMail(String to, String subject, String text) throws Exception;
    AdminBuyerDetailsVO getAdminBuyerDetails(int b_idx) throws SQLException;
    AdminBuyerDetailsVO updateAdminBuyer(AdminBuyerDetailsVO adminBuyerDetailsVO) throws SQLException;
    List<SellerVO> getAllSellers() throws SQLException;
    int getTotalSalesAmount();
    List<StoreSalesDTO> getStoreSalesDetails();
    AdminBuyerDetailsVO findAdminBuyerDetails(int b_idx);
}