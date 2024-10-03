package com.lunastore.dto;

import java.util.Date;

import lombok.Data;

@Data
public class QnaDTO {
    private Integer qna_idx;
    private Integer i_idx;
    private Integer b_idx;
    private Integer s_idx;

    private String qna_title;
    private String qna_question;
    private String qna_answer;
    private Date qna_regdate;
    private Date qna_modifydate;

    private String productNumFormatted;
    private String memberNumFormatted;
}