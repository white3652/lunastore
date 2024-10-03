package com.lunastore.vo;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class BuyerVO {
    private int b_idx;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$", message = "유효한 전화번호를 입력하세요.")
    private String b_tel;

    @NotBlank(message = "성을 입력하세요.")
    @Size(max = 10, message = "성은 10자 이내여야 합니다.")
    private String b_firstname;

    @NotBlank(message = "이름을 입력하세요.")
    @Size(max = 10, message = "이름은 10자 이내여야 합니다.")
    private String b_lastname;

    @NotBlank(message = "생년월일을 입력하세요.")
    @Pattern(regexp = "^(19\\d{2}|20\\d{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$", message = "유효한 생년월일을 입력하세요.")
    private String b_birth;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "유효한 이메일을 입력하세요.")
    private String b_email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$", message = "비밀번호는 문자, 숫자, 특수문자를 포함해야 합니다.")
    private String b_pw;

    private Date b_regdate;
    private Date b_modifydate;
    private Date b_lastlogindate;
    private Date b_pwmodifydate;
    private String b_cancel;
    private String b_check;
    private String b_terms;
    private String b_grade;
    private String b_gender;
    private String b_nickname;
    private String b_profile;
    private String b_point;
    private MultipartFile b_tempprofile;

}
