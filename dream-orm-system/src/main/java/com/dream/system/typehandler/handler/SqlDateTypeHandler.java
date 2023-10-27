package com.dream.system.typehandler.handler;

import java.sql.*;

public class SqlDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Date parameter, int jdbcType) throws SQLException {
        ps.setTimestamp(index, new Timestamp(parameter.getTime()));
    }

    @Override
    public Date getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(index);
        if (timestamp == null) {
            return null;
        } else {
            return new Date(timestamp.getTime());
        }
    }

    @Override
    public Date getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(column);
        if (timestamp == null) {
            return null;
        } else {
            return new Date(timestamp.getTime());
        }
    }

    @Override
    public int getNullType() {
        return Types.DATE;
    }
}
