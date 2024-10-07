package com.lunastore.common;


import lombok.Data;

@Data
public class ListPageNav {

    private int totalRows;
    private int rowsPerPage = 9; // 페이지당 아이템 수
    private int pagesPerBlock = 5; // 한 블록당 페이지 수
    private int pageNum = 1; // 현재 페이지 번호
    private int startPage; // 현재 블록의 시작 페이지 번호
    private int endPage; // 현재 블록의 끝 페이지 번호
    private int pageBlock; // 현재 페이지 블록
    private int totalPageNum; // 총 페이지 수
    private int lastPageBlock; // 마지막 페이지 블록 번호

    public void calculatePageNav() {
        System.out.println("Before adjustment - pageNum: " + this.pageNum);

        // 현재 페이지 번호 유효성 검사
        if (this.pageNum < 1) {
            this.pageNum = 1;
        }

        // 총 페이지 수 계산
        this.totalPageNum = (int) Math.ceil((double) totalRows / rowsPerPage);

        // 총 페이지 수가 0인 경우 처리
        if (this.totalPageNum == 0) {
            this.totalPageNum = 1;
        }

        // 현재 페이지 번호가 총 페이지 수를 초과하지 않도록 유효성 검사
        if (this.pageNum > this.totalPageNum) {
            this.pageNum = this.totalPageNum;
        }

        // 페이지 블록 계산
        this.pageBlock = (int) Math.ceil((double) this.pageNum / pagesPerBlock);

        // 현재 블록의 시작 페이지와 끝 페이지 번호 계산
        this.startPage = (pageBlock - 1) * pagesPerBlock + 1;
        this.endPage = Math.min(startPage + pagesPerBlock - 1, totalPageNum);

        // 마지막 페이지 블록 번호 계산
        this.lastPageBlock = (int) Math.ceil((double) totalPageNum / pagesPerBlock);
        System.out.println("After adjustment - pageNum: " + this.pageNum);
    }
}