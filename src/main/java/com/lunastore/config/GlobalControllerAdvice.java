package com.lunastore.config;


import com.lunastore.vo.BuyerVO;
import com.lunastore.vo.SellerVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 판매자 정보 추가
            SellerVO seller = (SellerVO) session.getAttribute("seller");
            if (seller != null) {
                model.addAttribute("isSeller", true);
                model.addAttribute("seller", seller);
            } else {
                model.addAttribute("isSeller", false);
            }

            // 구매자 정보 추가 (필요시)
            BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");
            if (buyer != null) {
                model.addAttribute("isBuyer", true);
                model.addAttribute("buyer", buyer);
            } else {
                model.addAttribute("isBuyer", false);
            }
        } else {
            model.addAttribute("isSeller", false);
            model.addAttribute("isBuyer", false);
        }
    }
}