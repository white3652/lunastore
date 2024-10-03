package com.lunastore.service;

import com.lunastore.dto.SettlementDTO;

import java.util.List;

public interface SettlementService {
    List<SettlementDTO> getSettlementStatus(String startDate, String endDate, int s_idx);
    List<SettlementDTO> getSettlementStatusPaged(String startDate, String endDate, int page, int size, int s_idx);
    int countSettlementData(String startDate, String endDate, int s_idx);
    boolean updateSettlement(SettlementDTO settlementDTO);
}