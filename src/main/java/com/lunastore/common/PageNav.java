package com.lunastore.common;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PageNav {

    private int totalRows;
    private int rows_page = 3;
    private int pages_pageBlock = 5;
    private int pageNum = 1;
    private int startNum;
    private int endNum;
    private int pageBlock;
    private int total_pageNum;
    private int last_pageBlock;

}
