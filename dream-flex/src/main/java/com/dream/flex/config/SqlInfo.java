package com.dream.flex.config;

import java.util.List;
import java.util.Set;

public class SqlInfo {
    private String sql;
    private List<Object> paramList;
    private Set<String> tableSet;

    public SqlInfo(String sql, List<Object> paramList, Set<String> tableSet) {
        this.sql = sql;
        this.paramList = paramList;
        this.tableSet = tableSet;
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public Set<String> getTableSet() {
        return tableSet;
    }

    @Override
    public String toString() {
        return "SqlInfo{" +
                "sql='" + sql + '\'' +
                ", paramList=" + paramList +
                ", tableSet=" + tableSet +
                '}';
    }
}
