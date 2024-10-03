package com.lunastore.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private String merchantUid;
    private int amount;
    private int bo_idx;
}