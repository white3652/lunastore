package com.lunastore.dto;


import lombok.Data;

@Data
public class ReviewDTO {
    private int br_idx;
    private int bos_idx;
    private String br_content;
    private int br_star;
    private String br_postdate;
    private String br_modifydate;
    private String br_cancel;

    private int buyerId;
    private int itemId;

    private String imageSrc;
    private String itemName;
}