package com.lunastore.common;

public class ValidatedListPageNav extends ListPageNav {

    @Override
    public void calculatePageNav() {
        // 현재 페이지 번호 유효성 검사
        if (this.getPageNum() < 1) {
            this.setPageNum(1);
        }

        // 총 페이지 수 계산
        super.calculatePageNav();

        // 총 페이지 수가 0인 경우 처리
        if (this.getTotalPageNum() == 0) {
            this.setTotalPageNum(1);
        }

        // 현재 페이지 번호가 총 페이지 수를 초과하지 않도록 유효성 검사
        if (this.getPageNum() > this.getTotalPageNum()) {
            this.setPageNum(this.getTotalPageNum());
        }
    }
}