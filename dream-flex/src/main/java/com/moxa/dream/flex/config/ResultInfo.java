package com.moxa.dream.flex.config;

import com.moxa.dream.antlr.config.Assist;

import java.util.List;
import java.util.Set;

public class ResultInfo {
    private String sql;
    private List<Object> paramList;
    private Set<String> tableSet;
    private Assist assist;

    public ResultInfo(String sql, List<Object> paramList, Set<String> tableSet, Assist assist) {
        this.sql = sql;
        this.paramList = paramList;
        this.tableSet = tableSet;
        this.assist = assist;
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

    public Assist getAssist() {
        return assist;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "sql='" + sql + '\'' +
                ", paramList=" + paramList +
                ", tableSet=" + tableSet +
                '}';
    }
}
