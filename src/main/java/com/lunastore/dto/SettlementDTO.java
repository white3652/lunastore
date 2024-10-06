package com.lunastore.dto;

import lombok.Data;

@Data
public class SettlementDTO {
    private String date;
    private int confirmedOrders;
    private int pendingOrders;
    private double todaySettlement;
    private double scheduledSettlement;
    private int boIdx;
    private String bosOption;
}