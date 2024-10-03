package com.lunastore.vo;

import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CartVO {
    private int b_idx;
    private int i_idx;
    private String i_name;
    private String i_option;
    private String i_price;
    private String i_img;
    private Date savedate;
    private int i_count;
    private int totalPrice;
    private double subtotal;
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
    public double getSubtotal() {
        try {
            return Double.parseDouble(i_price) * i_count;
        } catch (NumberFormatException e) {
            log.error("Invalid i_price value: {}", i_price, e);
            return 0.0;
        }
    }
}