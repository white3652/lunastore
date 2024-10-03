package com.lunastore.vo;

import lombok.Data;

@Data
public class ReviewVO {
    private int i_idx;
    private int b_idx;
    private String b_profile;
    private String b_nickname;

    private int br_idx;
    private int bos_idx;
    private String bos_option;
    private String br_content;
    private int br_star;
    private String br_check;
    private String br_postdate;
    private String br_modifydate;
    private String br_cancel;

    private int buyerId;
    private int itemId;
    private String i_name;
    private String imageSrc;
}