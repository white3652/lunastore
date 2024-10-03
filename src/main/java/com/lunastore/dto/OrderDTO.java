package com.lunastore.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private int bo_idx;
    private int b_idx;
    private String bo_itemname;
    private String bo_name;
    private String bo_zipcode;
    private String bo_address;
    private String bo_contact;
    private Date bo_orderdate;

    private int bos_idx;
    private int i_idx;
    private String bos_option;
    private double bos_price;
    private int bos_count;
    private int bos_settle;
    private int bos_state;
    private Date bos_postdate;

    private String I_saveimg0;
    private String I_saveimg1;
    private String I_saveimg2;
    private String I_saveimg3;

    private String itemImage;
    private String bos_state_text;

    private String i_name;
    public String getFormattedTotalPrice() {
        double totalPrice = this.bos_price * this.bos_count;
        return String.format("%,d", Math.round(totalPrice));
    }

}