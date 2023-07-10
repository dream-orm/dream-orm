package com.moxa.dream.flex.def;

import java.util.List;

public class SqlDef {
    private String sql;
    private List<Object> paramList;

    public SqlDef(String sql, List<Object> paramList) {
        this.sql = sql;
        this.paramList = paramList;
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    @Override
    public String toString() {
        return "SqlDef{" +
                "sql='" + sql + '\'' +
                ", paramList=" + paramList +
                '}';
    }
}
