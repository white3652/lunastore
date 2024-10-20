package com.lunastore.typehandler;

import com.lunastore.vo.Grade;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.*;

public class GradeTypeHandler extends BaseTypeHandler<Grade> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Grade parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("Setting Grade parameter: " + parameter);
        // Enum의 code 값을 DB에 설정
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public Grade getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int gradeCode = rs.getInt(columnName);
        if (rs.wasNull()) {
            return Grade.STANDARD; // NULL일 경우 기본값 반환
        }
        return Grade.fromCode(gradeCode);
    }

    @Override
    public Grade getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int gradeCode = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return Grade.STANDARD;
        }
        return Grade.fromCode(gradeCode);
    }

    @Override
    public Grade getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int gradeCode = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return Grade.STANDARD;
        }
        return Grade.fromCode(gradeCode);
    }
}
