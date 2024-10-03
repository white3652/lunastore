package com.lunastore.dto;

import com.lunastore.vo.OrderStateVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderViewDTO {
    private int bo_idx;
    private int b_idx;
    private String bo_itemname;
    private String bo_name;
    private String bo_zipcode;
    private String bo_address;
    private String bo_contact;
    private Date bo_orderdate;
    private Integer i_idx;
    private List<OrderStateVO> orderStateVO;
    private double totalPrice;
    private String ba_firstname;
    private String ba_lastname;
    private String ba_zipcode;
    private String ba_address;
    private String ba_contact;
    private String bos_state_text;
    private String itemImage;
    private String i_name;
    private String i_saveimg0;
    private int bos_count;
    private double bos_price;
    private int bos_state;
}