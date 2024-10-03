package com.lunastore.common;

import lombok.Data;

@Data
public class PageNav2 {

    private int totalRows;
    private int rows_page = 3;
    private int pages_pageBlock = 5;
    private int pageNum = 1;
    private int startNum;
    private int endNum;
    private int pageBlock;
    private int totalPageNum;
    private int last_pageBlock;
    public void calculate() {
        // 페이지 네비게이션 계산 로직 구현
        // 예시:
        totalPageNum = (int) Math.ceil((double) totalRows / rows_page);
        pageBlock = (int) Math.ceil((double) pageNum / pages_pageBlock);
        startNum = (pageBlock - 1) * pages_pageBlock + 1;
        endNum = Math.min(startNum + pages_pageBlock - 1, totalPageNum);
    }
}