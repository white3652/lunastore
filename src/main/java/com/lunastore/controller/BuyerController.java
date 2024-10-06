package com.lunastore.controller;

import com.lunastore.service.GlobalService;
import com.lunastore.vo.AddressVO;
import com.lunastore.vo.BuyerVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lunastore.service.BuyerService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;
    private final GlobalService globalService;

    @GetMapping("/join")
    public String buyerJoin() {
        return "buyer/user/buyerJoin";
    }

    @PostMapping("/buyerJoinProcess")
    public String buyerJoinProcess(HttpServletRequest request, BuyerVO buyerVO) {
        buyerVO.setB_regdate(new Date());
        // 중복된 값이 있으면 첫 번째 값만 사용
        String firstName = buyerVO.getB_firstname().split(",")[0];
        String lastName = buyerVO.getB_lastname().split(",")[0];
        String tel = buyerVO.getB_tel().split(",")[0];
        String birth = buyerVO.getB_birth().split(",")[0];

        // BuyerVO에 첫 번째 값만 설정
        buyerVO.setB_firstname(firstName);
        buyerVO.setB_lastname(lastName);
        buyerVO.setB_tel(tel);
        buyerVO.setB_birth(birth);

        // 데이터베이스에 저장
        if (buyerService.join(buyerVO) == 1 && buyerService.infoState(buyerVO) == 1) {
            HttpSession session = request.getSession();
            session.setAttribute("b_email", buyerVO.getB_email());

            // 가입이 완료되면 바로 메인 페이지로 리다이렉트
            return "redirect:/";
        }
        return "buyer/user/buyerJoin";  // 가입 실패 시 다시 가입 페이지로 이동
    }

    @GetMapping("/login")
    public String buyerLogin() {
        return "buyer/user/buyerLogin";
    }

    @PostMapping("/buyerLoginProcess")
    public String buyerLoginProcess(@RequestParam String b_email, @RequestParam String b_pw, Model model, HttpServletRequest request) {

        BuyerVO buyerVO = buyerService.login(b_email, b_pw);

        // 로그인 실패 시
        if (buyerVO == null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "buyer/user/buyerLogin";  // 로그인 페이지로 다시 반환
        }

        // 계정이 탈퇴된 경우
        if ("Y".equals(buyerVO.getB_cancel())) {
            model.addAttribute("errorMessage", "탈퇴된 계정입니다.");
            return "buyer/user/buyerLogin";  // 로그인 페이지로 다시 반환
        }

        // 로그인 성공 시 세션에 'buyer' 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("buyer", buyerVO); // 'buyer' 속성 설정
        session.setAttribute("b_idx", buyerVO.getB_idx());
        // 추가적으로 필요한 정보가 있다면 buyerVO에 포함시켜 세션에 저장
        // 예: session.setAttribute("b_email", buyerVO.getB_email());

        return "redirect:/";  // 홈 또는 다른 페이지로 리다이렉트
    }

    @GetMapping("/buyerLogout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/buyerServiceInfo")
    public String buyerServiceInfo(@RequestParam int b_idx, Model model) {
        try {
            BuyerVO buyer = buyerService.getBuyerById(b_idx);
            model.addAttribute("buyer", buyer);  // buyer 정보 추가
            model.addAttribute("address", buyerService.getAddress(b_idx));
        } catch (SQLException e) {
            e.printStackTrace();  // 디버깅을 위해 오류 출력
            model.addAttribute("error", "Error occurred while fetching buyer information.");
        }
        return "buyer/user/buyerServiceInfo";
    }

    @PostMapping("/buyerInsertAddressProcess")
    public String buyerInsertAddressProcess(AddressVO addressVO, RedirectAttributes ra) {
        if (buyerService.insertAddress(addressVO) == 1) {
            ra.addAttribute("b_idx", addressVO.getB_idx());
            return "redirect:/buyer/buyerServiceInfo";
        }
        return "buyer/user/buyerServiceInfo";
    }

    @PostMapping("/buyerInsertContactProcess")
    public String buyerInsertContactProcess(AddressVO addressVO, RedirectAttributes ra, Model model) {
        model.addAttribute("address", buyerService.insertContact(addressVO));
        ra.addAttribute("b_idx", addressVO.getB_idx());
        return "redirect:/buyer/buyerServiceInfo";
    }
    @GetMapping("/buyerUpdatePage")
    public String buyerUpdatePage(HttpSession session, Model model) {
        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");

        model.addAttribute("buyer", buyer);

        return "buyer/user/buyerUpdatePage";
    }

    @GetMapping("/buyerUpdatePage1")
    public String buyerUpdatePage1(@RequestParam int b_idx, Model model) throws SQLException {

        BuyerVO buyer = buyerService.getBuyerById(b_idx);

        if (buyer.getB_birth() != null && !buyer.getB_birth().isEmpty()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(buyer.getB_birth(), inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            String formattedDate = birthDate.format(outputFormatter);

            model.addAttribute("formattedDate", formattedDate);
        }

        model.addAttribute("buyer", buyer);

        return "buyer/user/buyerUpdatePage1";
    }

    @GetMapping("/buyerUpdatePage2")
    public String buyerUpdatePage2(HttpSession session, Model model) {
        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");

        model.addAttribute("buyer", buyer);

        return "buyer/user/buyerUpdatePage2";
    }

    @GetMapping("/buyerUpdatePage3")
    public String buyerUpdatePage3(HttpSession session, Model model) {
        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");

        model.addAttribute("buyer", buyer);

        return "buyer/user/buyerUpdatePage3";
    }

    @PostMapping("/buyerUpdateProcess")
    public String buyerUpdateProcess(BuyerVO buyerVO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String viewPage = determineUpdatePage(buyerVO);

        BuyerVO newVO = buyerService.update(buyerVO);
        if (newVO != null) {
            updateSessionWithBuyer(request.getSession(), newVO);

            redirectAttributes.addFlashAttribute("buyer", newVO);

            return viewPage;
        }
        return "buyer/user/buyerUpdatePage";
    }

    @PostMapping("/buyerInfoUpdateProcess")
    public String buyerInfoUpdateProcess(BuyerVO buyerVO, HttpServletRequest request) throws SQLException {
        int b_idx = buyerVO.getB_idx();
        String viewPage = null;

        // 페이지 선택 로직
        if (buyerVO.getB_tempprofile() != null || buyerVO.getB_nickname() != null) {
            viewPage = "buyer/user/buyerUpdatePage?b_idx=" + b_idx;
        } else if (buyerVO.getB_gender() != null) {
            viewPage = "buyer/user/buyerUpdatePage1?b_idx=" + b_idx;
        }

        try {
            MultipartFile tempProfile = buyerVO.getB_tempprofile();
            if (tempProfile != null && !tempProfile.isEmpty()) {
                String uploadedFileName = globalService.fileNameUpdate(tempProfile);
                buyerVO.setB_profile(uploadedFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (buyerVO.getB_gender() != null) {
            buyerVO.setB_gender(buyerVO.getB_gender());
        }

        BuyerVO newVO = buyerService.updateBuyerInfo(buyerVO);

        if (newVO != null) {
            HttpSession session = request.getSession();
            session.removeAttribute("buyer");
            session.setAttribute("buyer", newVO);

            if (buyerVO.getB_tempprofile() != null || buyerVO.getB_nickname() != null) {
                viewPage = "redirect:/buyer/buyerUpdatePage?b_idx=" + b_idx;
            } else if (buyerVO.getB_gender() != null) {
                viewPage = "redirect:/buyer/buyerUpdatePage1?b_idx=" + b_idx;
            }
        }

        return viewPage;
    }

    @GetMapping("/buyerCancelProcess")
    public String buyerCancelProcess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");
        if (buyerService.cancel(buyer.getB_idx()) == 2) {
            session.invalidate();
            return "redirect:/";
        }
        return "buyer/user/buyerUpdatePage";
    }

    @PostMapping("/nicknameCheckProcess")
    @ResponseBody
    public int nicknameCheckProcess(@RequestParam String b_nickname) {
        return buyerService.nicknameCheck(b_nickname);
    }

    @PostMapping("/emailCheckProcess")
    @ResponseBody
    public int emailCheckProcess(@RequestParam String b_email) {
        return buyerService.emailCheck(b_email);
    }

    @PostMapping("/telCheckProcess")
    @ResponseBody
    public int telCheckProcess(@RequestParam String b_tel) {
        System.out.println("telCheckProcess 호출됨: " + b_tel);
        return buyerService.telCheck(b_tel);
    }

    @GetMapping("/findPw")
    public String findPw() {
        return "buyer/user/buyerFindPw";
    }

    @PostMapping("/findPwProcess")
    @ResponseBody
    public ResponseEntity<Map<String, String>> findPwProcess(@ModelAttribute BuyerVO buyerVO, Locale locale) throws Exception {
        String result = buyerService.findPw(buyerVO, locale);
        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/passwordCheckProcess")
    @ResponseBody
    public int passwordCheckProcess(@RequestParam int b_idx, @RequestParam String b_pw) {
        System.out.println("passwordCheckProcess 호출됨: b_idx=" + b_idx + ", b_pw=" + b_pw);
        return buyerService.passwordCheck(b_idx, b_pw);
    }

    @PostMapping("/joinEmailProcess")
    @ResponseBody
    public String joinEmailProcess(@RequestParam String b_email, Locale locale) {
        return buyerService.joinEmail(b_email, locale);
    }

    @GetMapping("/buyerVerifyEmail")
    public String buyerVerifyEmail() {
        return "buyer/user/buyerVerifyEmail";
    }

    @PostMapping("/buyerVerifyEmailProcess")
    @ResponseBody
    public int buyerVerifyEmailProcess(@RequestParam String b_email) {
        return buyerService.verifyEmail(b_email);
    }

    private void updateSessionWithBuyer(HttpSession session, BuyerVO buyerVO) {
        session.removeAttribute("buyer");
        session.setAttribute("buyer", buyerVO);
    }

    private String determineUpdatePage(BuyerVO buyerVO) {
        int b_idx = buyerVO.getB_idx();
        System.out.println("Redirecting to page based on condition, b_idx: " + b_idx);
        if (buyerVO.getB_firstname() != null && buyerVO.getB_lastname() != null) {
            return "redirect:/buyer/buyerUpdatePage1?b_idx=" + b_idx;
        } else if (buyerVO.getB_tel() != null || buyerVO.getB_pw() != null) {
            return "redirect:/buyer/buyerUpdatePage2?b_idx=" + b_idx;
        } else {
            return "redirect:/buyer/buyerUpdatePage1?b_idx=" + b_idx;
        }
    }
}