package com.lunastore.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DeliveryVO {
    private int bo_idx;
    private int b_idx;
    private String bo_item_name;
    private String bo_name;
    private String bo_zipcode;
    private String bo_address;
    private String bo_contact;
    private int bos_state;
    private int bos_price;
    private Date bos_postdate;

}
