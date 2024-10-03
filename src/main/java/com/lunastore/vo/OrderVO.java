package com.lunastore.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderVO {

    private int bo_idx;
    private int b_idx;

    @NotBlank(message = "상품명을 입력하세요.")
    @Size(max = 255, message = "상품명은 255자 이내여야 합니다.")
    private String bo_itemname;

    @NotBlank(message = "주문자 이름을 입력하세요.")
    @Size(max = 20, message = "주문자 이름은 20자 이내여야 합니다.")
    private String bo_name;

    @NotBlank(message = "우편번호를 입력하세요.")
    private String bo_zipcode;

    @NotBlank(message = "주소를 입력하세요.")
    private String bo_address;

    @NotBlank(message = "연락처를 입력하세요.")
    @Pattern(regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$", message = "유효한 연락처를 입력하세요.")
    private String bo_contact;

    private Date bo_orderdate;
    private String bo_status;

    private List<OrderStateVO> orderStateVO = new ArrayList<>();
    private double totalPrice;

    private String ba_firstname;
    private String ba_lastname;
    private String ba_zipcode;
    private String ba_address;
    private String ba_restaddress;
    private String bos_option;
    private AddressVO addressVO;
}
