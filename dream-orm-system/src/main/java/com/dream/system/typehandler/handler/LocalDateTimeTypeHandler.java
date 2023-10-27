package com.dream.system.typehandler.handler;

import java.sql.*;
import java.time.LocalDateTime;

public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {
    @Override
    public void setParameter(PreparedStatement ps, int index, LocalDateTime parameter, int jdbcType) throws SQLException {
        ps.setTimestamp(index, Timestamp.valueOf(parameter));
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(index);
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(column);
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
