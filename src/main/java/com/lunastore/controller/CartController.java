package com.lunastore.controller;

import java.sql.SQLException;
import java.util.*;
import java.text.NumberFormat;
import java.util.stream.Collectors;

import com.lunastore.service.BuyerService;
import com.lunastore.service.ItemService;
import com.lunastore.vo.BuyerVO;
import com.lunastore.vo.ItemVO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lunastore.service.CartService;
import com.lunastore.vo.CartVO;

import lombok.RequiredArgsConstructor;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final BuyerService buyerService;
    private final ItemService itemService;

    @PostMapping("/insertCart")
    @ResponseBody
    public Map<String, Object> insertCart(CartVO cartVO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        BuyerVO buyerVO = (BuyerVO) session.getAttribute("buyer");

        if (buyerVO == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        cartVO.setB_idx(buyerVO.getB_idx());

        try {
            int result = cartService.insertCart(cartVO);
            if (result > 0) {
                response.put("success", true);
                response.put("message", "장바구니에 상품이 추가되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "장바구니에 상품을 추가하는데 실패했습니다.");
            }
        } catch (DuplicateKeyException e) {
            response.put("success", false);
            response.put("message", "장바구니에 동일한 상품이 존재합니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "장바구니에 상품을 추가하는데 실패했습니다.");
        }
        return response;
    }
    @PostMapping("/likeItem")
    @ResponseBody
    public int likeItem(CartVO cartVO) {
        return cartService.likeItem(cartVO);
    }

    @PostMapping("/cartList")
    @ResponseBody
    public List<CartVO> cartList(int b_idx) {
        return cartService.cartList(b_idx);
    }

    @PostMapping("/updateQuantity")
    @ResponseBody
    public Map<String, Object> updateCartQuantity(@RequestBody CartVO cartVO) {
        System.out.println("b_idx: " + cartVO.getB_idx());
        System.out.println("i_idx: " + cartVO.getI_idx());
        Map<String, Object> result = new HashMap<>();
        try {
            int updateResult = cartService.updateCartQuantity(cartVO);
            result.put("success", updateResult > 0);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }
    @GetMapping("/cart")
    public String cart(int b_idx, Model model) throws SQLException {
        List<CartVO> cartList = cartService.cartList(b_idx);
        BuyerVO buyer = buyerService.getBuyerById(b_idx);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        // 가격 포맷팅
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
        int itemCount = cartList.size();
        model.addAttribute("cartList", formattedCartList);
        model.addAttribute("buyer", buyer);
        model.addAttribute("interestList", interestList);
        model.addAttribute("recommendList", recommendList);
        model.addAttribute("itemCount", itemCount);
        log.info("Interest List Size: {}", interestList.size());
        log.info("Recommend List Size: {}", recommendList.size());
        log.info("Item Count: {}", itemCount);
        return "buyer/service/buyerItemCart";
    }

    @PostMapping("/updateQuantityWithPrice")
    @ResponseBody
    public Map<String, Object> updateCartQuantityWithPrice(@RequestBody CartVO cartVO) {
        Map<String, Object> result = new HashMap<>();

        boolean isUpdated = cartService.updateCartQuantity(cartVO.getI_idx(), cartVO.getI_count());

        if (isUpdated) {
            int totalPrice = cartService.calculateTotalPrice(cartVO.getI_idx(), cartVO.getI_count());
            result.put("success", true);
            result.put("totalPrice", totalPrice);
        } else {
            result.put("success", false);
        }

        return result;
    }
    @PostMapping("/removeItem")
    @ResponseBody
    public ResponseEntity<String> deleteCart(@RequestBody CartVO cartVO) {
        int result = cartService.deleteCart(cartVO);
        if (result > 0) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("fail");
        }
    }
}