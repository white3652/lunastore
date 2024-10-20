package com.lunastore.common;


import lombok.Data;

@Data
public class ListPageNav {

    private int totalRows;
    private int rowsPerPage = 9;
    private int pagesPerBlock = 5;
    private int pageNum = 1;
    private int startPage;
    private int endPage;
    private int pageBlock;
    private int totalPageNum;
    private int lastPageBlock;

    public void calculatePageNav() {

        if (this.pageNum < 1) {
            this.pageNum = 1;
        }

        this.totalPageNum = (int) Math.ceil((double) totalRows / rowsPerPage);

        if (this.totalPageNum == 0) {
            this.totalPageNum = 1;
        }

        if (this.pageNum > this.totalPageNum) {
            this.pageNum = this.totalPageNum;
        }

        this.pageBlock = (int) Math.ceil((double) this.pageNum / pagesPerBlock);

        this.startPage = (pageBlock - 1) * pagesPerBlock + 1;
        this.endPage = Math.min(startPage + pagesPerBlock - 1, totalPageNum);

        this.lastPageBlock = (int) Math.ceil((double) totalPageNum / pagesPerBlock);
    }
}