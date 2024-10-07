package com.lunastore.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SearchVO {
    private String buyerNickname;
    private int reviewState;
    private String orderNum;
    private String orderNickname;
    private int orderState;
    private String itemNum;
    private String itemName;
    private int itemState;
    private int largeCategory;
    private int smallCategory;
    private Date startDate;
    private Date endDate;
    private Integer s_idx;
    private int offset;
    private int limit;
    private String searchWord;
    private String searchField;
    private String orderByType;
    private int br_star;

    private int i_idx;
    private int p_idx;
    private int c_idx;
    private int b_idx;
    private String answeredStatus;
    private int viewNum;
    private int pageNum;      // 기존 페이지 번호 (주로 리스트 전체)
    private int pageBlock;
    private int startIdx;

    // 추가: 리뷰 및 Q&A 별도의 페이지 번호
    private int reviewPageNum = 1;
    private int qnaPageNum = 1;

    public void calculateOffset() {
        this.offset = (this.pageNum - 1) * this.viewNum;
        this.limit = this.viewNum;
    }
}