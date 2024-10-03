package com.lunastore.service;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Random;

import com.lunastore.common.PageNav;
import com.lunastore.vo.SearchVO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lunastore.dao.BuyerDAO;
import com.lunastore.vo.AddressVO;
import com.lunastore.vo.BuyerVO;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final BuyerDAO dao;
    private final JavaMailSenderImpl mailSender;
    private final MessageSource messageSource;
    @Override
    public int join(BuyerVO buyerVO) {
        try {
            return dao.join(buyerVO);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int infoState(BuyerVO buyerVO) {
        try {
            if (dao.info(buyerVO.getB_idx()) == 1 && dao.state(buyerVO.getB_idx()) == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public BuyerVO login(String b_email, String b_pw) {
        try {
            String storedPassword = dao.getPassword(b_email);
            if (storedPassword != null && storedPassword.equals(b_pw)) {
                BuyerVO buyerVO = dao.login(b_email);
                dao.lastLoginDate(buyerVO.getB_idx());
                return buyerVO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BuyerVO update(BuyerVO buyerVO) {
        try {
            return dao.updateBuyer(buyerVO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AddressVO getAddress(int b_idx) {
        return dao.getAddress(b_idx);
    }

    @Override
    public int insertAddress(AddressVO addressVO) {
        try {
            dao.changeDefaultAddress(addressVO.getB_idx());
            return dao.insertAddress(addressVO);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public AddressVO insertContact(AddressVO addressVO) {
        try {
            return dao.insertContact(addressVO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int cancel(int b_idx) {
        try {
            return dao.cancel(b_idx);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int nicknameCheck(String b_nickname) {
        try {
            return dao.nicknameCheck(b_nickname);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int emailCheck(String b_email) {
        try {
            return dao.emailCheck(b_email);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int telCheck(String b_tel) {
        try {
            return dao.telCheck(b_tel);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void sendEmail(BuyerVO vo, String div, Locale locale) {
        String fromEmail = "white3652@naver.com";
        String subject = "";
        String msg = "";

        if ("findpw".equals(div)) {
            subject = messageSource.getMessage("email.findpw.subject", null, locale);
            msg += messageSource.getMessage("email.findpw.msg", null, locale);
            msg += messageSource.getMessage("email.findpw.temp", new Object[]{vo.getB_pw()}, locale);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(vo.getB_email());
        message.setFrom(fromEmail);
        message.setSubject(subject);
        message.setText(msg);

        try {
            mailSender.send(message);
            dao.updatePassword(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findPw(BuyerVO vo, Locale locale) throws Exception {
        if (dao.matchTelEmail(vo) == 1) {
            String newPw = generateRandomPassword();
            vo.setB_pw(newPw);
            dao.updatePassword(vo);
            sendEmail(vo, "findpw", locale);
            return messageSource.getMessage("email.pw.sent", null, locale);
        } else {
            return messageSource.getMessage("email.pw.mismatch", null, locale);
        }
    }

    private String generateRandomPassword() {
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            password.append(lowerCaseChars.charAt(random.nextInt(lowerCaseChars.length())));
        }
        for (int i = 0; i < 4; i++) {
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        return password.toString();
    }

    @Override
    public String joinEmail(String b_email, Locale locale) {
        int authNumber = (int) (Math.random() * 888889) + 111111;

        String setFrom = "white3652@naver.com";
        String title = messageSource.getMessage("email.join.subject", null, locale);
        String content = messageSource.getMessage("email.join.content", new Object[]{authNumber}, locale);

        mailSend(setFrom, b_email, title, content);

        return Integer.toString(authNumber);
    }

    private void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int verifyEmail(String b_email) {
        try {
            return dao.verifyEmail(dao.getB_idx(b_email));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int passwordCheck(int b_idx, String b_pw) {
        try {
            String storedPassword = dao.getPw(b_idx);
            return storedPassword.equals(b_pw) ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public BuyerVO getBuyerById(int bIdx) throws SQLException {
        return dao.getBuyer(bIdx);
    }
    @Override
    public BuyerVO updateBuyerInfo(BuyerVO buyerVO) throws SQLException {
        return dao.infoUpdate(buyerVO);
    }

}