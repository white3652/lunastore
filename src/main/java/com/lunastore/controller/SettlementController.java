package com.lunastore.controller;


import com.lunastore.dto.SettlementDTO;
import com.lunastore.service.SettlementService;
import com.lunastore.vo.SellerVO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settlement")
public class SettlementController {
    private final SettlementService settlementService;

    @GetMapping("/status")
    public String getSettlementStatus(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            HttpSession session,
            Model model) {

        SellerVO seller = (SellerVO) session.getAttribute("seller");
        if (seller == null) {
            // seller가 세션에 없을 경우 로그인 페이지로 리다이렉트
            return "redirect:/sellerLogin";
        }
        int s_idx = seller.getS_idx();
        // 기본 날짜 설정 (최근 7일)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        // startDate 설정 (기본값: 7일 전)
        if (startDate == null || startDate.isEmpty()) {
            calendar.add(Calendar.DAY_OF_MONTH, -30); // 최근 7일 전으로 설정
            startDate = formatter.format(calendar.getTime()); // 'yyyy-MM-dd' 형식으로 변환
        }

        // endDate 설정 (기본값: 오늘 날짜, 시간은 23:59:59로 설정)
        if (endDate == null || endDate.isEmpty()) {
            calendar = Calendar.getInstance(); // 오늘 날짜로 초기화
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            endDate = formatter.format(calendar.getTime());
        }

        // 페이징 계산
        int totalCount = settlementService.countSettlementData(startDate, endDate, s_idx);
        int totalPage = (int) Math.ceil((double) totalCount / size);
        if (page > totalPage) page = totalPage;
        if (page < 1) page = 1;

        List<SettlementDTO> settlementData = settlementService.getSettlementStatusPaged(startDate, endDate, page, size, s_idx);

        model.addAttribute("settlementData", settlementData);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPage", totalPage);

        return "seller/service/settlement";
    }

    // 정산 데이터 업데이트 예시 (필요 시)
    @PostMapping("/update")
    public String updateSettlement(@ModelAttribute SettlementDTO settlementDTO, RedirectAttributes redirectAttributes) {
        try {
            boolean success = settlementService.updateSettlement(settlementDTO);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "정산 데이터가 성공적으로 업데이트되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "정산 데이터 업데이트에 실패했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "정산 데이터 업데이트 중 오류가 발생했습니다.");
            // 로그 예시:
            // logger.error("Failed to update settlement data: {}", settlementDTO, e);
        }
        return "redirect:/settlement/status";
    }
}