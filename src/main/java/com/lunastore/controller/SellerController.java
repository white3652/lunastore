package com.lunastore.controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.lunastore.common.ListPageNav;
import com.lunastore.common.PageNav2;
import com.lunastore.common.ValidatedListPageNav;
import com.lunastore.dto.ReviewDetailsDTO;
import com.lunastore.dto.StoreStatusDTO;
import com.lunastore.service.*;
import com.lunastore.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.lunastore.common.PageNav;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Slf4j

@Controller
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    private final SellerService sellerService;
    private final GlobalService globalService;
    private final PageNav pageNav;
    private final QnaService qnaService;
    private final DeliveryService deliveryService;
    private final SettlementService settlementService;

    @GetMapping("/sellerJoin")
    public String sellerJoin() {
        return "seller/user/sellerJoin";
    }


    @PostMapping("/sellerJoinProcess")
    public String sellerJoinProcess(HttpServletRequest request, SellerVO sellerVO) {
        String viewPage = "seller/user/sellerJoin";

        if (sellerService.join(sellerVO) == 1) {
            if (sellerService.infoState(sellerVO) == 1) {
                String verificationCode = sellerService.joinEmail(sellerVO.getS_email());

                HttpSession session = request.getSession();
                session.setAttribute("s_email", sellerVO.getS_email());
                session.setAttribute("verificationCode", verificationCode);

                viewPage = "redirect:/";
            }
        }

        return viewPage;
    }

    @GetMapping("/sellerLogin")
    public String sellerLogin() {
        return "seller/user/sellerLogin";
    }

    @PostMapping("/sellerLoginProcess")
    public String sellerLoginProcess(String s_businessnum, String s_pw, HttpServletRequest request, Model model) {
        String viewPage = "seller/user/sellerLogin";

        SellerVO sellerVO = sellerService.login(s_businessnum, s_pw);

        if (sellerVO != null) {
            if (sellerVO.getS_lastlogindate() != null) {
                sellerVO.setS_lastlogindate(globalService.dateUpdate(sellerVO.getS_lastlogindate()));
            }
            if (sellerVO.getS_pwmodifydate() != null) {
                sellerVO.setS_pwmodifydate(globalService.dateUpdate(sellerVO.getS_pwmodifydate()));
            }
            if (sellerVO.getS_modifydate() != null) {
                sellerVO.setS_modifydate(globalService.dateUpdate(sellerVO.getS_modifydate()));
            }
            if (sellerVO.getS_regdate() != null) {
                sellerVO.setS_regdate(globalService.dateUpdate(sellerVO.getS_regdate()));
            }

            HttpSession session = request.getSession();
            session.setAttribute("seller", sellerVO);

            viewPage = "redirect:/seller/dashBoard";
        } else {
            model.addAttribute("loginError", true);
        }

        return viewPage;
    }

    @GetMapping("/sellerLogout")
    public String sellerLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/sellerUpdate/{s_idx}")
    public String sellerUpdate(@PathVariable("s_idx") int s_idx, Model model) {
        // Seller와 Store 정보 가져오기
        SellerVO seller = sellerService.getSellerWithStoreInfo(s_idx);

        String formattedBirth = "";
        if (seller != null && seller.getS_birth() != null) {
            try {
                Object birthObject = seller.getS_birth();

                if (birthObject instanceof Date) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    formattedBirth = dateFormat.format((Date) birthObject);
                } else if (birthObject instanceof String) {
                    formattedBirth = (String) birthObject;
                } else {
                    formattedBirth = birthObject.toString();
                }
            } catch (Exception e) {
                formattedBirth = "";
            }
        }

        model.addAttribute("formattedBirth", formattedBirth);
        model.addAttribute("seller", seller);

        return "seller/user/sellerUpdate";
    }

    @PostMapping("/sellerUpdateProcess")
    public String sellerUpdateProcess(SellerVO sellerVO, HttpServletRequest request) {
        String viewPage = "seller/user/sellerUpdate";

        SellerVO newVO = sellerService.update(sellerVO);

        if (newVO != null) {
            newVO.setS_lastlogindate(globalService.dateUpdate(newVO.getS_lastlogindate()));
            newVO.setS_pwmodifydate(globalService.dateUpdate(newVO.getS_pwmodifydate()));
            newVO.setS_modifydate(globalService.dateUpdate(newVO.getS_modifydate()));
            newVO.setS_regdate(globalService.dateUpdate(newVO.getS_regdate()));

            HttpSession session = request.getSession();
            session.removeAttribute("seller");
            session.setAttribute("seller", newVO);

            viewPage = "redirect:/seller/sellerUpdate/" + newVO.getS_idx();
        }

        return viewPage;
    }

    @PostMapping("/sellerInfoUpdateProcess")
    public String sellerInfoUpdateProcess(SellerVO sellerVO, HttpServletRequest request) throws SQLException {
        String viewPage = "seller/user/sellerUpdate";

        try {
            MultipartFile profileFile = sellerVO.getS_tempprofile();

            if (profileFile != null && !profileFile.isEmpty()) {
                String newProfile = globalService.fileNameUpdate(profileFile);
                sellerVO.setS_profile(newProfile);
            } else {
                SellerVO existingSeller = sellerService.getSellerWithStoreInfo(sellerVO.getS_idx());
                if (existingSeller != null) {
                    sellerVO.setS_profile(existingSeller.getS_profile());
                } else {
                    sellerVO.setS_profile("기본!@프로필.jpg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sellerVO.setS_profile("기본!@프로필.jpg");
        }

        SellerVO newVO = sellerService.infoUpdate(sellerVO);

        if (newVO != null) {
            newVO.setS_lastlogindate(globalService.dateUpdate(newVO.getS_lastlogindate()));
            newVO.setS_pwmodifydate(globalService.dateUpdate(newVO.getS_pwmodifydate()));
            newVO.setS_modifydate(globalService.dateUpdate(newVO.getS_modifydate()));
            newVO.setS_regdate(globalService.dateUpdate(newVO.getS_regdate()));

            HttpSession session = request.getSession();
            session.removeAttribute("seller");
            session.setAttribute("seller", newVO);

            viewPage = "redirect:/seller/sellerUpdate/" + newVO.getS_idx();
        }

        return viewPage;
    }



    @GetMapping("/sellerCancelProcess")
    public String sellerCancelProcess(HttpServletRequest request) {
        String viewPage = "seller/user/sellerUpdate";

        HttpSession session = request.getSession();
        SellerVO vo = (SellerVO) session.getAttribute("seller");
        int s_idx = vo.getS_idx();

        if (sellerService.cancel(s_idx) == 2) {
            session.invalidate();
            viewPage = "redirect:/";
        }

        return viewPage;
    }

    @PostMapping("/storenameCheckProcess")
    @ResponseBody
    public int storenameCheckProcess(String s_storename) {
        return sellerService.storenameCheck(s_storename);
    }

    @PostMapping("/telCheckProcess")
    @ResponseBody
    public int telCheckProcess(String s_tel) {
        return sellerService.telCheck(s_tel);
    }

    @PostMapping("/businessnumCheckProcess")
    @ResponseBody
    public int businessnumCheckProcess(@RequestParam("s_businessnum") String s_businessnum) {
        return sellerService.businessnumCheck(s_businessnum);
    }

    @PostMapping("/emailCheckProcess")
    @ResponseBody
    public int emailCheckProcess(@RequestParam("s_email") String s_email) {
        return sellerService.emailCheck(s_email);
    }

    @GetMapping("/findPw")
    public String findPw() {
        return "seller/user/sellerFindPw";
    }

    @PostMapping("/findPwProcess")
    @ResponseBody
    public void findPwProcess(@ModelAttribute SellerVO sellerVO, HttpServletResponse response) throws Exception {
        sellerService.findPw(response, sellerVO);
    }

    @PostMapping("/passwordCheckProcess")
    @ResponseBody
    public int passwordCheckProcess(@RequestParam("s_idx") int s_idx, @RequestParam("s_pw") String s_pw) {
        return sellerService.passwordCheck(s_idx, s_pw);
    }
    @PostMapping("/joinEmailProcess")
    @ResponseBody
    public String joinEmailProcess(HttpSession session, String s_email) {
        String authCode = sellerService.joinEmail(s_email);
        session.setAttribute("s_email", s_email);
        session.setAttribute("authCode", authCode);
        return authCode;
    }

    @GetMapping("/sellerVerifyEmail")
    public String sellerVerifyEmail() {
        return "seller/user/sellerVerifyEmail";
    }

    @PostMapping("/sellerVerifyEmailProcess")
    @ResponseBody
    public int sellerVerifyEmailProcess(String s_email) {
        return sellerService.verifyEmail(s_email);
    }

    @PostMapping("/getItemProcess")
    @ResponseBody
    public List<ItemVO> getItemProcess(Model model, int s_idx) {
        List<ItemVO> newVO = sellerService.getItem(s_idx);
        model.addAttribute("item", newVO);

        return newVO;
    }

    @PostMapping("/getOrderProcess")
    @ResponseBody
    public List<OrderStateVO> getOrderProcess(Model model, int s_idx) {
        List<OrderStateVO> newVO = sellerService.getOrder(s_idx);
        model.addAttribute("order", newVO);

        return newVO;
    }

    @GetMapping("/dashBoard")
    public String dashBoard(Model model, HttpSession session) {
        SellerVO seller = (SellerVO) session.getAttribute("seller");
        if (seller != null) {
            int s_idx = seller.getS_idx();

            List<ItemVO> items = sellerService.getItemsWithCounts(s_idx);
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

            for (ItemVO item : items) {
                if (item.getSalesAmount() != null) {
                    String formattedSalesAmount = numberFormat.format(item.getSalesAmount());
                    item.setFormattedSalesAmount(formattedSalesAmount);
                } else {
                    item.setFormattedSalesAmount("0");
                }
            }

            List<ReviewVO> reviews = sellerService.getReviewsBySellerId(s_idx);
            List<QnaVO> inquiries = sellerService.getInquiriesBySellerId(s_idx);

            int newOrders = sellerService.getNewOrderCount(s_idx);
            int preparingShipment = sellerService.getPreparingShipmentCount(s_idx);
            int shipping = sellerService.getShippingCount(s_idx);
            int delivered = sellerService.getDeliveredCount(s_idx);

            int refundRequests = sellerService.getRefundRequestCount(s_idx);
            int returnRequests = sellerService.getReturnRequestCount(s_idx);
            int exchangeRequests = sellerService.getExchangeRequestCount(s_idx);

            int todaySettlement = sellerService.getTodaySettlement(s_idx);
            int upcomingSettlement = sellerService.getUpcomingSettlement(s_idx);


            String formattedTodaySettlement = numberFormat.format(todaySettlement);
            String formattedUpcomingSettlement = numberFormat.format(upcomingSettlement);
            model.addAttribute("formattedTodaySettlement", formattedTodaySettlement);
            model.addAttribute("formattedUpcomingSettlement", formattedUpcomingSettlement);
            // 모델에 데이터 추가
            model.addAttribute("newOrders", newOrders);
            model.addAttribute("preparingShipment", preparingShipment);
            model.addAttribute("shipping", shipping);
            model.addAttribute("delivered", delivered);

            model.addAttribute("refundRequests", refundRequests);
            model.addAttribute("returnRequests", returnRequests);
            model.addAttribute("exchangeRequests", exchangeRequests);

            model.addAttribute("todaySettlement", todaySettlement);
            model.addAttribute("upcomingSettlement", upcomingSettlement);


            model.addAttribute("seller", seller);
            model.addAttribute("items", items);
            model.addAttribute("reviews", reviews);
            model.addAttribute("inquiries", inquiries);

            return "seller/dashBoard";
        }
        return "redirect:/seller/login";
    }


    @GetMapping("/viewEdit")
    public String viewEdit(@ModelAttribute("sVO") SearchVO searchVO, @RequestParam("s_idx") int s_idx, String sDate, String eDate, HttpServletRequest request, Model model) {
        if (searchVO.getPageNum() == 0) {
            searchVO.setPageNum(1);
        }

        if (searchVO.getItemNum() == null) {
            searchVO.setItemNum("");
        }

        if (searchVO.getItemName() == null) {
            searchVO.setItemName("");
        }

        if (sDate != null && eDate != null) {
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = dtFormat.parse(sDate);
                Date endDate = dtFormat.parse(eDate);
                searchVO.setStartDate(startDate);
                searchVO.setEndDate(endDate);
            } catch (ParseException e) {
                // 예외 처리
            }
        }

        // Seller 정보 가져오기
        SellerVO seller = sellerService.getSellerWithStoreInfo(searchVO.getS_idx());
        if (seller != null) {
            model.addAttribute("seller", seller);
        } else {
            throw new IllegalArgumentException("Seller not found with id: " + s_idx);
        }

        List<ItemVO> itemList = sellerService.getItems(searchVO);
        model.addAttribute("itemList", itemList);

        pageNav.setTotalRows(sellerService.getItemTotalCount(searchVO));
        PageNav updatedPageNav = globalService.setPageNav(pageNav, searchVO.getPageNum(), searchVO.getPageBlock(), searchVO.getViewNum());
        model.addAttribute("pageNav", updatedPageNav);

        String requestUrl = request.getRequestURL().toString();
        model.addAttribute("requestUrl", requestUrl);
        model.addAttribute("s_idx", s_idx);
        return "seller/service/viewEdit";
    }
    @GetMapping("/sales")
    public String sales(@ModelAttribute("sVO") SearchVO searchVO, String sDate, String eDate, Model model) {


        if (searchVO.getPageNum() == 0) {
            searchVO.setPageNum(1);
        }

        if (searchVO.getOrderNum() == null) {
            searchVO.setOrderNum("");
        }

        if (searchVO.getOrderNickname() == null) {
            searchVO.setOrderNickname("");
        }

        if (sDate != null && eDate != null) {
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = dtFormat.parse(sDate);
                Date endDate = dtFormat.parse(eDate);

                searchVO.setStartDate(startDate);
                searchVO.setEndDate(endDate);
            } catch (ParseException e) {
                // 오류 처리
            }
        }

        SellerVO seller = sellerService.getSellerWithStoreInfo(searchVO.getS_idx());
        if (seller != null) {
            model.addAttribute("seller", seller);
        } else {
            model.addAttribute("error", "판매자 정보를 찾을 수 없습니다.");
        }

        List<OrderStateVO> orderList = sellerService.getOrders(searchVO);

        DecimalFormat df = new DecimalFormat("#,###.##");
        for (OrderStateVO order : orderList) {
            double totalPrice = order.getBos_price() * order.getBos_count() + 3000;
            String formattedTotalPrice = df.format(totalPrice) + "원";
            order.setFormattedTotalPrice(formattedTotalPrice);
        }

        model.addAttribute("orderList", orderList);

        pageNav.setTotalRows(sellerService.getOrderTotalCount(searchVO));

        PageNav updatedPageNav = globalService.setPageNav(pageNav, searchVO.getPageNum(), searchVO.getPageBlock(), searchVO.getViewNum());
        model.addAttribute("pageNav", updatedPageNav);
        int s_idx = searchVO.getS_idx();
        model.addAttribute("s_idx", s_idx);
        return "seller/service/sales";
    }
    @GetMapping("/review")
    public String review(@ModelAttribute("sVO") SearchVO searchVO, @RequestParam(required = false) String sDate, @RequestParam(required = false) String eDate, Model model, HttpServletRequest request) {

        if (searchVO.getPageNum() == 0) {
            searchVO.setPageNum(1);
        }

        if (searchVO.getItemNum() == null) {
            searchVO.setItemNum("");
        }

        if (searchVO.getBuyerNickname() == null) {
            searchVO.setBuyerNickname("");
        }

        if (sDate != null && eDate != null && !eDate.trim().isEmpty()) {
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
            dtFormat.setLenient(false);
            try {
                Calendar calendar = Calendar.getInstance();

                Date startDate = dtFormat.parse(sDate);
                calendar.setTime(startDate);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                searchVO.setStartDate(calendar.getTime());

                Date endDate = dtFormat.parse(eDate);
                calendar.setTime(endDate);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                searchVO.setEndDate(calendar.getTime());
            } catch (ParseException e) {
                log.error("날짜 파싱 오류: {}", e.getMessage());
                model.addAttribute("error", "날짜 형식이 올바르지 않습니다. (예: yyyy-MM-dd)");
            }
        }

        List<ReviewVO> reviewList = sellerService.getReviews(searchVO);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("s_idx", searchVO.getS_idx());

        int totalRows = sellerService.getReviewTotalCount(searchVO);
        pageNav.setTotalRows(totalRows);
        PageNav updatedPageNav = globalService.setPageNav(pageNav, searchVO.getPageNum(), searchVO.getPageBlock(), searchVO.getViewNum());
        model.addAttribute("pageNav", updatedPageNav);

        // 현재 요청 URL을 모델에 추가
        String requestUrl = request.getRequestURI();
        model.addAttribute("pageType", "review");
        model.addAttribute("requestUrl", requestUrl);

        log.debug("SearchVO: {}", searchVO);
        return "seller/service/review";
    }

    @PostMapping("/updateOrderStateProcess")
    @ResponseBody
    public int updateOrderStateProcess(@RequestBody OrderStateVO orderStateVO) {
        log.debug("Received orderStateVO: {}", orderStateVO);
        return sellerService.updateOrderState(orderStateVO);
    }

    @PostMapping("/updateItemStateProcess")
    @ResponseBody
    public int updateItemStateProcess(@RequestBody ItemVO itemVO) {
        return sellerService.updateItemState(itemVO);
    }

    @GetMapping("/inquiry")
    public String viewInquiry(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "productNum", required = false) String productNum, @RequestParam(value = "memberNum", required = false) String memberNum, @RequestParam(value = "answeredStatus", required = false, defaultValue = "all") String answeredStatus, @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Model model) {
        if (endDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            endDate = cal.getTime();
        }
        int rowsPage = 10;
        int pagesPageBlock = 5;

        try {
            Integer productIdx = null;
            Integer memberIdx = null;

            try {
                if (productNum != null && !productNum.trim().isEmpty()) {
                    productIdx = Integer.parseInt(productNum);
                }
            } catch (NumberFormatException e) {
                log.error("Invalid productNum format: {}", productNum);
                model.addAttribute("errorMessage", "상품 번호 형식이 올바르지 않습니다.");
                return "error/generalError";
            }

            try {
                if (memberNum != null && !memberNum.trim().isEmpty()) {
                    memberIdx = Integer.parseInt(memberNum);
                }
            } catch (NumberFormatException e) {
                log.error("Invalid memberNum format: {}", memberNum);
                model.addAttribute("errorMessage", "회원 번호 형식이 올바르지 않습니다.");
                return "error/generalError";
            }

            // answeredStatus 유효성 검증 (all, unanswered, answered)
            if (!Arrays.asList("all", "unanswered", "answered").contains(answeredStatus)) {
                log.error("Invalid answeredStatus value: {}", answeredStatus);
                model.addAttribute("errorMessage", "잘못된 문의 상태 값입니다.");
                return "error/generalError";
            }

            int totalRows = qnaService.countInquiries(productIdx, memberIdx, answeredStatus, startDate, endDate);

            PageNav2 pageNav = new PageNav2();
            pageNav.setTotalRows(totalRows);
            pageNav.setRows_page(rowsPage);
            pageNav.setPages_pageBlock(pagesPageBlock);
            pageNav.setPageNum(pageNum);
            pageNav.calculate();

            List<QnaVO> qnaList = qnaService.getInquiries(productIdx, memberIdx, answeredStatus, startDate, endDate, pageNum, rowsPage);

            model.addAttribute("qnaList", qnaList);
            model.addAttribute("totalCount", totalRows);
            model.addAttribute("pageNav", pageNav);
            model.addAttribute("productNum", productNum);
            model.addAttribute("memberNum", memberNum);
            model.addAttribute("answeredStatus", answeredStatus);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);

        } catch (Exception e) {
            log.error("Error fetching inquiries: ", e);
            model.addAttribute("errorMessage", "문의 목록을 불러오는 중 오류가 발생했습니다.");
            return "error/generalError";
        }

        return "seller/service/inquiry";
    }

    /**
     * 문의 답변 수정 처리 (AJAX 요청)
     */
    @PostMapping("/inquiry/edit")
    @ResponseBody
    public ResponseEntity<?> updateInquiry(@Valid @ModelAttribute("inquiry") QnaVO inquiry, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            qnaService.updateAnswer(inquiry.getQna_idx(), inquiry.getQna_answer());
            return ResponseEntity.ok("문의가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            log.error("Error updating inquiry: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의 수정 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/delivery")
    public String delivery(@RequestParam(required = false) Integer status, HttpSession session, Model model) {

        Integer b_idx = (Integer) session.getAttribute("b_idx");

        if (b_idx == null) {
            return "redirect:/login";
        }

        List<DeliveryVO> deliveryList;
        if (status != null && status != 0) {
            deliveryList = deliveryService.get_delivery_by_status(b_idx, status);
        } else {
            deliveryList = deliveryService.get_delivery_list(b_idx);
        }

        model.addAttribute("deliveryList", deliveryList);
        return "seller/service/delivery";
    }

    @GetMapping("/settlement")
    public String settlement() {
        return "seller/service/settlement";
    }

    @GetMapping("/store")
    public String storeStatus(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                              Model model,
                              HttpSession session) {

        SellerVO seller = (SellerVO) session.getAttribute("seller");
        if (seller == null) {
            return "redirect:/seller/login";
        }

        int s_idx = seller.getS_idx();

        // 총 항목 수 가져오기
        int totalItems = sellerService.getTotalStoreStatusCount(s_idx, startDate, endDate);

        ListPageNav pageNav = new ListPageNav();
        pageNav.setTotalRows(totalItems);
        pageNav.setPageNum(pageNum);
        pageNav.setRowsPerPage(10);
        pageNav.calculatePageNav();

        int offset = (pageNav.getPageNum() - 1) * pageNav.getRowsPerPage();
        offset = Math.max(0, offset);

        List<StoreStatusDTO> items = sellerService.getStoreStatusList(s_idx, startDate, endDate, offset, pageNav.getRowsPerPage());
        model.addAttribute("items", items);
        model.addAttribute("pageNav", pageNav);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("s_idx", s_idx);
        model.addAttribute("currentPage", pageNav.getPageNum());
        model.addAttribute("pageSize", pageNav.getRowsPerPage());
        model.addAttribute("requestUrl", "/seller/store");
        return "seller/service/store";
    }
}