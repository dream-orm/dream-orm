package com.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement ps, int index, Boolean parameter, int jdbcType) throws SQLException {
        switch (jdbcType) {
            case Types.NULL:
            case Types.BOOLEAN:
                ps.setBoolean(index, parameter);
                break;
            default:
                ps.setByte(index, (byte) (parameter ? 1 : 0));
                break;
        }

    }

    @Override
    public Boolean getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        switch (jdbcType) {
            case Types.NULL:
            case Types.BOOLEAN: {
                boolean result = rs.getBoolean(index);
                return !result && rs.wasNull() ? null : result;
            }
            default: {
                byte result = rs.getByte(index);
                return result == 0 && rs.wasNull() ? null : retBoolean(result);
            }
        }
    }

    @Override
    public Boolean getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        switch (jdbcType) {
            case Types.NULL:
            case Types.BOOLEAN: {
                boolean result = rs.getBoolean(column);
                return !result && rs.wasNull() ? null : result;
            }
            default: {
                byte result = rs.getByte(column);
                return result == 0 && rs.wasNull() ? null : retBoolean(result);
            }
        }
    }

    protected boolean retBoolean(byte result) {
        return result > 0 ? true : false;
    }

    @Override
    public int getNullType() {
        return Types.BOOLEAN;
    }
}
