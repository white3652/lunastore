package com.lunastore.controller;
import com.lunastore.dto.SellerSalesDTO;
import com.lunastore.dto.StoreSalesDTO;
import com.lunastore.vo.Grade;
import com.lunastore.service.AdminService;
import com.lunastore.service.GlobalService;
import com.lunastore.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final GlobalService globalService;
    private final MessageSource messageSource;

    @GetMapping({"", "/"})
    public String showAdminLoginPage() {
        return "admin/user/login";
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute AdminVO adminVO, HttpServletRequest request, Model model, Locale locale) throws SQLException {

        AdminVO loginAdmin = adminService.login(adminVO.getA_id(), adminVO.getA_pw());

        if (loginAdmin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", loginAdmin);
            return "redirect:buyerList";
        } else {
            String errorMsg = messageSource.getMessage("login.failed", null, locale);
            model.addAttribute("msg", errorMsg);
            model.addAttribute("a_id", adminVO.getA_id());
            return "admin/user/login";
        }
    }

    @GetMapping("/buyerList")
    public String buyerlist(Model model) throws SQLException {
        List<BuyerVO> buyerList = adminService.searchBuyer(new SearchVO());
        int totalCnt = adminService.totalList(new SearchVO());
        model.addAttribute("buyerList", buyerList);
        model.addAttribute("totalcnt", totalCnt);
        model.addAttribute("searchVO", new SearchVO());
        return "admin/service/buyerList";
    }

    @GetMapping("/buyers/{b_idx}/edit")
    public String editBuyer(@PathVariable("b_idx") int b_idx, Model model) throws SQLException {

        AdminBuyerDetailsVO buyerDetails = adminService.getAdminBuyerDetails(b_idx);
        if (buyerDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Buyer not found");
        }

        List<Grade> grades = Arrays.asList(Grade.values());

        model.addAttribute("grades", grades);
        model.addAttribute("adminBuyer", buyerDetails);

        return "admin/service/buyerUpdate";
    }

    @PostMapping("/buyers/{b_idx}/update")
    public String updateBuyer(@PathVariable("b_idx") int b_idx, @ModelAttribute("adminBuyer") AdminBuyerDetailsVO adminBuyerDetails, BindingResult bindingResult, HttpServletRequest request, Model model, Locale locale) throws SQLException {

        if (bindingResult.hasErrors()) {
            if (adminBuyerDetails.getBa_address() == null) {
                adminBuyerDetails.setBa_address("");
            }
            if (adminBuyerDetails.getBa_zipcode() == null) {
                adminBuyerDetails.setBa_zipcode("");
            }
            if (adminBuyerDetails.getBa_restaddress() == null) {
                adminBuyerDetails.setBa_restaddress("");
            }
            if (adminBuyerDetails.getBa_contact() == null) {
                adminBuyerDetails.setBa_contact("");
            }

            List<Grade> grades = Arrays.asList(Grade.values());
            model.addAttribute("grades", grades);

            return "admin/service/buyerUpdate";
        }

        adminBuyerDetails.setB_idx(b_idx);
        AdminBuyerDetailsVO updatedAdminBuyer = adminService.updateAdminBuyer(adminBuyerDetails);

        if (updatedAdminBuyer != null) {
            HttpSession session = request.getSession();
            session.removeAttribute("adminBuyer");
            session.setAttribute("adminBuyer", updatedAdminBuyer);
            String successMsg = messageSource.getMessage("update.success", null, locale);
            model.addAttribute("msg", successMsg);
        } else {
            String failureMsg = messageSource.getMessage("update.failure", null, locale);
            model.addAttribute("msg", failureMsg);
        }

        return "redirect:/admin/close";
    }

    @PostMapping("/buyers/search")
    public String searchBuyer(@ModelAttribute("searchVO") SearchVO searchVO, Model model, Locale locale) throws SQLException {
        if (searchVO.getSearchField3() == null || searchVO.getSearchField3().isEmpty()) {
            searchVO.setSearchField3("0");
        }
        List<BuyerVO> buyerList = adminService.searchBuyer(searchVO);
        model.addAttribute("buyerList", buyerList);

        int totalCnt = adminService.totalList(searchVO);
        model.addAttribute("totalcnt", totalCnt);

        String searchMsg = messageSource.getMessage("search.result", new Object[]{totalCnt}, locale);
        model.addAttribute("searchMsg", searchMsg);

        return "admin/service/buyerList";
    }

    @GetMapping("/buyers/{b_idx}/send-email")
    public String sendEmailToBuyer(@PathVariable("b_idx") int b_idx, Model model, HttpServletRequest request) throws SQLException {

        BuyerVO adminBuyer = adminService.findByBuyer(b_idx);
        model.addAttribute("adminBuyer", adminBuyer);

        HttpSession session = request.getSession();
        session.setAttribute("adminBuyer", adminBuyer);

        return "admin/service/sendMailToBuyer";
    }

    @GetMapping("/send-email/all")
    public String sendFullMail(HttpServletRequest request) throws SQLException {

        List<String> emailList = adminService.allBuyerMail(new BuyerVO());

        HttpSession session = request.getSession();
        session.setAttribute("emailList", emailList);

        return "admin/service/sendMailToAllMembers";
    }

    @PostMapping("/send-email/all")
    public String sendAllEmails(HttpServletRequest request, Model model, Locale locale) throws Exception {

        HttpSession session = request.getSession();
        List<String> emailList = (List<String>) session.getAttribute("emailList");

        String title = request.getParameter("admin_email_tit");
        String content = request.getParameter("admin_email_content");

        boolean allSent = true;
        for (String tomail : emailList) {
            boolean isSent = adminService.sendMail(tomail, title, content);
            if (!isSent) {
                allSent = false;
                break;
            }
        }
        if (allSent) {
            String successMsg = messageSource.getMessage("email.sent.all.success", null, locale);
            model.addAttribute("msg", successMsg);
            return "admin/common/close";
        } else {
            String failureMsg = messageSource.getMessage("email.sent.all.failure", null, locale);
            model.addAttribute("msg", failureMsg);
        }

        return "admin/service/sendMailToAllMembers";
    }

    @GetMapping("/downloads/excel")
    public String excelDownloads() {
        return "admin/service/excelDownload";
    }

    @PostMapping("/downloads/excel")
    public void downloadExcel(HttpServletResponse response, @ModelAttribute BuyerVO buyerVO, Locale locale) throws IOException, SQLException {

        List<BuyerVO> buyerList = adminService.totalBuyer(buyerVO);

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("BuyerList");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue(messageSource.getMessage("excel.header.num", null, locale));
        headerRow.createCell(1).setCellValue(messageSource.getMessage("excel.header.firstName", null, locale));
        headerRow.createCell(2).setCellValue(messageSource.getMessage("excel.header.lastName", null, locale));
        headerRow.createCell(3).setCellValue(messageSource.getMessage("excel.header.phone", null, locale));
        headerRow.createCell(4).setCellValue(messageSource.getMessage("excel.header.birth", null, locale));
        headerRow.createCell(5).setCellValue(messageSource.getMessage("excel.header.email", null, locale));
        headerRow.createCell(6).setCellValue(messageSource.getMessage("excel.header.grade", null, locale));
        headerRow.createCell(7).setCellValue(messageSource.getMessage("excel.header.gender", null, locale));
        headerRow.createCell(8).setCellValue(messageSource.getMessage("excel.header.nickname", null, locale));
        headerRow.createCell(9).setCellValue(messageSource.getMessage("excel.header.point", null, locale));

        int rowNum = 1;
        for (BuyerVO buyer : buyerList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(buyer.getB_idx());
            row.createCell(1).setCellValue(buyer.getB_firstname());
            row.createCell(2).setCellValue(buyer.getB_lastname());
            row.createCell(3).setCellValue(buyer.getB_tel());
            row.createCell(4).setCellValue(buyer.getB_birth());
            row.createCell(5).setCellValue(buyer.getB_email());
            row.createCell(6).setCellValue(buyer.getB_grade().toString());
            row.createCell(7).setCellValue(buyer.getB_gender());
            row.createCell(8).setCellValue(buyer.getB_nickname());
            row.createCell(9).setCellValue(buyer.getB_point());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=BuyerList.xlsx");

        try {
            wb.write(response.getOutputStream());
        } finally {
            wb.close();
        }
    }

    @PostMapping("/buyers/{b_idx}/cancel")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelBuyerAjax(
            @PathVariable("b_idx") int b_idx,
            Locale locale) {

        Map<String, Object> response = new HashMap<>();
        try {
            int result = adminService.cancelB(b_idx);
            if (result > 0) {
                // 세션 무효화가 필요하다면 추가
                response.put("success", true);
                String successMsg = messageSource.getMessage("cancel.success", null, locale);
                response.put("message", successMsg);
            } else {
                response.put("success", false);
                String failureMsg = messageSource.getMessage("cancel.failure", null, locale);
                response.put("message", failureMsg);

                // 실패 시 상세 정보 제공
                AdminBuyerDetailsVO adminBuyer = adminService.findAdminBuyerDetails(b_idx);
                if (adminBuyer != null) {
                    response.put("adminBuyer", adminBuyer);
                } else {
                    response.put("message", "해당 구매자를 찾을 수 없습니다.");
                }
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(response);
    }



    @GetMapping("/sellerList")
    public String sellerList(Model model) throws SQLException {

        List<SellerVO> sellerList = adminService.getAllSellers();
        List<StoreSalesDTO> storeSalesList = adminService.getStoreSalesDetails();

        DecimalFormat formatter = new DecimalFormat("#,###");

        Map<Integer, StoreSalesDTO> salesMap = storeSalesList.stream()
                .collect(Collectors.toMap(StoreSalesDTO::getS_idx, Function.identity()));

        List<SellerSalesDTO> sellerSalesList = sellerList.stream()
                .map(seller -> {
                    SellerSalesDTO dto = new SellerSalesDTO();
                    dto.setSeller(seller);
                    StoreSalesDTO sales = salesMap.get(seller.getS_idx());
                    if (sales != null) {
                        sales.setSettledAmountFormatted(formatter.format(sales.getSettledAmount()));
                        sales.setUnsettledAmountFormatted(formatter.format(sales.getUnsettledAmount()));
                        sales.setTotalAmountFormatted(formatter.format(sales.getTotalAmount()));
                    }
                    dto.setSales(sales);
                    return dto;
                })
                .collect(Collectors.toList());

        int totalSales = storeSalesList.stream()
                .mapToInt(StoreSalesDTO::getTotalAmount)
                .sum();

        model.addAttribute("sellerSalesList", sellerSalesList);
        model.addAttribute("totalSales", formatter.format(totalSales));

        return "admin/service/sellerList";
    }

    @GetMapping("/close")
    public String windowClose() {
        return "admin/common/close";
    }

    // 바이어 이메일 전송 처리
    @PostMapping("/buyers/{b_idx}/send-email")
    public String processEmailToBuyer(@PathVariable("b_idx") int b_idx, HttpServletRequest request, Model model, Locale locale) throws Exception {

        BuyerVO adminBuyer = (BuyerVO) request.getSession().getAttribute("adminBuyer");
        if (adminBuyer == null) {
            String failureMsg = messageSource.getMessage("email.sent.failure", null, locale);
            model.addAttribute("msg", failureMsg);
            return "admin/service/sendMailToBuyer";
        }
        model.addAttribute("adminBuyer", adminBuyer);
        String tomail = adminBuyer.getB_email();
        String title = request.getParameter("admin_email_tit");
        String content = request.getParameter("admin_email_content");

        boolean isSent = adminService.sendMail(tomail, title, content);

        if (isSent) {
            String successMsg = messageSource.getMessage("email.sent.success", null, locale);
            model.addAttribute("msg", successMsg);
            return "admin/common/close";
        } else {
            String failureMsg = messageSource.getMessage("email.sent.failure", null, locale);
            model.addAttribute("msg", failureMsg);
        }
        return "admin/service/sendMailToBuyer";
    }

    @GetMapping("/sellers/{s_idx}/edit")
    public String editSeller(@PathVariable("s_idx") int s_idx, Model model) throws SQLException {

        SellerVO seller = adminService.findBySeller(s_idx);
        if (seller != null) {seller.setS_lastlogindate(globalService.dateUpdate(seller.getS_lastlogindate() != null ? seller.getS_lastlogindate() : new Date()));
            seller.setS_pwmodifydate(globalService.dateUpdate(seller.getS_pwmodifydate()));
            seller.setS_modifydate(globalService.dateUpdate(seller.getS_modifydate()));
            seller.setS_regdate(globalService.dateUpdate(seller.getS_regdate()));
        }
        model.addAttribute("seller", seller);
        return "admin/service/sellerUpdate";
    }

    @PostMapping("/sellers/{s_idx}/update")
    public String updateSeller(@PathVariable("s_idx") int s_idx, @ModelAttribute("sellerVO") SellerVO sellerVO, Model model, Locale locale) throws SQLException {

        SellerVO currentSeller = adminService.findBySeller(s_idx);
        if (currentSeller == null) {
            String message = messageSource.getMessage("update.failure", null, locale);
            model.addAttribute("msg", message);
            return "redirect:/admin/close";
        }

        if (sellerVO.getS_pw() != null) {
            currentSeller.setS_pw(sellerVO.getS_pw());
        }
        if (sellerVO.getS_tel() != null) {
            currentSeller.setS_tel(sellerVO.getS_tel());
        }
        if (sellerVO.getS_birth() != null) {
            currentSeller.setS_birth(sellerVO.getS_birth());
        }
        if (sellerVO.getS_email() != null) {
            currentSeller.setS_email(sellerVO.getS_email());
        }
        if (sellerVO.getS_address() != null) {
            currentSeller.setS_address(sellerVO.getS_address());
        }
        if (sellerVO.getS_restaddress() != null) {
            currentSeller.setS_restaddress(sellerVO.getS_restaddress());
        }
        if (sellerVO.getS_zipcode() != null) {
            currentSeller.setS_zipcode(sellerVO.getS_zipcode());
        }
        if (sellerVO.getS_storename() != null) {
            currentSeller.setS_storename(sellerVO.getS_storename());
        }
        if (sellerVO.getS_storeintro() != null) {
            currentSeller.setS_storeintro(sellerVO.getS_storeintro());
        }
        if (sellerVO.getS_memo() != null) {
            currentSeller.setS_memo(sellerVO.getS_memo());
        }
        if (sellerVO.getS_cancel() != null) {
            currentSeller.setS_cancel(sellerVO.getS_cancel());
        }
        if (sellerVO.getS_check() != null) {
            currentSeller.setS_check(sellerVO.getS_check());
        }
        if (sellerVO.getS_terms() != null) {
            currentSeller.setS_terms(sellerVO.getS_terms());
        }

        SellerVO updatedSeller = adminService.updateSeller(currentSeller);
        String message;

        if (updatedSeller != null) {
            message = messageSource.getMessage("update.success", null, locale);
            model.addAttribute("seller", updatedSeller);
        } else {
            message = messageSource.getMessage("update.failure", null, locale);
        }

        model.addAttribute("msg", message);
        return "redirect:/admin/close";
    }



    @PostMapping("/sellers/search")
    public String searchSeller(@ModelAttribute("searchVO") SearchVO searchVO, Model model, Locale locale) throws SQLException {
        List<SellerVO> sellerList = adminService.searchSeller(searchVO);
        List<StoreSalesDTO> storeSalesList = adminService.getStoreSalesDetails();

        DecimalFormat formatter = new DecimalFormat("#,###");

        Map<Integer, StoreSalesDTO> salesMap = storeSalesList.stream()
                .collect(Collectors.toMap(StoreSalesDTO::getS_idx, Function.identity()));

        List<SellerSalesDTO> sellerSalesList = sellerList.stream()
                .map(seller -> {
                    SellerSalesDTO dto = new SellerSalesDTO();
                    dto.setSeller(seller);
                    StoreSalesDTO sales = salesMap.get(seller.getS_idx());
                    if (sales != null) {
                        sales.setSettledAmountFormatted(formatter.format(sales.getSettledAmount()));
                        sales.setUnsettledAmountFormatted(formatter.format(sales.getUnsettledAmount()));
                        sales.setTotalAmountFormatted(formatter.format(sales.getTotalAmount()));
                    }
                    dto.setSales(sales);
                    return dto;
                })
                .collect(Collectors.toList());

        int totalSales = storeSalesList.stream()
                .mapToInt(StoreSalesDTO::getTotalAmount)
                .sum();
        model.addAttribute("sellerSalesList", sellerSalesList);
        model.addAttribute("totalSales", formatter.format(totalSales));
        String searchMsg = messageSource.getMessage("search.result.seller", new Object[]{sellerList.size()}, locale);
        model.addAttribute("searchMsg", searchMsg);

        return "admin/service/sellerList";
    }

    @GetMapping("/sellers/{s_idx}/send-email")
    public String sendEmailToSeller(@PathVariable("s_idx") int s_idx, Model model, HttpServletRequest request) throws SQLException {
        SellerVO seller = adminService.findBySeller(s_idx);
        if (seller == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found");
        }
        HttpSession session = request.getSession();
        session.setAttribute("seller", seller);

        model.addAttribute("seller", seller);

        return "admin/service/sendMailToSeller";
    }


    @PostMapping("/sellers/{s_idx}/send-email")
    public String processEmailToSeller(@PathVariable("s_idx") int s_idx, HttpServletRequest request, Model model, Locale locale) throws Exception {
        SellerVO seller = (SellerVO) request.getSession().getAttribute("seller");
        if (seller == null) {
            String failureMsg = messageSource.getMessage("email.sent.failure", null, locale);
            model.addAttribute("msg", failureMsg);
            return "admin/service/sendMailToSeller";
        }

        String tomail = seller.getS_email();
        String title = request.getParameter("admin_email_tit");
        String content = request.getParameter("admin_email_content");

        boolean isSent = adminService.sendMail(tomail, title, content);

        if (isSent) {
            String successMsg = messageSource.getMessage("email.sent.success", null, locale);
            model.addAttribute("msg", successMsg);
            return "admin/common/close";
        } else {
            String failureMsg = messageSource.getMessage("email.sent.failure", null, locale);
            model.addAttribute("msg", failureMsg);
        }
        return "admin/service/sendMailToSeller";
    }

    @PostMapping("/sellers/{s_idx}/cancel")
    public String cancelSeller(@PathVariable("s_idx") int s_idx, HttpServletRequest request, Model model, Locale locale) throws SQLException {
        int result = adminService.cancelS(s_idx);
        if (result == 1) {
            HttpSession session = request.getSession();
            session.invalidate();
            String successMsg = messageSource.getMessage("cancel.success", null, locale);
            model.addAttribute("msg", successMsg);
            return "redirect:/admin/close";
        }
        String failureMsg = messageSource.getMessage("cancel.failure", null, locale);
        model.addAttribute("msg", failureMsg);
        return "admin/common/close";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/admin";
    }
}
