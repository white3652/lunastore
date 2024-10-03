package com.lunastore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateContactDTO {
    private int b_idx;

    @NotBlank(message = "연락처를 입력하세요.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "유효한 연락처 형식을 입력하세요 (예: 010-1234-1234).")
    private String b_tel;
}
