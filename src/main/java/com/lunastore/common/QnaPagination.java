package com.lunastore.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaPagination {
    private int totalRows;        // 총 데이터 수
    private int pageNum;          // 현재 페이지 번호
    private int totalPageNum;     // 총 페이지 수
    private int viewNum;          // 한 페이지당 표시할 항목 수
    private int pageBlock;        // 페이지 블록 크기 (예: 5)
    private int startPage;        // 페이지 블록의 시작 페이지 번호
    private int endPage;          // 페이지 블록의 끝 페이지 번호
}