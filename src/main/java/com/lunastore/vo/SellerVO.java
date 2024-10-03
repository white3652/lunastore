package com.lunastore.vo;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class SellerVO {
    private int s_idx;
    private String s_businessnum;
    private String s_pw;
    private String s_firstname;
    private String s_lastname;
    private String s_email;
    private String s_tel;
    private String s_birth;
    private String s_zipcode;
    private String s_address;
    private String s_restaddress;
    private Date s_regdate;
    private Date s_lastlogindate;
    private Date s_modifydate;
    private Date s_pwmodifydate;

    private String s_cancel;
    private String s_check;
    private String s_terms;

    private String s_profile;
    private String s_storename;
    private String s_storeintro;
    private String s_memo;

    private MultipartFile s_tempprofile;
}