package com.lunastore.dto;


import lombok.Data;

@Data
public class AddressUpdateDTO {
    private int b_idx;
    private String b_firstname;
    private String b_lastname;
    private String ba_zipcode;
    private String ba_address;
    private String ba_restaddress;
}