package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.FunctionStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.util.AntlrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToMYSQL extends ToPubSQL {
    private Map<String, String> replaceMap = new HashMap<>();

    public ToMYSQL() {
        replaceMap.put("yyyy", "%Y");
        replaceMap.put("yyyy", "%Y");
        replaceMap.put("YY", "%y");
        replaceMap.put("yy", "%y");
        replaceMap.put("MM", "%m");
        replaceMap.put("mm", "%m");
        replaceMap.put("DD", "%d");
        replaceMap.put("dd", "%d");
        replaceMap.put("HH24", "%H");
        replaceMap.put("hh24", "%H");
        replaceMap.put("HH", "%h");
        replaceMap.put("hh", "%h");
        replaceMap.put("MI", "%i");
        replaceMap.put("mi", "%i");
        replaceMap.put("SS", "%s");
        replaceMap.put("ss", "%s");
        replaceMap.put("DDD", "%j");
        replaceMap.put("ddd", "%j");
    }

    private String getPattern(String pattern) {
        return AntlrUtil.replace(pattern, replaceMap);
    }


    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length > 1) {
            String pattern = statement.getPattern();
            if (pattern == null) {
                pattern = getPattern(toStr(columnList[1], assist, invokerList));
                statement.setPattern(pattern);
            }
            return "DATE_FORMAT(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
        } else {
            return "CONVERT(" + toStr(columnList[0], assist, invokerList) + ",CHAR)";
        }
    }

    @Override
    protected String toString(FunctionStatement.ToNumberStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CONVERT(" + toStr(columnList[0], assist, invokerList) + ",DECIMAL)";
    }

    @Override
    protected String toString(FunctionStatement.ToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }

        return "STR_TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToTimeStampStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }

        return "STR_TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }
}
