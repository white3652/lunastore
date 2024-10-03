package com.lunastore.dao;

import com.lunastore.dto.ReviewDTO;
import com.lunastore.vo.SearchVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDAO {
    public static final String MAPPER = "com.lunastore.ReviewMapper";
    private final SqlSession sqlSession;

    public List<ReviewDTO> getRecentReviewsBySeller(int s_idx) {
        return sqlSession.selectList(MAPPER + ".getRecentReviewsBySeller", s_idx);
    }

    // 필요한 추가 메서드가 있으면 여기에 추가
}