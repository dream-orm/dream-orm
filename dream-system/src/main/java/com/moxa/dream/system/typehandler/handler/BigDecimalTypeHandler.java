package com.moxa.dream.system.typehandler.handler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {

    @Override
    public void setParameter(PreparedStatement ps, int i, BigDecimal parameter, int jdbcType) throws SQLException {
        ps.setBigDecimal(i, parameter);
    }

    @Override
    public BigDecimal getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        return rs.getBigDecimal(columnIndex);
    }

    @Override
    public int getNullType() {
        return Types.DECIMAL;
    }
}

