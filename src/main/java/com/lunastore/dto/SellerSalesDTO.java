package com.lunastore.dto;

import com.lunastore.vo.SellerVO;
import lombok.Data;

@Data
public class SellerSalesDTO {
    private SellerVO seller;
    private StoreSalesDTO sales;
}
