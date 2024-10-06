package com.lunastore.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lunastore.common.PageNav;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GlobalServiceImpl implements GlobalService {

    @Value("${upload.dir:./uploads/}")
    private String uploadDir;

    private final ResourceLoader resourceLoader;

    private Path absoluteUploadPath;
    @Override
    public Date dateUpdate(Date date) {
        if (date == null) {
            return new Date();
        }
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime updatedLocalDateTime = localDateTime.minusHours(9);
        Instant instant = updatedLocalDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
    @PostConstruct
    public void init() {
        absoluteUploadPath = Paths.get(System.getProperty("user.dir")).resolve(uploadDir).toAbsolutePath();
        File uploadDirectory = absoluteUploadPath.toFile();
        if (!uploadDirectory.exists()) {
            boolean created = uploadDirectory.mkdirs();
            if (created) {
                System.out.println("Upload directory created at: " + absoluteUploadPath.toString());
            } else {
                System.err.println("Failed to create upload directory at: " + absoluteUploadPath.toString());
            }
        } else {
            System.out.println("Upload directory already exists at: " + absoluteUploadPath.toString());
        }
    }
    @Override
    public String fileNameUpdate(MultipartFile imgFile) {
        String saveFilename = null;
        if (imgFile != null && !imgFile.isEmpty()) {
            String orgFilename = imgFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
            saveFilename = uuid + "." + extension;

            Path filePath = absoluteUploadPath.resolve(saveFilename);
            File file = filePath.toFile();

            try {
                imgFile.transferTo(file);
                System.out.println("파일이 성공적으로 저장되었습니다: " + filePath.toString());
            } catch (Exception e) {
                System.err.println("파일 저장 중 예외 발생: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("파일 저장에 실패했습니다.");
            }
        }
        return saveFilename;
    }

    @Override
    public PageNav setPageNav(PageNav pageNav, int pageNum, int pageBlock, int rowsPage) {
        int totalRows = pageNav.getTotalRows();
        int rows_page = rowsPage > 0 ? rowsPage : 10;
        int pages_pageBlock = pageNav.getPages_pageBlock() > 0 ? pageNav.getPages_pageBlock() : 5;

        pageNav.setRows_page(rows_page);
        pageNav.setPages_pageBlock(pages_pageBlock);

        int pNum = pageNum > 0 ? pageNum : 1;
        pageNav.setPageNum(pNum);

        int pBlock = pageBlock > 0 ? pageBlock : 1;
        pageNav.setPageBlock(pBlock);

        int startNum = (pNum - 1) * rows_page + 1;
        int endNum = pNum * rows_page;
        if (endNum > totalRows) {
            endNum = totalRows;
        }
        pageNav.setStartNum(startNum);
        pageNav.setEndNum(endNum);

        int total_pageNum = (int) Math.ceil((double) totalRows / rows_page);
        pageNav.setTotal_pageNum(total_pageNum);

        int last_pageBlock = (int) Math.ceil((double) total_pageNum / pages_pageBlock);
        pageNav.setLast_pageBlock(last_pageBlock);

        return pageNav;
    }
}