package com.lunastore.common;

import lombok.Data;

@Data
public class ViewPagination {

    private int totalRows;       // 총 데이터 수
    private int currentPage;     // 현재 페이지 번호
    private int rowsPerPage;     // 페이지당 데이터 수
    private int totalPages;      // 총 페이지 수
    private int startPage;       // 시작 페이지 번호
    private int endPage;         // 끝 페이지 번호
    private int pageBlockSize;   // 페이지 블록 크기 (네비게이션에 표시될 페이지 수)

    public ViewPagination(int totalRows, int currentPage, int rowsPerPage, int pageBlockSize) {
        this.totalRows = totalRows;
        this.currentPage = currentPage;
        this.rowsPerPage = rowsPerPage;
        this.pageBlockSize = pageBlockSize;

        calculate();
    }

    private void calculate() {
        // 총 페이지 수 계산
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);

        // 현재 페이지 블록 계산
        int currentBlock = (int) Math.ceil((double) currentPage / pageBlockSize);

        // 시작 페이지와 끝 페이지 계산
        startPage = (currentBlock - 1) * pageBlockSize + 1;
        endPage = Math.min(startPage + pageBlockSize - 1, totalPages);
    }
}