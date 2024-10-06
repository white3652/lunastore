package com.lunastore.controller;

import com.lunastore.dto.DailySalesDTO;
import com.lunastore.dto.OrderStatusCountsDTO;
import com.lunastore.dto.ReviewDetailsDTO;
import com.lunastore.service.ItemService;
import com.lunastore.service.OrderService;
import com.lunastore.service.SellerService;
import com.lunastore.vo.BuyerVO;
import com.lunastore.vo.SellerVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ItemService itemService;
    private final SellerService sellerService;
    private final OrderService orderService;

    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Boolean>> checkLogin(HttpSession session) {
        BuyerVO buyerVO = (BuyerVO) session.getAttribute("buyer");
        SellerVO sellerVO = (SellerVO) session.getAttribute("seller");
        Map<String, Boolean> response = new HashMap<>();
        response.put("isBuyerLoggedIn", buyerVO != null);
        response.put("isSellerLoggedIn", sellerVO != null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getReviewDetails(@RequestParam("reviewId") int reviewId) {
        try {
            ReviewDetailsDTO reviewDetails = itemService.getReviewDetails(reviewId);
            return ResponseEntity.ok(reviewDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 상세 정보를 가져오는 데 실패했습니다.");
        }
    }
    @GetMapping("/dailySalesData")
    public ResponseEntity<?> getDailySalesData(HttpSession session) {
        SellerVO seller = (SellerVO) session.getAttribute("seller");
        if (seller != null) {
            int s_idx = seller.getS_idx();
            List<DailySalesDTO> data = sellerService.getDailySalesData(s_idx);
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
    @GetMapping("/orderStatusCounts")
    public ResponseEntity<OrderStatusCountsDTO> getOrderStatusCounts(HttpSession session) {
        SellerVO seller = (SellerVO) session.getAttribute("seller");
        if (seller == null || seller.getS_idx() <= 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        int s_idx = seller.getS_idx();
        OrderStatusCountsDTO counts = orderService.getOrderStatusCountsBySellerId(s_idx);
        return ResponseEntity.ok(counts);
    }
}