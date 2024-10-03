package com.lunastore.common;

import lombok.Data;

@Data
public class SettlePageNav {


    private int totalRows;
    private int pageSize = 10;          // 페이지당 행 수
    private int pageBlockSize = 5;      // 페이지 블록 크기
    private int currentPage = 1;        // 현재 페이지 번호
    private int startNum;
    private int endNum;
    private int pageBlock;
    private int totalPageNum;
    private int lastPageBlock;

    public void calculate() {
        // 총 페이지 수 계산
        totalPageNum = (int) Math.ceil((double) totalRows / pageSize);

        // 현재 페이지 블록 계산
        pageBlock = (int) Math.ceil((double) currentPage / pageBlockSize);

        // 현재 블록의 시작 페이지 번호 계산
        startNum = (pageBlock - 1) * pageBlockSize + 1;

        // 현재 블록의 끝 페이지 번호 계산
        endNum = Math.min(startNum + pageBlockSize - 1, totalPageNum);

        // 마지막 페이지 블록 번호 계산
        lastPageBlock = (int) Math.ceil((double) totalPageNum / pageBlockSize);
    }
}
