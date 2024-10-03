package com.lunastore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lunastore.service.ItemService;
import com.lunastore.vo.BuyerVO;
import com.lunastore.vo.ItemVO;
import com.lunastore.vo.SearchVO;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {

        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");
        model.addAttribute("buyer", buyer);

        SearchVO categoryA = new SearchVO();
        SearchVO categoryB = new SearchVO();
        SearchVO categoryC = new SearchVO();
        SearchVO categoryD = new SearchVO();
        SearchVO categoryE = new SearchVO();

        categoryA.setP_idx(1);
        categoryB.setP_idx(2);
        categoryC.setP_idx(3);
        categoryD.setP_idx(4);
        categoryE.setP_idx(5);

        List<ItemVO> listA = itemService.getItems(categoryA);
        List<ItemVO> listB = itemService.getItems(categoryB);
        List<ItemVO> listC = itemService.getItems(categoryC);
        List<ItemVO> listD = itemService.getItems(categoryD);
        List<ItemVO> listE = itemService.getItems(categoryE);

        List<ItemVO> allItems = new ArrayList<>();
        allItems.addAll(listA);
        allItems.addAll(listB);
        allItems.addAll(listC);
        allItems.addAll(listD);
        allItems.addAll(listE);
        Collections.shuffle(allItems);

        model.addAttribute("allItems", allItems);

        model.addAttribute("listA", listA);
        model.addAttribute("listB", listB);
        model.addAttribute("listC", listC);
        model.addAttribute("listD", listD);
        model.addAttribute("listE", listE);

        return "index";
    }
}