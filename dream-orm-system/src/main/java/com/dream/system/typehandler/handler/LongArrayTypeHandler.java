package com.dream.system.typehandler.handler;

import com.dream.util.common.ObjectUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

    @Override
    public void setParameter(PreparedStatement ps, int index, Long[] parameter, int jdbcType) throws SQLException {
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < parameter.length; i++) {
            joiner.add(String.valueOf(parameter[i]));
        }
        ps.setString(index, joiner.toString());
    }

    @Override
    public Long[] getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        String result = rs.getString(index);
        return getResult(result);
    }

    @Override
    public Long[] getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        String result = rs.getString(column);
        return getResult(result);
    }

    @Override
    public int getNullType() {
        return Types.VARCHAR;
    }

    protected Long[] getResult(String result) {
        if (ObjectUtil.isNull(result)) {
            return null;
        }
        Long[] results = Arrays.stream(result.split(","))
                .map(res -> Long.valueOf(res))
                .collect(Collectors.toList())
                .toArray(new Long[0]);
        return results;
    }
}
