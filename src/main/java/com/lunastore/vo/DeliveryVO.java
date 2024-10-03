package com.lunastore.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DeliveryVO {
    private int bo_idx;          // 주문 ID
    private int b_idx;           // 구매자 ID
    private String bo_item_name; // 상품 이름
    private String bo_name;      // 수령인 이름
    private String bo_zipcode;   // 우편번호
    private String bo_address;   // 주소
    private String bo_contact;   // 연락처
    private int bos_state;       // 배송 상태
    private int bos_price; // 가격
    private Date bos_postdate;   // 배송일자

}
