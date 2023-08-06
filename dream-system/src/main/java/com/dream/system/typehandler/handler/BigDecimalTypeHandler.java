package com.dream.system.typehandler.handler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {

    @Override
    public void setParameter(PreparedStatement ps, int index, BigDecimal parameter, int jdbcType) throws SQLException {
        ps.setBigDecimal(index, parameter);
    }

    @Override
    public BigDecimal getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        return rs.getBigDecimal(index);
    }

    @Override
    public BigDecimal getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        return rs.getBigDecimal(column);
    }

    @Override
    public int getNullType() {
        return Types.DECIMAL;
    }
}

