package com.dream.helloworld.h2;

import com.dream.system.typehandler.handler.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomTypeHandler implements TypeHandler<String> {
    @Override
    public void setParam(PreparedStatement ps, int index, String parameter, int jdbcType) throws SQLException {
        ps.setString(index, "hello");
    }

    @Override
    public String getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        return "hello";
    }

    @Override
    public String getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        return "hello";
    }
}
