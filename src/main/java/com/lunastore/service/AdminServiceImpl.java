package com.lunastore.service;

import com.lunastore.dao.AdminDAO;
import com.lunastore.dto.StoreSalesDTO;
import com.lunastore.vo.*;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminDAO dao;
    private final JavaMailSenderImpl mailSender;
    private final GlobalService globalService;

    @Override
    public AdminVO login(String a_id, String a_pw) throws SQLException {
        return dao.adminlogin(a_id, a_pw);
    }

    @Override
    public AdminVO adminFindById(AdminVO adminVO) throws SQLException {
        return dao.adminFindById(adminVO);
    }

    @Override
    public List<BuyerVO> searchBuyer(SearchVO searchVO) throws SQLException {
        return dao.getSearchBuyer(searchVO);
    }

    @Override
    public List<SellerVO> searchSeller(SearchVO searchVO) throws SQLException {
        return dao.getSearchSeller(searchVO);
    }

    @Override
    public List<ItemVO> searchItem(SearchVO searchVO) throws SQLException {
        return dao.getSearchItem(searchVO);
    }

    @Override
    public int totalList(SearchVO searchVO) throws SQLException {
        return dao.getTotalList(searchVO);
    }

    @Override
    public BuyerVO findByBuyer(int b_idx) throws SQLException {
        return dao.findByBuyer(b_idx);
    }

    @Override
    public SellerVO findBySeller(int s_idx) throws SQLException {
        return dao.findBySeller(s_idx);
    }

    @Override
    @Transactional
    public BuyerVO updateBuyer(BuyerVO buyerVO) throws SQLException {
        BuyerVO existingBuyer = dao.findByBuyer(buyerVO.getB_idx());
        if (existingBuyer == null) {
            throw new SQLException("Buyer not found with b_idx: " + buyerVO.getB_idx());
        }

        BuyerVO updatedBuyer = dao.updateBuyer(buyerVO);
        if (updatedBuyer != null) {
            return updatedBuyer;
        } else {
            throw new SQLException("Failed to update Buyer with b_idx: " + buyerVO.getB_idx());
        }
    }

    @Override
    @Transactional
    public SellerVO updateSeller(SellerVO sellerVO) throws SQLException {
        SellerVO existingSeller = dao.findBySeller(sellerVO.getS_idx());
        if (existingSeller == null) {
            throw new SQLException("Seller not found with s_idx: " + sellerVO.getS_idx());
        }

        if (sellerVO.getS_pw() != null) existingSeller.setS_pw(sellerVO.getS_pw());
        if (sellerVO.getS_tel() != null) existingSeller.setS_tel(sellerVO.getS_tel());
        if (sellerVO.getS_birth() != null) existingSeller.setS_birth(sellerVO.getS_birth());
        if (sellerVO.getS_email() != null) existingSeller.setS_email(sellerVO.getS_email());
        if (sellerVO.getS_address() != null) existingSeller.setS_address(sellerVO.getS_address());
        if (sellerVO.getS_restaddress() != null) existingSeller.setS_restaddress(sellerVO.getS_restaddress());
        if (sellerVO.getS_zipcode() != null) existingSeller.setS_zipcode(sellerVO.getS_zipcode());
        if (sellerVO.getS_storename() != null) existingSeller.setS_storename(sellerVO.getS_storename());
        if (sellerVO.getS_storeintro() != null) existingSeller.setS_storeintro(sellerVO.getS_storeintro());
        if (sellerVO.getS_memo() != null) existingSeller.setS_memo(sellerVO.getS_memo());
        if (sellerVO.getS_cancel() != null) existingSeller.setS_cancel(sellerVO.getS_cancel());
        if (sellerVO.getS_check() != null) existingSeller.setS_check(sellerVO.getS_check());
        if (sellerVO.getS_terms() != null) existingSeller.setS_terms(sellerVO.getS_terms());

        SellerVO updatedSeller = dao.updateSeller(existingSeller);
        if (updatedSeller != null) {
            return updatedSeller;
        } else {
            throw new SQLException("Failed to update Seller with s_idx: " + sellerVO.getS_idx());
        }
    }

    @Override
    public int cancelB(int b_idx) throws SQLException {
        return dao.cancelB(b_idx);
    }

    @Override
    public int cancelS(int s_idx) throws SQLException {
        return dao.cancelS(s_idx);
    }


    @Override
    public boolean sendMail(String to, String subject, String text) throws Exception {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom("white3652@naver.com");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AddressVO getBAddress(int b_idx) throws SQLException {
        return dao.getBAddress(b_idx);
    }

    @Override
    public AddressVO getSAddress(int s_idx) throws SQLException {
        return dao.getSAddress(s_idx);
    }

    @Override
    public List<String> allBuyerMail(BuyerVO buyerVO) throws SQLException {
        return dao.getAllBuyerMail(buyerVO);
    }

    @Override
    public List<BuyerVO> totalBuyer(BuyerVO buyerVO) throws SQLException {
        buyerVO.setB_check("Y");
        return dao.getTotalBuyer(buyerVO);
    }

    @Override
    public int itemTotalList(SearchVO searchVO) throws SQLException {
        return dao.getItemTotalList(searchVO);
    }

    @Override
    @Transactional
    public void updateAddress(AddressVO addressVO) throws SQLException {
        int updateCount = dao.updateBuyerAddress(addressVO);
        if (updateCount <= 0) {
            throw new SQLException("Failed to update Address with b_idx: " + addressVO.getB_idx());
        }
    }

    @Override
    @Transactional
    public void uploadProfileImage(int b_idx, MultipartFile file) throws SQLException {

        if (file.isEmpty()) {
            return;
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = b_idx + "_" + System.currentTimeMillis() + fileExtension;

        String uploadDir = "uploads/profile/";
        Path path = Paths.get(uploadDir + newFilename);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            log.error("Error saving profile image for b_idx: {}", b_idx, e);
            throw new SQLException("Failed to save profile image", e);
        }

        BuyerVO buyer = dao.findByBuyer(b_idx);
        if (buyer != null) {
            buyer.setB_profile(newFilename);
            dao.updateBuyerProfile(buyer);
        }
    }

    @Override
    public AdminBuyerDetailsVO getAdminBuyerDetails(int b_idx) throws SQLException {

        AdminBuyerDetailsVO buyerDetails = dao.getAdminBuyerDetails(b_idx);
        if (buyerDetails == null) {
            throw new SQLException("Buyer not found with b_idx: " + b_idx);
        }

        buyerDetails.setB_lastlogindate(globalService.dateUpdate(
                buyerDetails.getB_lastlogindate() != null ? buyerDetails.getB_lastlogindate() : new Date()
        ));
        buyerDetails.setB_pwmodifydate(globalService.dateUpdate(buyerDetails.getB_pwmodifydate()));
        buyerDetails.setB_modifydate(globalService.dateUpdate(buyerDetails.getB_modifydate()));
        buyerDetails.setB_regdate(globalService.dateUpdate(buyerDetails.getB_regdate()));

        return buyerDetails;
    }

    @Override
    @Transactional
    public AdminBuyerDetailsVO updateAdminBuyer(AdminBuyerDetailsVO adminBuyerDetailsVO) throws SQLException {

        AdminBuyerDetailsVO existingAdminBuyer = dao.findByAdminBuyer(adminBuyerDetailsVO.getB_idx());
        if (existingAdminBuyer == null) {
            throw new SQLException("AdminBuyer not found with b_idx: " + adminBuyerDetailsVO.getB_idx());
        }

        // 전달된 값이 null이 아닐 때만 기존 값을 수정 (병합)
        if (adminBuyerDetailsVO.getB_firstname() != null) {
            existingAdminBuyer.setB_firstname(adminBuyerDetailsVO.getB_firstname());
        }
        if (adminBuyerDetailsVO.getB_lastname() != null) {
            existingAdminBuyer.setB_lastname(adminBuyerDetailsVO.getB_lastname());
        }
        if (adminBuyerDetailsVO.getB_tel() != null) {
            existingAdminBuyer.setB_tel(adminBuyerDetailsVO.getB_tel());
        }
        if (adminBuyerDetailsVO.getB_nickname() != null) {
            existingAdminBuyer.setB_nickname(adminBuyerDetailsVO.getB_nickname());
        }
        if (adminBuyerDetailsVO.getB_email() != null) {
            existingAdminBuyer.setB_email(adminBuyerDetailsVO.getB_email());
        }
        if (adminBuyerDetailsVO.getB_birth() != null) {
            existingAdminBuyer.setB_birth(adminBuyerDetailsVO.getB_birth());
        }
        if (adminBuyerDetailsVO.getB_pw() != null && !adminBuyerDetailsVO.getB_pw().isEmpty()) {
            existingAdminBuyer.setB_pw(adminBuyerDetailsVO.getB_pw());
        }
        if (adminBuyerDetailsVO.getB_cancel() != null) {
            existingAdminBuyer.setB_cancel(adminBuyerDetailsVO.getB_cancel());
        }
        if (adminBuyerDetailsVO.getB_check() != null) {
            existingAdminBuyer.setB_check(adminBuyerDetailsVO.getB_check());
        }
        if (adminBuyerDetailsVO.getB_terms() != null) {
            existingAdminBuyer.setB_terms(adminBuyerDetailsVO.getB_terms());
        }
        if (adminBuyerDetailsVO.getB_grade() != null) {
            existingAdminBuyer.setB_grade(adminBuyerDetailsVO.getB_grade());
        }
        if (adminBuyerDetailsVO.getB_profile() != null) {
            existingAdminBuyer.setB_profile(adminBuyerDetailsVO.getB_profile());
        }
        if (adminBuyerDetailsVO.getB_gender() != null) {
            existingAdminBuyer.setB_gender(adminBuyerDetailsVO.getB_gender());
        }
        // 업데이트할 때 기존 객체인 existingAdminBuyer 사용
        AdminBuyerDetailsVO updatedAdminBuyer = dao.updateAdminBuyer(existingAdminBuyer);
        if (updatedAdminBuyer != null) {
            return updatedAdminBuyer;
        } else {
            throw new SQLException("Failed to update AdminBuyer with b_idx: " + adminBuyerDetailsVO.getB_idx());
        }
    }
    @Override
    public List<SellerVO> getAllSellers() throws SQLException {
        return dao.getAllSellers();
    }
    @Override
    public int getTotalSalesAmount() {
        return dao.getTotalSalesAmount();
    }
    @Override
    public List<StoreSalesDTO> getStoreSalesDetails() {
        return dao.getStoreSalesDetails();
    }

    @Override
    public AdminBuyerDetailsVO findAdminBuyerDetails(int b_idx) {
        try {
            return dao.findAdminBuyerDetails(b_idx);
        } catch (Exception e) {
            return null;
        }
    }
}