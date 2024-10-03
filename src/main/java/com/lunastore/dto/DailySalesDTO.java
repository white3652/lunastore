package com.lunastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesDTO {
    private String date; // 날짜 (예: "2023-09-27")
    private int salesAmount; // 매출액
}