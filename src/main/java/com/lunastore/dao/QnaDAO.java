package com.lunastore.dao;


import com.lunastore.dto.QnaDTO;
import com.lunastore.mapper.QnaMapper;
import com.lunastore.vo.QnaVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.lunastore.dao.OrderDAO.MAPPER;

@Repository
@RequiredArgsConstructor
public class QnaDAO {


    public static final String MAPPER = "com.lunastore.QnaMapper";
    private final SqlSession sqlSession;
    private final QnaMapper qnaMapper;

    public List<QnaVO> getQnasByItemId(int iIdx) throws Exception {
        return qnaMapper.getQnasByItemId(iIdx);
    }

    public void insertQna(QnaVO qnaVO) throws Exception {
        qnaMapper.insertQna(qnaVO);
    }

    public void updateAnswer(Integer qnaIdx, String answer) throws Exception {
        qnaMapper.updateAnswer(qnaIdx, answer);
    }

    public QnaVO findById(int qnaIdx) throws Exception {
        return qnaMapper.getQnaById(qnaIdx);
    }

    public int countInquiries(Integer i_idx, Integer b_idx, String answeredStatus, Date startDate, Date endDate) throws Exception {
        return qnaMapper.countInquiries(
                i_idx != null ? i_idx.toString() : null,
                b_idx != null ? b_idx.toString() : null,
                answeredStatus,
                startDate,
                endDate
        );
    }

    public List<QnaVO> getInquiries(Integer i_idx, Integer b_idx, String answeredStatus, Date startDate, Date endDate, int offset, int rowsPage) throws Exception {
        return qnaMapper.getInquiries(
                i_idx != null ? i_idx.toString() : null,
                b_idx != null ? b_idx.toString() : null,
                answeredStatus,
                startDate,
                endDate,
                offset,
                rowsPage
        );
    }
    public List<QnaDTO> getRecentInquiriesBySeller(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getRecentInquiriesBySeller", s_idx);
    }
}