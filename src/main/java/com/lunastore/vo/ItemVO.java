package com.lunastore.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class ItemVO {
    private int c_idx;
    private String c_name;
    private BigDecimal formattedPrice;
    private int s_idx;
    private String s_profile;
    private String s_storename;
    private String s_storeintro;

    private int i_idx;
    private String i_name;
    private String i_price;
    private String i_explain;
    private String i_count;
    private String i_state;
    private String i_option0;
    private String i_option1;
    private String i_option2;
    private String i_option3;
    private String i_option4;
    private String i_saveimg0;
    private String i_saveimg1;
    private String i_saveimg2;
    private String i_saveimg3;
    private String i_saveimg4;
    private String i_thumbnail;
    private Date i_regdate;
    private Date i_modifydate;
    private double avg_star;
    private int comment_count;
    private int orderCount;
    private int inquiryCount;
    private int reviewCount;
    private Integer salesAmount;
    private MultipartFile i_img0;
    private MultipartFile i_img1;
    private MultipartFile i_img2;
    private MultipartFile i_img3;
    private MultipartFile i_img4;
    private String formattedSalesAmount;
    public double getNumericPrice() {
        if (i_price != null && !i_price.isEmpty()) {
            try {
                return Double.parseDouble(i_price.replace(",", "").replace("원", ""));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    public String getFormattedPrice() {
        if (i_price != null && !i_price.isEmpty()) {
            BigDecimal price = new BigDecimal(i_price.replace(",", "").replace("원", ""));
            DecimalFormat currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.KOREA);
            return currencyFormatter.format(price);
        }
        return "₩0";
    }
}