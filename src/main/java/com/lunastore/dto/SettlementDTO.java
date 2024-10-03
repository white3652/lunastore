package com.lunastore.dto;

import lombok.Data;

@Data
public class SettlementDTO {
    private String date; // 날짜
    private int confirmedOrders; // 구매확정 건수 (bos_state = 10)
    private int pendingOrders; // 확정대기 건수 (bos_state = 1,2,3,4)
    private double todaySettlement; // 오늘정산 금액 (bos_state = 10)
    private double scheduledSettlement; // 정산예정 금액 (bos_state = 1,2,3,4)

    private int boIdx; // 정산 업데이트 시 필요
    private String bosOption; // 정산 업데이트 시 필요
}