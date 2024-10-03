package com.lunastore.common;

import lombok.Data;

@Data
public class Pagination {
    private int totalRows;          // 총 행 수
    private int rowsPerPage = 10;   // 페이지당 행 수 (기본값)
    private int pageBlockSize = 5;  // 페이지 블록 크기 (기본값)
    private int currentPage = 1;    // 현재 페이지 번호
    private int startNum;           // 현재 블록의 시작 페이지 번호
    private int endNum;             // 현재 블록의 끝 페이지 번호
    private int pageBlock;          // 현재 페이지 블록 번호
    private int totalPageNum;      // 총 페이지 수
    private int lastPageBlock;      // 마지막 페이지 블록 번호

    /**
     * 기본 생성자
     */
    public Pagination() {}

    /**
     * 커스텀 rowsPerPage와 pageBlockSize를 설정하는 생성자
     */
    public Pagination(int rowsPerPage, int pageBlockSize) {
        this.rowsPerPage = rowsPerPage;
        this.pageBlockSize = pageBlockSize;
    }

    /**
     * 페이징 계산 로직
     */
    public void calculate() {
        // 총 페이지 수 계산
        totalPageNum = (int) Math.ceil((double) totalRows / rowsPerPage);

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
