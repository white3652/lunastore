package com.lunastore.service;

import com.lunastore.dao.QnaDAO;
import com.lunastore.vo.QnaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {
    private final QnaDAO qnaDAO;

    @Override
    public List<QnaVO> getQnasByItemId(int iIdx) throws Exception {
        return qnaDAO.getQnasByItemId(iIdx);
    }

    @Override
    public QnaVO getQnaById(int qnaIdx) throws Exception {
        return qnaDAO.findById(qnaIdx);
    }

    @Override
    @Transactional
    public void submitQuestion(QnaVO qnaVO) throws Exception {
        qnaDAO.insertQna(qnaVO);
    }

    @Override
    @Transactional
    public void answerQuestion(int qnaIdx, String answer) throws Exception {
        qnaDAO.updateAnswer(qnaIdx, answer);
    }

    @Override
    public int countInquiries(Integer i_idx, Integer b_idx, String answeredStatus, Date startDate, Date endDate) throws Exception {
        return qnaDAO.countInquiries(i_idx, b_idx, answeredStatus, startDate, endDate);
    }

    @Override
    public List<QnaVO> getInquiries(Integer i_idx, Integer b_idx, String answeredStatus, Date startDate, Date endDate, int pageNum, int rowsPage) throws Exception {
        int offset = (pageNum - 1) * rowsPage;
        List<QnaVO> inquiries = qnaDAO.getInquiries(i_idx, b_idx, answeredStatus, startDate, endDate, offset, rowsPage);

        // 포맷된 상품번호와 회원번호 설정
        for (QnaVO inquiry : inquiries) {
            inquiry.formatNumbers();
        }

        return inquiries;
    }

    @Override
    @Transactional
    public void updateAnswer(Integer qnaIdx, String answer) throws Exception {
        qnaDAO.updateAnswer(qnaIdx, answer);
    }

}