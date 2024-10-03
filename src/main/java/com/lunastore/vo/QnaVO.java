package com.lunastore.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class QnaVO {
    private Integer qna_idx;
    private Integer i_idx;
    private Integer b_idx;
    private Integer s_idx;

    private String qna_title;

    private String qna_question;

    @NotBlank(message = "답변은 필수입니다.")
    private String qna_answer;
    private Date qna_regdate;
    private Date qna_modifydate;

    // 포맷된 상품번호와 회원번호
    private String productNumFormatted;
    private String memberNumFormatted;
    private String i_name;
    private String imageSrc;
    // 답변 여부
    public boolean isAnswered() {
        return this.qna_answer != null && !this.qna_answer.trim().isEmpty();
    }
    // 포맷 메서드 추가
    public void formatNumbers() {
        if (this.i_idx != null) {
            this.productNumFormatted = "P" + this.i_idx;
        }
        if (this.b_idx != null) {
            this.memberNumFormatted = "M" + this.b_idx;
        }
    }
}
