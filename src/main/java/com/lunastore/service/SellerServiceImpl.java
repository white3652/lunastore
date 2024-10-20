package com.lunastore.service;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import com.lunastore.dao.OrderDAO;
import com.lunastore.dto.DailySalesDTO;
import com.lunastore.dto.PaymentDTO;
import com.lunastore.dto.StoreStatusDTO;
import com.lunastore.vo.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.lunastore.dao.SellerDAO;
import com.lunastore.common.PageNav;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerDAO dao;
    private final OrderDAO orderDAO;
    private final JavaMailSenderImpl mailSender;

    @Override
    public int join(SellerVO sellerVO) {
        int result = 0;
        try {
            String s_pw = sellerVO.getS_pw();
            sellerVO.setS_pw(s_pw);
            result = dao.join(sellerVO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int infoState(SellerVO sellerVO) {
        int result = 0;
        try {
            if (dao.info(sellerVO.getS_idx()) == 1 && dao.state(sellerVO.getS_idx()) == 1) {
                result = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public SellerVO login(String s_businessnum, String s_pw) {
        SellerVO sellerVO = null;
        try {
            String storedPassword = dao.getPassword(s_businessnum);
            if (storedPassword != null && storedPassword.equals(s_pw)) {
                sellerVO = dao.login(s_businessnum);
                if (sellerVO != null && sellerVO.getS_idx() != 0) {
                    try {
                        dao.lastLoginDate(sellerVO.getS_idx());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerVO;
    }

    @Override
    public SellerVO update(SellerVO sellerVO) {
        SellerVO newVO = null;
        try {
            newVO = dao.update(sellerVO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newVO;
    }

    @Override
    public int cancel(int s_idx) {
        int result = 0;
        try {
            result = dao.cancel(s_idx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int storenameCheck(String s_storename) {
        int result = 0;
        try {
            result = dao.storenameCheck(s_storename);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int telCheck(String s_tel) {
        int result = 0;
        try {
            result = dao.telCheck(s_tel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int businessnumCheck(String s_businessnum) {
        int result = 0;
        try {
            result = dao.businessnumCheck(s_businessnum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int emailCheck(String s_email) {
        int result = 0;
        try {
            result = dao.emailCheck(s_email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void sendEmail(SellerVO vo, String div) throws Exception {
        String fromEmail = "white3652@naver.com";
        String toEmail = vo.getS_email();
        String subject = "";
        String msg = "";

        if ("findpw".equals(div)) {
            subject = "[Luna] 임시 비밀번호 발급 메일입니다.";
            msg += "임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.";
            msg += "임시 비밀번호: ";
            msg += vo.getS_pw();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(fromEmail);
        message.setSubject(subject);
        message.setText(msg);

        try {
            mailSender.send(message);
            dao.updatePassword(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findPw(HttpServletResponse response, SellerVO vo) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        if (dao.matchBusinessnumEmail(vo) == 1) {
            String newS_pw = generateRandomPassword();
            vo.setS_pw(newS_pw);
            dao.updatePassword(vo);
            sendEmail(vo, "findpw");
            out.print("이메일로 임시 비밀번호를 발송하였습니다.");
            out.close();
        } else {
            out.print("전화번호와 이메일이 일치하지 않습니다.");
            out.close();
        }
    }

    private static String generateRandomPassword() {
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#";

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            password.append(lowerCaseChars.charAt(random.nextInt(lowerCaseChars.length())));
        }

        for (int i = 0; i < 4; i++) {
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        return password.toString();
    }

    @Override
    public int passwordCheck(int s_idx, String s_pw) {
        int result = 0;
        try {
            String storedPassword = dao.getPw(s_idx);
            if (storedPassword.equals(s_pw)) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String joinEmail(String s_email) {
        int authNumber = (int) (Math.random() * 888889) + 111111;
        String setFrom = "white3652@naver.com";
        String toMail = s_email;
        String title = "[root] 회원가입 인증 메일입니다.";
        String content = "홈페이지를 방문해주셔서 감사합니다.<br><br>" + "인증번호: " + authNumber + "<br>" + "해당 인증번호를 인증번호 확인란에 입력해 주세요.";
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    private void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int verifyEmail(String s_email) {
        return dao.verifyEmail(dao.getS_idx(s_email));
    }

    @Override
    public List<ItemVO> getItem(int s_idx) {
        return dao.getItem(s_idx);
    }

    @Override
    public List<ItemVO> getItems(SearchVO vo) {
        vo.setStartIdx((vo.getPageNum() - 1) * 6);
        return dao.getItems(vo);
    }

    @Override
    public List<OrderStateVO> getOrder(int s_idx) {
        return dao.getOrder(s_idx);
    }

    @Override
    public List<OrderStateVO> getOrders(SearchVO vo) {
        vo.setStartIdx((vo.getPageNum() - 1) * 6);
        return dao.getOrders(vo);
    }

    @Override
    public List<ReviewVO> getReviews(SearchVO vo) {
        vo.setStartIdx((vo.getPageNum() - 1) * 6);
        return dao.getReviews(vo);
    }

    @Override
    public int getItemTotalCount(SearchVO vo) {
        return dao.getItemTotalCount(vo);
    }

    @Override
    public int getOrderTotalCount(SearchVO vo) {
        return dao.getOrderTotalCount(vo);
    }

    @Override
    public int getReviewTotalCount(SearchVO vo) {
        return dao.getReviewTotalCount(vo);
    }

    @Override
    public PageNav setPageNav(PageNav pageNav, int pageNum, int pageBlock) {
        int totalRows = pageNav.getTotalRows();
        int rows_page = 6;
        int pages_pageBlock = 5;
        pageNav.setRows_page(rows_page);
        pageNav.setPages_pageBlock(pages_pageBlock);
        int pNum = pageNum == 0 ? 1 : pageNum;
        pageNav.setPageNum(pNum);
        int pBlock = pageBlock == 0 ? 1 : pageBlock;
        pageNav.setPageBlock(pBlock);
        int startNum = (pNum - 1) * rows_page + 1;
        int endNum = pNum * rows_page;
        int total_pageNum = (int) Math.ceil((double) totalRows / rows_page);
        int last_pageBlock = (int) Math.ceil((double) total_pageNum / pages_pageBlock);
        pageNav.setStartNum(startNum);
        pageNav.setEndNum(endNum);
        pageNav.setTotal_pageNum(total_pageNum);
        pageNav.setLast_pageBlock(last_pageBlock);
        return pageNav;
    }

    @Override
    public int updateOrderState(OrderStateVO orderStateVO) {
        return dao.updateOrderState(orderStateVO);
    }

    @Override
    public int updateItemState(ItemVO itemVO) {
        return dao.updateItemState(itemVO);
    }

    @Override
    public SellerVO getSellerBySIdx(int s_idx) {
        return dao.getSellerBySIdx(s_idx);
    }
    @Override
    public SellerVO infoUpdate(SellerVO sellerVO) {
        SellerVO updatedSeller = null;
        try {
            updatedSeller = dao.infoUpdate(sellerVO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedSeller;
    }

    @Override
    public SellerVO getSellerWithStoreInfo(int s_idx) {
        return dao.getSellerWithStoreInfo(s_idx);
    }

@Override
public List<ReviewVO> getReviewsBySellerId(int s_idx) {
    return dao.getReviewsBySellerId(s_idx);
}

    @Override
    public List<QnaVO> getInquiriesBySellerId(int s_idx) {
        return dao.getInquiriesBySellerId(s_idx);
    }
    @Override
    public List<ItemVO> getItemsWithCounts(int s_idx) {
        return dao.getItemsWithCounts(s_idx);
    }

    @Override
    public int getNewOrderCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getNewOrderCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getPreparingShipmentCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getPreparingShipmentCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getShippingCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getShippingCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getDeliveredCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getDeliveredCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getRefundRequestCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getRefundRequestCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getReturnRequestCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getReturnRequestCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getExchangeRequestCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getExchangeRequestCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getTodaySettlement(int sellerId) {
        int result = 0;
        try {
            result = dao.getTodaySettlement(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getUpcomingSettlement(int sellerId) {
        int result = 0;
        try {
            result = dao.getUpcomingSettlement(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getConfirmedPurchaseCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getConfirmedPurchaseCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getPendingConfirmationCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getPendingConfirmationCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getStoreWishlistCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getStoreWishlistCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getItemWishlistCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getItemWishlistCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getModificationRequestCount(int sellerId) {
        int result = 0;
        try {
            result = dao.getModificationRequestCount(sellerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<DailySalesDTO> getDailySalesData(int s_idx) {
        return dao.getDailySalesData(s_idx);
    }


    @Override
    public List<ReviewVO> getReviewsByPage(int s_idx, int offset, int limit) {
        return dao.getReviewsByPage(s_idx, offset, limit);
    }

    @Override
    public int getTotalReviewCount(int s_idx) {
        return dao.getTotalReviewCount(s_idx);
    }

    @Override
    public List<StoreStatusDTO> getStoreStatusList(int s_idx, LocalDate startDate, LocalDate endDate,  int offset, int limit) {

        if (offset < 0) {
            offset = 0;
        }
        List<StoreStatusDTO> storeStatusList = dao.getStoreStatusList(s_idx, startDate, endDate, offset, limit);
        storeStatusList = storeStatusList.stream().map(dto -> {
            String formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(dto.getSalesAmount());
            dto.setFormattedSalesAmount(formatted);
            return dto;
        }).collect(Collectors.toList());

        return storeStatusList;
    }

    @Override
    public int getTotalStoreStatusCount(int s_idx, LocalDate startDate, LocalDate endDate) {
        return dao.getTotalStoreStatusCount(s_idx, startDate, endDate);
    }
}
