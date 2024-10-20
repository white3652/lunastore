package com.lunastore.dao;

import java.sql.SQLException;
import java.util.List;

import com.lunastore.dto.StoreSalesDTO;
import com.lunastore.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminDAO {

    public static final String MAPPER = "com.lunastore.mapper.AdminMapper";

    private final SqlSession sqlSession;

    // 로그인
    public AdminVO adminlogin(String a_id, String a_pw) throws SQLException {
        AdminVO admin = new AdminVO();
        admin.setA_id(a_id);
        admin.setA_pw(a_pw);
        return sqlSession.selectOne(MAPPER + ".adminlogin", admin);
    }

    public AdminVO adminFindById(AdminVO adminVO) throws SQLException {
        try {
            return sqlSession.selectOne(MAPPER + ".adminFindById", adminVO);
        } catch (Exception e) {
            log.error("Error in adminFindById with AdminVO: {}", adminVO, e);
            throw new SQLException("Error finding Admin by ID", e);
        }
    }

    public List<BuyerVO> getTotalBuyer(BuyerVO vo) {
        return sqlSession.selectList(MAPPER + ".getTotalBuyer", vo);
    }

    // 검색에 해당하는 회원 가져오기
    public List<BuyerVO> getSearchBuyer(SearchVO vo) {
        try {
            return sqlSession.selectList(MAPPER + ".getSearchBuyer", vo);
        } catch (Exception e) {
            log.error("Error in getSearchBuyer with SearchVO: {}", vo, e);
            return null;
        }
    }

    // 검색에 해당하는 회원의 수
    public int getTotalList(SearchVO searchVO) {
        try {
            return sqlSession.selectOne(MAPPER + ".getTotalList", searchVO);
        } catch (Exception e) {
            log.error("Error in getTotalList with SearchVO: {}", searchVO, e);
        }
        return 0;
    }

    public List<SellerVO> getSearchSeller(SearchVO searchVO) {
        List<SellerVO> sql = null;
        try {
            sql = sqlSession.selectList(MAPPER + ".getSearchSeller", searchVO);
        } catch (Exception e) {
            log.error("Error in getSearchSeller with SearchVO: {}", searchVO, e);
        }
        return sql;
    }

    public List<ItemVO> getSearchItem(SearchVO searchVO) {
        try {
            return sqlSession.selectList(MAPPER + ".getSearchItem", searchVO);
        } catch (Exception e) {
            log.error("Error in getSearchItem with SearchVO: {}", searchVO, e);
            return null;
        }
    }

    public int getItemTotal(SearchVO searchVO) {
        try {
            return sqlSession.selectOne(MAPPER + ".getItemTotal", searchVO);
        } catch (Exception e) {
            log.error("Error in getItemTotal with SearchVO: {}", searchVO, e);
            return 0;
        }
    }

    public SellerVO findBySeller(int s_idx) {
        try {
            return sqlSession.selectOne(MAPPER + ".findBySeller", s_idx);
        } catch (Exception e) {
            log.error("Error in findBySeller with s_idx: {}", s_idx, e);
            return null;
        }
    }

    public BuyerVO findByBuyer(int b_idx) {
        try {
            return sqlSession.selectOne(MAPPER + ".findByBuyer", b_idx);
        } catch (Exception e) {
            log.error("Error in findByBuyer with b_idx: {}", b_idx, e);
            return null;
        }
    }

    public BuyerVO updateBuyer(BuyerVO buyerVO) throws SQLException {
        try {
            // 기본 정보 업데이트
            int buyerBasicUpdate = sqlSession.update(MAPPER + ".updateBuyerBasicInfo", buyerVO);
            log.info("updateBuyerBasicInfo affected rows: {}", buyerBasicUpdate);

            // 상세 정보 업데이트
            int buyerInfoUpdate = sqlSession.update(MAPPER + ".updateBuyerInfo", buyerVO);
            log.info("updateBuyerInfo affected rows: {}", buyerInfoUpdate);

            // 주소 업데이트
            AddressVO address = buyerVO.getAddress();
            int buyerAddressUpdate = 0;
            if (address != null) {
                buyerAddressUpdate = sqlSession.update(MAPPER + ".updateBuyerAddress", address);
                log.info("updateBuyerAddress affected rows: {}", buyerAddressUpdate);
            }

            if (buyerBasicUpdate > 0 || buyerInfoUpdate > 0 || buyerAddressUpdate > 0) {
                return sqlSession.selectOne(MAPPER + ".findByBuyer", buyerVO.getB_idx());
            }
        } catch (Exception e) {
            log.error("Error in updateBuyer with BuyerVO: {}", buyerVO, e);
            throw new SQLException("Failed to update Buyer", e);
        }
        return null;
    }

    public SellerVO updateSeller(SellerVO sellerVO) throws SQLException {
        try {
            // 기본 정보 업데이트
            int sellerBasicUpdate = sqlSession.update(MAPPER + ".updateSellerBasicInfo", sellerVO);
            log.info("updateSellerBasicInfo affected rows: {}", sellerBasicUpdate);

            // 상세 정보 업데이트
            int sellerInfoUpdate = sqlSession.update(MAPPER + ".updateSellerInfo", sellerVO);
            log.info("updateSellerInfo affected rows: {}", sellerInfoUpdate);

            if (sellerBasicUpdate > 0 || sellerInfoUpdate > 0) {
                return sqlSession.selectOne(MAPPER + ".findBySeller", sellerVO.getS_idx());
            }
        } catch (Exception e) {
            log.error("Error in updateSeller with SellerVO: {}", sellerVO, e);
            throw new SQLException("Failed to update Seller", e);
        }
        return null;
    }

    public int cancelB(int b_idx) throws SQLException {
        try {
            return sqlSession.update(MAPPER + ".cancelB", b_idx);
        } catch (Exception e) {
            log.error("Error in cancelB with b_idx: {}", b_idx, e);
            throw new SQLException("Failed to cancel Buyer", e);
        }
    }

    public int cancelS(int s_idx) throws SQLException {
        try {
            return sqlSession.update(MAPPER + ".cancelS", s_idx);
        } catch (Exception e) {
            log.error("Error in cancelS with s_idx: {}", s_idx, e);
            throw new SQLException("Failed to cancel Seller", e);
        }
    }

    public List<String> getAllBuyerMail(BuyerVO buyerVO) {
        try {
            return sqlSession.selectList(MAPPER + ".getAllBuyerMail", buyerVO);
        } catch (Exception e) {
            log.error("Error in getAllBuyerMail with BuyerVO: {}", buyerVO, e);
            return null;
        }
    }

    public List<BuyerVO> getAllBuyers() {
        try {
            return sqlSession.selectList(MAPPER + ".getAllBuyers");
        } catch (Exception e) {
            log.error("Error in getAllBuyers", e);
            return null;
        }
    }

    // Update Buyer Address
    public int updateBuyerAddress(AddressVO addressVO) throws SQLException {
        try {
            return sqlSession.update(MAPPER + ".updateBuyerAddress", addressVO);
        } catch (Exception e) {
            log.error("Error in updateBuyerAddress with AddressVO: {}", addressVO, e);
            throw new SQLException("Failed to update Buyer Address", e);
        }
    }

    // Update Buyer Profile
    public int updateBuyerProfile(BuyerVO buyerVO) throws SQLException {
        try {
            return sqlSession.update(MAPPER + ".updateBuyerProfile", buyerVO);
        } catch (Exception e) {
            log.error("Error in updateBuyerProfile with BuyerVO: {}", buyerVO, e);
            throw new SQLException("Failed to update Buyer Profile", e);
        }
    }

    public AddressVO getBAddress(int b_idx) {
        return sqlSession.selectOne(MAPPER + ".getBAddress", b_idx);
    }

    public AddressVO getSAddress(int s_idx) {
        return sqlSession.selectOne(MAPPER + ".getSAddress", s_idx);
    }

    public int getItemTotalList(SearchVO vo) {
        return sqlSession.selectOne(MAPPER + ".getItemTotal", vo);
    }

    public AdminBuyerDetailsVO getAdminBuyerDetails(int b_idx) throws SQLException {
        try {
            return sqlSession.selectOne(MAPPER + ".getAdminBuyerDetails", b_idx);
        } catch (Exception e) {
            log.error("Error in getAdminBuyerDetails with b_idx: {}", b_idx, e);
            throw new SQLException("Error getting AdminBuyerDetails", e);
        }
    }
    public AdminBuyerDetailsVO findByAdminBuyer(int b_idx) {
        try {
            return sqlSession.selectOne(MAPPER + ".findByAdminBuyer", b_idx);
        } catch (Exception e) {
            log.error("Error in findByAdminBuyer with b_idx: {}", b_idx, e);
            return null;
        }
    }

    public AdminBuyerDetailsVO updateAdminBuyer(AdminBuyerDetailsVO adminBuyerDetailsVO) throws SQLException {
        try {
            // 기본 정보 업데이트
            int adminBuyerBasicUpdate = sqlSession.update(MAPPER + ".updateAdminBuyerBasicInfo", adminBuyerDetailsVO);
            log.info("updateAdminBuyerBasicInfo affected rows: {}", adminBuyerBasicUpdate);

            // 주소 업데이트
            AddressVO address = adminBuyerDetailsVO.getAddress();
            if (address != null) {
                address.setB_idx(adminBuyerDetailsVO.getB_idx()); // b_idx 설정
                int adminBuyerAddressUpdate = sqlSession.update(MAPPER + ".updateAdminBuyerAddress", address);
                log.info("updateAdminBuyerAddress affected rows: {}", adminBuyerAddressUpdate);
            }

            if (adminBuyerBasicUpdate > 0 ) {
                return sqlSession.selectOne(MAPPER + ".findByAdminBuyer", adminBuyerDetailsVO.getB_idx());
            }
        } catch (Exception e) {
            log.error("Error in updateAdminBuyer with AdminBuyerDetailsVO: {}", adminBuyerDetailsVO, e);
            throw new SQLException("Failed to update AdminBuyer", e);
        }
        return null;
    }

    public List<SellerVO> getAllSellers() throws SQLException {
        return sqlSession.selectList(MAPPER + ".getAllSellers");
    }
    public int getTotalSalesAmount() {
        return sqlSession.selectOne(MAPPER + ".getTotalSalesAmount");
    }
    public List<StoreSalesDTO> getStoreSalesDetails() {
        return sqlSession.selectList(MAPPER + ".getStoreSalesDetails");
    }
    public AdminBuyerDetailsVO findAdminBuyerDetails(int b_idx) throws SQLException {
        return sqlSession.selectOne(MAPPER + ".findByAdminBuyer", b_idx);
    }
}
