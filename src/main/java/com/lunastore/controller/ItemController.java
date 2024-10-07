package com.lunastore.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import com.lunastore.common.CustomIntEditor;
import com.lunastore.common.ListPageNav;
import com.lunastore.dto.DeleteItemResponse;
import com.lunastore.dto.ItemDeleteRequest;
import com.lunastore.service.QnaService;
import com.lunastore.service.SellerService;
import com.lunastore.vo.*;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.lunastore.common.PageNav;
import com.lunastore.service.GlobalService;
import com.lunastore.service.ItemService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final GlobalService globalService;
    private final PageNav pageNav;
    private final QnaService qnaService;
    private final SellerService sellerService;
    private final MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(int.class, "i_idx", new CustomIntEditor());
    }
    @GetMapping("/write")
    public String write(Model model, HttpSession session) {
        SellerVO seller = (SellerVO) session.getAttribute("seller");

        if (seller == null) {
            return "redirect:/login";
        }

        model.addAttribute("seller", seller);
        return "seller/service/write";
    }

    @PostMapping("/writeProcess")
    public String writeProcess(ItemVO vo, HttpServletRequest request) {

        String viewPage = "seller/service/write";

        try {
            if (vo.getI_img0() != null && !vo.getI_img0().isEmpty()) {
                vo.setI_saveimg0(globalService.fileNameUpdate(vo.getI_img0()));
            }
            if (vo.getI_img1() != null && !vo.getI_img1().isEmpty()) {
                vo.setI_saveimg1(globalService.fileNameUpdate(vo.getI_img1()));
            }
            if (vo.getI_img2() != null && !vo.getI_img2().isEmpty()) {
                vo.setI_saveimg2(globalService.fileNameUpdate(vo.getI_img2()));
            }
            if (vo.getI_img3() != null && !vo.getI_img3().isEmpty()) {
                vo.setI_saveimg3(globalService.fileNameUpdate(vo.getI_img3()));
            }
            if (vo.getI_img4() != null && !vo.getI_img4().isEmpty()) {
                vo.setI_saveimg4(globalService.fileNameUpdate(vo.getI_img4()));
            }
        } catch (NullPointerException e) {
        }

        int result = itemService.insert(vo);

        if (result == 1) {
            viewPage = "redirect:/seller/dashBoard";
        }
        return viewPage;
    }
    @GetMapping("/list")
    public String list(@ModelAttribute("sVO") SearchVO searchVO, Model model, HttpServletRequest request, Locale locale) {

        if (searchVO.getPageNum() == 0) {
            searchVO.setPageNum(1);
        }

        if (searchVO.getSearchField() == null) {
            searchVO.setSearchField("i_name");
        }

        if (searchVO.getViewNum() == 0) {
            searchVO.setViewNum(9);
        }

        if (searchVO.getOrderByType() == null) {
            searchVO.setOrderByType("pop");
        }

        // 총 개수 조회
        int totalCount = itemService.getListTotalCount(searchVO);
        log.debug("Total count (List): {}", totalCount);
        model.addAttribute("totalCount", totalCount);

        // 새로운 ListPageNav 인스턴스 생성 및 설정
        ListPageNav pageNav = new ListPageNav();
        pageNav.setTotalRows(totalCount);
        pageNav.setPageNum(searchVO.getPageNum());
        pageNav.setRowsPerPage(searchVO.getViewNum());
        pageNav.setPagesPerBlock(5); // 한 블록당 페이지 수 설정
        pageNav.calculatePageNav();
        model.addAttribute("pageNav", pageNav);
        searchVO.calculateOffset();
        List<ItemVO> itemList = itemService.getItems(searchVO);
        log.debug("Fetched itemList size: {}", itemList.size());
        for (ItemVO item : itemList) {
            log.debug("Item: {}", item);
        }
        Map<Integer, String> formattedPriceMap = new HashMap<>();
        DecimalFormat currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.KOREA);

        for (ItemVO item : itemList) {
            try {
                BigDecimal price = new BigDecimal(item.getI_price().replace(",", "").replace("원", ""));
                String formatted = currencyFormatter.format(price);
                formattedPriceMap.put(item.getI_idx(), formatted);
            } catch (NumberFormatException e) {
                formattedPriceMap.put(item.getI_idx(), "₩0");
            }
        }
        List<Map<String, Object>> categories = getTranslatedCategories(locale);
        model.addAttribute("categories", categories);
        model.addAttribute("itemList", itemList);
        model.addAttribute("formattedPriceMap", formattedPriceMap);
        model.addAttribute("requestURI", request.getRequestURI());

        return "buyer/service/list";
    }

    private List<Map<String, Object>> getTranslatedCategories(Locale locale) {
        List<Map<String, Object>> categories = new ArrayList<>();

        Map<Integer, String> mainCategories = new LinkedHashMap<>();
        mainCategories.put(1, "category.digital");
        mainCategories.put(2, "category.interior");
        mainCategories.put(3, "category.food");
        mainCategories.put(4, "category.clothing");
        mainCategories.put(5, "category.lifestyle");

        // 각 메인 카테고리에 해당하는 서브카테고리 c_idx 목록
        Map<Integer, List<Integer>> subCategoryMap = new HashMap<>();
        subCategoryMap.put(1, Arrays.asList(1, 2, 3, 4, 5));
        subCategoryMap.put(2, Arrays.asList(6, 7, 8, 9, 10));
        subCategoryMap.put(3, Arrays.asList(11, 12, 13, 14, 15));
        subCategoryMap.put(4, Arrays.asList(16, 17, 18, 19, 20));
        subCategoryMap.put(5, Arrays.asList(21, 22, 23, 24, 25));

        for (Map.Entry<Integer, String> mainCatEntry : mainCategories.entrySet()) {
            Integer mainCatId = mainCatEntry.getKey();
            String mainCatKey = mainCatEntry.getValue();
            String mainCatName;
            try {
                mainCatName = messageSource.getMessage(mainCatKey, null, locale);
            } catch (NoSuchMessageException e) {
                throw e;
            }

            Map<String, Object> mainCat = new HashMap<>();
            mainCat.put("id", mainCatId);
            mainCat.put("name", mainCatName);

            List<Integer> subCatIds = subCategoryMap.get(mainCatId);
            List<Map<String, Object>> subCats = new ArrayList<>();

            if (subCatIds != null) {
                for (Integer subCatId : subCatIds) {
                    String subCatKey = "subcategory." + subCatId;
                    String subCatName;
                    try {
                        subCatName = messageSource.getMessage(subCatKey, null, locale);
                        log.debug("Loaded sub category: key={}, name={}", subCatKey, subCatName);
                    } catch (NoSuchMessageException e) {
                        log.error("Message key '{}' not found for locale '{}'", subCatKey, locale);
                        subCatName = "알 수 없음";
                    }

                    Map<String, Object> subCat = new HashMap<>();
                    subCat.put("id", subCatId);
                    subCat.put("name", subCatName);
                    subCats.add(subCat);
                }
            }

            mainCat.put("subCategories", subCats);
            categories.add(mainCat);
        }

        return categories;
    }
    private String formatPrice(String i_price) {
        try {
            double price = Double.parseDouble(i_price);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(price) + " ₩";
        } catch (NumberFormatException e) {
            return i_price + " ₩";
        }
    }

    private String formatPrice(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }

    @GetMapping("/view")
    public String view(@ModelAttribute("sVO") SearchVO searchVO, Model model, HttpSession session) {
        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");
        SellerVO seller = (SellerVO) session.getAttribute("seller");
        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("i_idx", searchVO.getI_idx());

        // i_idx 유효성 검증
        if (searchVO.getI_idx() <= 0) {
            log.warn("Invalid i_idx received: " + searchVO.getI_idx());
            model.addAttribute("errorMessage", "유효하지 않은 상품 번호입니다.");
            return "error/invalidItem"; // 커스텀 에러 페이지
        }

        // Item 조회
        ItemVO item = itemService.view(searchVO.getI_idx());
        if (item == null) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            return "error/itemNotFound"; // 커스텀 에러 페이지
        }

        // **판매자 정보 가져오기**
        SellerVO sellerInfo = sellerService.getSellerWithStoreInfo(item.getS_idx());
        if (sellerInfo != null) {
            model.addAttribute("sellerInfo", sellerInfo);
        }

        // 리뷰 리스트 조회
        List<ReviewVO> reviewList = itemService.reviewList(searchVO);
        int[] starArr = new int[5];

        if (reviewList != null) {
            for (ReviewVO review : reviewList) {
                int star = review.getBr_star();
                if (star >= 1 && star <= 5) {
                    starArr[star - 1]++;
                }
                log.debug("Review ID: " + review.getBr_idx() + ", Star: " + review.getBr_star());
            }
            for (int i = 0; i < starArr.length; i++) {
                log.debug("starArr[" + i + "] = " + starArr[i]);
            }
        }

        // 날짜 업데이트
        item.setI_modifydate(globalService.dateUpdate(item.getI_modifydate()));
        item.setI_regdate(globalService.dateUpdate(item.getI_regdate()));

        // 이미지 및 옵션 배열 설정
        String[] imgArr = {
                item.getI_saveimg0(),
                item.getI_saveimg1(),
                item.getI_saveimg2(),
                item.getI_saveimg3(),
                item.getI_saveimg4()
        };
        String[] optionArr = {
                item.getI_option0(),
                item.getI_option1(),
                item.getI_option2(),
                item.getI_option3(),
                item.getI_option4()
        };

        // 모델에 데이터 추가
        model.addAttribute("imgArr", imgArr);
        model.addAttribute("optionArr", optionArr);
        model.addAttribute("item", item);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("starArr", starArr);
        int avgStarInt = (int) item.getAvg_star();
        model.addAttribute("avgStarInt", avgStarInt);
        List<QnaVO> qnaList = null;
        try {
            qnaList = qnaService.getQnasByItemId(searchVO.getI_idx());
        } catch (SQLException e) {
            log.error("Error fetching Q&A list: ", e);
            model.addAttribute("errorMessage", "Q&A 목록을 불러오는 중 오류가 발생했습니다.");
            return "error/generalError"; // 일반 오류 페이지
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("qnaList", qnaList);

        boolean isBuyer = buyer != null;
        boolean isSellerUser = seller != null && !isBuyer;

        log.debug("isBuyer: " + isBuyer + ", isSeller: " + isSellerUser);

        model.addAttribute("isBuyer", isBuyer);
        model.addAttribute("isSeller", isSellerUser);

        log.debug("isBuyer: " + isBuyer + ", isSeller: " + isSellerUser);

        return "buyer/service/view";
    }


    @PostMapping("/insertReview")
    @ResponseBody
    public Map<String, Object> insertReview(@RequestBody ReviewVO reviewVO) {
        log.debug("리뷰 등록 시도: bos_idx=" + reviewVO.getBos_idx() + ", br_star=" + reviewVO.getBr_star() + ", br_content=" + reviewVO.getBr_content());
        Map<String, Object> response = new HashMap<>();
        boolean success = itemService.insertReview(reviewVO);
        response.put("success", success);
        response.put("message", success ? "리뷰가 성공적으로 등록되었습니다." : "이미 리뷰가 등록되어 있거나 유효하지 않은 bos_idx입니다.");
        return response;
    }

    @PostMapping("/deleteItemProcess")
    @ResponseBody
    public ResponseEntity<DeleteItemResponse> deleteItemProcess(@RequestBody ItemDeleteRequest request) {
        try {
            int result = itemService.deleteItem(request.getI_idx());
            if(result > 0){
                return ResponseEntity.ok(new DeleteItemResponse(true, "삭제가 완료되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DeleteItemResponse(false, "삭제에 실패했습니다."));
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new DeleteItemResponse(false, "해당 아이템을 삭제할 수 없습니다. 관련 주문 상태나 이미지가 존재합니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeleteItemResponse(false, "알 수 없는 오류가 발생했습니다."));
        }
    }



}