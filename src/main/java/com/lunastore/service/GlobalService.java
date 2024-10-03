package com.lunastore.service;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.lunastore.common.PageNav;

public interface GlobalService {
    Date dateUpdate(Date date);
    String fileNameUpdate(MultipartFile imgFile);
    PageNav setPageNav(PageNav pageNav, int pageNum, int pageBlock, int rowsPage);
}