package com.lunastore.dto;

import lombok.Data;

@Data
public class StoreSalesDTO {
    private int s_idx;
    private String storeName;
    private int settledAmount;
    private int unsettledAmount;
    private int totalAmount;
    private String settledAmountFormatted;
    private String unsettledAmountFormatted;
    private String totalAmountFormatted;
}