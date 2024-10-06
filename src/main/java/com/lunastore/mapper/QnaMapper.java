package com.lunastore.mapper;

import com.lunastore.vo.QnaVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QnaMapper {

    void insertQna(QnaVO qna);

    QnaVO getQnaById(Integer qnaIdx);

    List<QnaVO> getQnasByItemId(Integer itemId);

    void updateAnswer(@Param("qnaIdx") Integer qnaIdx, @Param("answer") String answer);

    int countInquiries(@Param("productNum") String productNum, @Param("memberNum") String memberNum, @Param("answeredStatus") String answeredStatus, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<QnaVO> getInquiries(@Param("productNum") String productNum, @Param("memberNum") String memberNum, @Param("answeredStatus") String answeredStatus, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("offset") int offset, @Param("rowsPage") int rowsPage);
}