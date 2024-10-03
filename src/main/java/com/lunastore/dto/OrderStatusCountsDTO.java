package com.lunastore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusCountsDTO {
    private int purchaseConfirmed;
    private int confirmationWaiting;
}
