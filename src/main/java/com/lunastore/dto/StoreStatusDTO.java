package com.lunastore.dto;

import lombok.Data;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

@Data
public class StoreStatusDTO {
    private LocalDate date;
    private int orderCount;
    private int inquiryCount;
    private int reviewCount;
    private int salesAmount;
    private String formattedSalesAmount;
    public void setSalesAmount(int salesAmount) {
        this.salesAmount = salesAmount;
        this.formattedSalesAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(salesAmount);
    }
}
