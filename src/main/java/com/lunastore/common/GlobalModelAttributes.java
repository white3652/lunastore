package com.lunastore.common;


import com.lunastore.service.GlobalService;
import com.lunastore.service.ItemService;
import com.lunastore.vo.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private ItemService itemService;

    @Autowired
    private GlobalService globalService;

    @ModelAttribute
    public void addPageNav(Model model, @ModelAttribute("sVO") SearchVO searchVO) {
        PageNav pageNav = new PageNav();
        pageNav.setTotalRows(itemService.getTotalCount(searchVO));
        PageNav updatedPageNav = globalService.setPageNav(pageNav, searchVO.getPageNum(), searchVO.getPageBlock(), searchVO.getViewNum());
        model.addAttribute("pageNav", updatedPageNav);
        // 로그 추가
        System.out.println("pageNav 추가됨: " + updatedPageNav);
    }
}