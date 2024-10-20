package com.lunastore.vo;

import lombok.Data;

@Data
public class AddressVO {
    private Integer ba_idx;
    private int b_idx;
    private String ba_firstname;
    private String ba_lastname;
    private String ba_zipcode;
    private String ba_address;
    private String ba_restaddress;
    private String ba_contact;
    private String b_contact;
    private String ba_check;
}