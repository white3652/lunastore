package com.lunastore.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ItemDTO {
    private int i_idx;
    private String i_name;
    private double i_price;
    private String i_explain;
    private int i_count;
    private int i_state;
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

    private String c_name;
    private int c_idx;
    private String s_profile;
    private String s_storename;
    private String s_storeintro;
}