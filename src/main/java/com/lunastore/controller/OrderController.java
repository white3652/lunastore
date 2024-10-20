package com.lunastore.controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunastore.common.PageNav;
import com.lunastore.dto.*;
import com.lunastore.service.*;
import com.lunastore.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final BuyerService buyerService;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    private final ItemService itemService;
    private final PageNav pageNav;
    private final GlobalService globalService;

    @PostMapping("/order")
    public String order(int b_idx, HttpSession session, Model model) throws SQLException, JsonProcessingException {

        BuyerVO buyer = buyerService.getBuyerById(b_idx);
        if (buyer == null) {
            model.addAttribute("errorMessage", "구매자를 찾을 수 없습니다.");
            return "errorPage";
        }

        AddressVO address = buyerService.getAddress(b_idx);
        List<CartVO> cartList = cartService.cartList(b_idx);

        double totalPrice = 0;
        for (CartVO cart : cartList) {
            double price = cart.getNumericPrice();
            int quantity = cart.getI_count();
            totalPrice += price * quantity;
        }

        totalPrice += 3000;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
        String formattedTotalPrice = formatter.format(totalPrice - 3000);
        String formattedTotalWithShipping = formatter.format(totalPrice);

        String itemsName = cartList.stream()
                .map(CartVO::getI_name)
                .collect(Collectors.joining(", "));

        String buyerName = buyer.getB_firstname() + " " + buyer.getB_lastname();

        List<Integer> cartItemsCount = cartList.stream()
                .map(CartVO::getI_count)
                .collect(Collectors.toList());

        String cartItemsCountJson = objectMapper.writeValueAsString(cartItemsCount);
        session.setAttribute("cartList", cartList);
        model.addAttribute("cartList", cartList);
        model.addAttribute("buyer", buyer);
        model.addAttribute("address", address);
        model.addAttribute("formattedTotalPrice", formattedTotalPrice);
        model.addAttribute("formattedTotalWithShipping", formattedTotalWithShipping);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("itemsName", itemsName);
        model.addAttribute("buyerName", buyerName);
        model.addAttribute("cartItemsCountJson", cartItemsCountJson);

        return "buyer/service/buyerShoppingPage";
    }

    @GetMapping("/orderPage")
    public String orderPage(@RequestParam("b_idx") int b_idx, Model model) throws SQLException, JsonProcessingException {

        BuyerVO buyer = buyerService.getBuyerById(b_idx);
        if (buyer == null) {

            model.addAttribute("errorMessage", "구매자를 찾을 수 없습니다.");
            return "errorPage";
        }

        List<CartVO> cartList = cartService.cartList(b_idx);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        List<Integer> cartItemsCount = cartList.stream()
                .map(CartVO::getI_count)
                .collect(Collectors.toList());

        String cartItemsCountJson = objectMapper.writeValueAsString(cartItemsCount);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
        List<CartVO> formattedCartList = cartList.stream()
                .map(cart -> {
                    String priceWithoutCommas = cart.getI_price().replace(",", "");
                    double price = Double.parseDouble(priceWithoutCommas);
                    cart.setI_price(formatter.format(price));
                    return cart;
                })
                .collect(Collectors.toList());

        List<ItemVO> interestList = itemService.getRandomItems(3);
        if (interestList == null) {
            interestList = new ArrayList<>();
        }

        List<ItemVO> recommendList = itemService.getRandomItems(3);
        if (recommendList == null) {
            recommendList = new ArrayList<>();
        }

        model.addAttribute("cartList", formattedCartList);
        model.addAttribute("buyer", buyer);
        model.addAttribute("interestList", interestList);
        model.addAttribute("recommendList", recommendList);
        model.addAttribute("cartItemsCountJson", cartItemsCountJson);

        return "buyer/service/buyerItemCart";
    }

    @GetMapping("/paymentContent")
    public String paymentContent(@RequestParam("b_idx") int b_idx, Model model) throws SQLException, JsonProcessingException {

        AddressVO address = buyerService.getAddress(b_idx);

        BuyerVO buyer = buyerService.getBuyerById(b_idx);

        List<CartVO> cartList = cartService.cartList(b_idx);

        List<Integer> cartItemsCount = cartList.stream()
                .map(CartVO::getI_count)
                .collect(Collectors.toList());

        String cartItemsCountJson = objectMapper.writeValueAsString(cartItemsCount);
        log.info("paymentContent: cartItemsCountJson 생성 완료.");

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
        List<CartVO> formattedCartList = cartList.stream()
                .map(cart -> {
                    String priceWithoutCommas = cart.getI_price().replace(",", "");
                    double price = Double.parseDouble(priceWithoutCommas);
                    cart.setI_price(formatter.format(price));
                    return cart;
                })
                .collect(Collectors.toList());
        List<ItemVO> interestList = itemService.getRandomItems(3);
        if (interestList == null) {
            interestList = new ArrayList<>();
        }

        List<ItemVO> recommendList = itemService.getRandomItems(3);

        model.addAttribute("address", address);
        model.addAttribute("cartList", formattedCartList);
        model.addAttribute("buyer", buyer);
        model.addAttribute("interestList", interestList);
        model.addAttribute("recommendList", recommendList);
        model.addAttribute("cartItemsCountJson", cartItemsCountJson);

        return "buyer/service/buyerShoppingPage :: paymentContent";
    }

    @PostMapping("/insertOrder")
    @ResponseBody
    public ResponseEntity<?> insertOrder(@Valid @RequestBody OrderVO orderVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
        }
        try {
            log.debug("Received OrderVO: {}", orderVO);
            int bo_idx = orderService.insertOrder(orderVO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "주문이 성공적으로 처리되었습니다.");
            response.put("bo_idx", bo_idx);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Order insertion failed: ", e);
            return ResponseEntity.status(500).body(new ApiResponse(false, "주문 처리 중 서버 오류가 발생했습니다."));
        }
    }

    @PostMapping("/deleteOrder")
    @ResponseBody
    public ResponseEntity<?> deleteOrder(@RequestBody Map<String, Integer> payload) {
        Integer bo_idx = payload.get("bo_idx");
        if (bo_idx == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "bo_idx가 제공되지 않았습니다."));
        }
        orderService.deleteOrder(bo_idx);
        return ResponseEntity.ok(new ApiResponse(true, "주문이 취소되었습니다."));
    }

    @PostMapping("/successOrder")
    @ResponseBody
    public ResponseEntity<?> successOrder(@RequestBody OrderVO orderVO) {
        try {
            orderService.updateOrderState(orderVO.getBo_idx(), 1);
            return ResponseEntity.ok(new ApiResponse(true, "결제가 성공적으로 완료되었습니다."));
        } catch (Exception e) {
            log.error("Order state update failed: ", e);
            return ResponseEntity.status(500).body(new ApiResponse(false, "주문 상태 업데이트 중 오류가 발생했습니다."));
        }
    }

    @PostMapping("/findOrder")
    @ResponseBody
    public List<OrderStateVO> findOrder(@RequestParam int b_idx, @RequestParam int i_idx) {
        return orderService.findOrder(b_idx, i_idx);
    }

    @GetMapping("/orderList")
    public String orderList(Model model, HttpSession session, HttpServletRequest request,
                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageBlock", defaultValue = "1") int pageBlock) {

        Integer b_idx = (Integer) session.getAttribute("b_idx");

        if (b_idx == null) {
            log.warn("b_idx is null. Redirecting to login.");
            return "redirect:/login";
        }

        log.debug("orderList method START called with b_idx: {}", b_idx);
        // 전체 주문 수 설정
        int totalOrders = orderService.getTotalCount(b_idx);
        pageNav.setTotalRows(totalOrders);

        // 페이징 로직 적용
        PageNav updatedPageNav = globalService.setPageNav(pageNav, pageNum, pageBlock, pageNav.getRows_page());

        List<OrderDTO> orders = orderService.getOrdersForBuyer(b_idx, updatedPageNav.getStartNum(), updatedPageNav.getRows_page());
        log.debug("Fetched Orders Count: " + orders.size());

        model.addAttribute("orders", orders);
        model.addAttribute("pageNav", updatedPageNav);
        model.addAttribute("buyerId", b_idx);

        String url = request.getRequestURI();
        int lastSlashIndex = url.lastIndexOf('/');
        String jspPage = (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) ? url.substring(lastSlashIndex + 1) : "";

        model.addAttribute("url", url);
        model.addAttribute("lastSlashIndex", lastSlashIndex);
        model.addAttribute("jspPage", jspPage);
        log.debug("Fetched Orders Details: " + orders); // 상세 로그
        log.debug("orderList method END called with b_idx: {}", b_idx);
        return "buyer/service/buyerOrderList";
    }



    @PostMapping("/updateAddress")
    @ResponseBody
    public ResponseEntity<?> updateAddress(@RequestBody AddressVO addressVO) {
        try {
            orderService.updateAddress(addressVO);
            return ResponseEntity.ok(new ApiResponse(true, "주소가 수정되었습니다."));
        } catch (Exception e) {
            log.error("Address update failed: ", e);
            return ResponseEntity.status(500).body(new ApiResponse(false, "주소 수정 중 오류가 발생했습니다."));
        }
    }

    @PostMapping("/updateContact")
    @ResponseBody
    public ResponseEntity<?> updateContact(@RequestBody UpdateContactDTO updateContactDTO) {
        try {
            orderService.updateContact(updateContactDTO);
            return ResponseEntity.ok(new ApiResponse(true, "연락처가 수정되었습니다."));
        } catch (Exception e) {
            log.error("Contact update failed: ", e);
            return ResponseEntity.status(500).body(new ApiResponse(false, "연락처 수정 중 오류가 발생했습니다."));
        }
    }

    @PostMapping("/submitNewOrder")
    @ResponseBody
    public ResponseEntity<?> submitNewOrder(@Valid @RequestBody OrderVO orderVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            log.debug("Validation failed: {}", errorMessage);
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
        }

        try {
            log.debug("Received OrderVO2: {}", orderVO);
            if (orderVO.getBo_itemname() == null) {
                log.error("bo_itemname 값이 null입니다.");
                return ResponseEntity.badRequest().body(new ApiResponse(false, "상품명이 누락되었습니다."));
            }

            orderService.submitNewOrder(orderVO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "주문이 성공적으로 처리되었습니다.");
            response.put("bo_idx", orderVO.getBo_idx());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.debug("Error processing order: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "주문 처리 중 서버 오류가 발생했습니다."));
        }
    }
    @PostMapping("/payment")
    @ResponseBody
    public ResponseEntity<?> payment(@RequestBody PaymentDTO paymentDTO) {
        try {
            orderService.processPayment(paymentDTO);
            return ResponseEntity.ok(new ApiResponse(true, "결제가 완료되었습니다."));
        } catch (Exception e) {
            log.error("Payment processing failed: ", e);
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/updateOrderState")
    public ResponseEntity<?> updateOrderState(@RequestBody UpdateOrderStateDTO updateOrderStateDTO) {
        try {
            orderService.updateOrderState(updateOrderStateDTO.getBo_idx(), updateOrderStateDTO.getState());
            return ResponseEntity.ok(new ApiResponse(true, "주문 상태가 업데이트되었습니다."));
        } catch (Exception e) {
            log.error("Order state update failed: ", e);
            return ResponseEntity.status(500).body(new ApiResponse(false, "주문 상태 업데이트 중 오류가 발생했습니다."));
        }
    }



    @GetMapping("/orderDetail")
    public String orderDetail(@RequestParam("orderId") int orderId, Model model, HttpSession session) throws SQLException {
        Integer b_idx = (Integer) session.getAttribute("b_idx");
        if (b_idx == null) {
            return "redirect:/login";
        }

        List<OrderViewDTO> orders = orderService.getOrderViewByBoIdx(orderId);
        log.debug("Fetched orders: {}", orders);

        if (orders.isEmpty() || orders.get(0).getB_idx() != b_idx) {
            return "redirect:/order/orderList?error=notfound";
        }

        double totalPrice = orders.stream()
                .mapToDouble(o -> o.getBos_price() * o.getBos_count())
                .sum() + 3000;
        OrderViewDTO order = orders.get(0);
        order.setTotalPrice(totalPrice);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
        formatter.setMaximumFractionDigits(0);

        String formattedTotalPrice = formatter.format(totalPrice);
        model.addAttribute("formattedTotalPrice", formattedTotalPrice);

        model.addAttribute("order", order);
        model.addAttribute("items", orders);

        return "buyer/service/buyerOrderView";
    }

    // 주문 상세 조회 (POST)
    @PostMapping("/orderDetail")
    public String orderDetailPost(@RequestParam("orderId") int orderId, Model model, HttpSession session) throws SQLException {
        return orderDetail(orderId, model, session);
    }

    // 주문 취소 (POST)
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam("bo_idx") int boIdx, HttpSession session) {
        Integer b_idx = (Integer) session.getAttribute("b_idx");
        if (b_idx == null) {
            log.warn("b_idx is null. Redirecting to login.");
            return "redirect:/login";
        }

        // 주문 소유자 확인
        List<OrderViewDTO> orders = orderService.getOrderViewByBoIdx(boIdx);
        if (orders.isEmpty() || orders.get(0).getB_idx() != b_idx) {
            return "redirect:/order/orderList?error=notfound";
        }

        orderService.deleteOrder(boIdx);
        return "redirect:/order/orderList?cancel=success";
    }
    // 구매확정 처리 메소드 추가
    @PostMapping("/confirmPurchase")
    public String confirmPurchase(@RequestParam("bo_idx") int boIdx, HttpSession session, Model model) {
        Integer b_idx = (Integer) session.getAttribute("b_idx");
        if (b_idx == null) {
            log.warn("b_idx is null. Redirecting to login.");
            return "redirect:/login";
        }

        // 주문 소유자 확인
        List<OrderViewDTO> orders = orderService.getOrderViewByBoIdx(boIdx);
        if (orders.isEmpty() || orders.get(0).getB_idx() != b_idx) {
            return "redirect:/order/orderList?error=notfound";
        }

        try {
            orderService.confirmPurchase(boIdx);
            return "redirect:/order/orderList?confirm=success";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @Data
    @AllArgsConstructor
    static class ApiResponse {
        private boolean success;
        private String message;
    }
    @ModelAttribute("formatNumber")
    public String formatNumber(Number number) {
        if (number == null) {
            return "";
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.KOREA);
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0", symbols);
        return decimalFormat.format(number);
    }
}