package com.lunastore.controller;
import com.lunastore.service.*;
import com.lunastore.vo.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

@Slf4j
@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final ItemService itemService;
    private final QnaService qnaService;

    @PostMapping("/submit")
    @Transactional
    public String submitQuestion(@RequestParam("i_idx") Integer itemId, @RequestParam("title") String title, @RequestParam("question") String question, HttpSession session, RedirectAttributes redirectAttributes) {
        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");

        if (buyer == null) {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요합니다.");
            return "redirect:/buyer/login";
        }

        ItemVO item = itemService.view(itemId);
        if (item == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 상품입니다.");
            return "redirect:/item/view?i_idx=" + itemId;
        }

        QnaVO qnaVO = new QnaVO();
        qnaVO.setI_idx(itemId);
        qnaVO.setB_idx(buyer.getB_idx());
        qnaVO.setS_idx(item.getS_idx());
        qnaVO.setQna_title(title);
        qnaVO.setQna_question(question);

        try {
            qnaService.submitQuestion(qnaVO);
        } catch (SQLException e) {
            log.error("Error submitting question: ", e);
            redirectAttributes.addFlashAttribute("message", "질문 제출에 실패했습니다.");
            return "redirect:/item/view?i_idx=" + itemId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("message", "질문이 성공적으로 제출되었습니다.");
        return "redirect:/item/view?i_idx=" + itemId;
    }

    // 답변 제출 처리
    @PostMapping("/{qnaIdx}/answer")
    @Transactional
    public String submitAnswer(@PathVariable("qnaIdx") Integer qnaIdx, @RequestParam("answer") String answer, HttpSession session, RedirectAttributes redirectAttributes) {
        SellerVO seller = (SellerVO) session.getAttribute("seller");

        if (seller == null) {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요합니다.");
            return "redirect:/seller/login";
        }

        QnaVO qna = null;
        try {
            qna = qnaService.getQnaById(qnaIdx);
        } catch (SQLException e) {
            log.error("Error fetching Q&A: ", e);
            redirectAttributes.addFlashAttribute("message", "Q&A 조회에 실패했습니다.");
            return "redirect:/item/view?i_idx=" + (qna != null ? qna.getI_idx() : "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (qna == null) {
            redirectAttributes.addFlashAttribute("message", "유효하지 않은 Q&A입니다.");
            return "redirect:/item/view";
        }

        // 현재 판매자가 해당 Q&A의 판매자인지 확인
        if (!qna.getS_idx().equals(seller.getS_idx())) {
            redirectAttributes.addFlashAttribute("message", "이 Q&A에 대한 답변 권한이 없습니다.");
            return "redirect:/item/view?i_idx=" + qna.getI_idx();
        }

        try {
            qnaService.answerQuestion(qnaIdx, answer);
        } catch (SQLException e) {
            log.error("Error submitting answer: ", e);
            redirectAttributes.addFlashAttribute("message", "답변 제출에 실패했습니다.");
            return "redirect:/item/view?i_idx=" + qna.getI_idx();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("message", "답변이 성공적으로 제출되었습니다.");
        return "redirect:/item/view?i_idx=" + qna.getI_idx();
    }
}