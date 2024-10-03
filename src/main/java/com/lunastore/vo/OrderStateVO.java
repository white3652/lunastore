package com.lunastore.vo;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderStateVO {
    private String b_nickname;
    private String i_name;
    private Date bo_orderdate;

    private int bos_idx;
    private int bo_idx;
    private int i_idx;
    private int s_idx;
    @NotBlank(message = "옵션을 입력하세요.")
    private String bos_option;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private double bos_price;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int bos_count;

    private String formattedTotalPrice;
    private String bos_settle;
    private int bos_state = 1;
    private Date bos_postdate;
    private String orderNum;
    private String formattedOrderNum;
}
