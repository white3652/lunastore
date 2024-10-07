package com.lunastore.service;


import com.lunastore.dao.OrderDAO;
import com.lunastore.dao.SettlementDAO;
import com.lunastore.dto.SettlementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {
    private final SettlementDAO settlementDAO;

    @Override
    public List<SettlementDTO> getSettlementStatus(String startDate, String endDate, int s_idx) {
        return settlementDAO.getSettlementData(startDate, endDate, s_idx);
    }

    @Override
    public List<SettlementDTO> getSettlementStatusPaged(String startDate, String endDate, int page, int size, int s_idx) {
        int offset = (page - 1) * size;
        return settlementDAO.getSettlementDataPaged(startDate, endDate, offset, size, s_idx);
    }

    @Override
    public int countSettlementData(String startDate, String endDate, int s_idx) {
        return settlementDAO.countSettlementData(startDate, endDate, s_idx);
    }

    @Override
    @Transactional
    public boolean updateSettlement(SettlementDTO settlementDTO) {
        return settlementDAO.updateSettlement(settlementDTO) > 0;
    }
}