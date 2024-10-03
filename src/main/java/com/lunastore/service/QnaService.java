package com.lunastore.service;

import com.lunastore.vo.QnaVO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface QnaService {
    List<QnaVO> getQnasByItemId(int iIdx) throws Exception;
    QnaVO getQnaById(int qnaIdx) throws Exception;
    void submitQuestion(QnaVO qnaVO) throws Exception;
    void answerQuestion(int qnaIdx, String answer) throws Exception;
    int countInquiries(Integer i_idx, Integer b_idx, String answeredStatus, Date startDate, Date endDate) throws Exception;
    List<QnaVO> getInquiries(Integer i_idx, Integer b_idx, String answeredStatus, Date startDate, Date endDate, int pageNum, int rowsPage) throws Exception;
    void updateAnswer(Integer qnaIdx, String answer) throws Exception;

}