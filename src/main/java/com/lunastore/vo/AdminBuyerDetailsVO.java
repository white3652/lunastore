package com.lunastore.vo;


import java.util.Date;
import lombok.Data;

@Data
public class AdminBuyerDetailsVO {
    private int b_idx;
    private String b_firstname;
    private String b_lastname;
    private String b_tel;
    private String b_email;
    private Date b_birth;
    private String b_pw;
    private Date b_regdate;
    private Date b_lastlogindate;
    private Date b_pwmodifydate;
    private Date b_modifydate;
    private String b_cancel;
    private String b_check;
    private String b_terms;
    private String b_gender;
    private Integer b_grade;
    private String b_nickname;
    private String b_profile;
    private int b_point;
    private String ba_address;
    private String ba_zipcode;
    private String ba_restaddress;
    private String ba_contact;
    private int purchaseCount;
    private double totalPurchaseAmount;
    private AddressVO address;
}