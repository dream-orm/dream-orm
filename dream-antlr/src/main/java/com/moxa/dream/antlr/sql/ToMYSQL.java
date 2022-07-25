package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.FunctionStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;

import java.util.List;

public class ToMYSQL extends ToPubSQL {

    private String getPattern(String pattern) {
        char[] patternArray = pattern.toCharArray();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < patternArray.length) {
            char sign = Character.toUpperCase(patternArray[i]);
            switch (sign) {
                case 'Y':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'Y':
                            switch (Character.toUpperCase(patternArray[i + 2])) {
                                case 'Y':
                                    switch (Character.toUpperCase(patternArray[i + 3])) {
                                        case 'Y':
                                            builder.append("%Y");
                                            i += 4;
                                            break;
                                        default:
                                            builder.append("%y");
                                            i += 2;
                                            break;
                                    }
                                    break;
                                default:
                                    builder.append("%y");
                                    i += 2;
                                    break;
                            }
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'M':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'M':
                            builder.append("%m");
                            i += 2;
                            break;
                        case 'I':
                            builder.append("%i");
                            i += 2;
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'D':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'D':
                            switch (Character.toUpperCase(patternArray[i + 2])) {
                                case 'D':
                                    builder.append("%j");
                                    i += 3;
                                    break;
                                default:
                                    builder.append("%d");
                                    i += 2;
                                    break;
                            }
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'H':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'H':
                            switch (Character.toUpperCase(patternArray[i + 2])) {
                                case '2':
                                    switch (Character.toUpperCase(patternArray[i + 3])) {
                                        case '4':
                                            builder.append("%H");
                                            i += 4;
                                            break;
                                        default:
                                            builder.append("%h");
                                            i += 2;
                                            break;
                                    }
                                    break;
                                case '1':
                                    switch (Character.toUpperCase(patternArray[i + 3])) {
                                        case '2':
                                            builder.append("%h");
                                            i += 4;
                                            break;
                                        default:
                                            builder.append("%h");
                                            i += 2;
                                            break;
                                    }
                                    break;
                                default:
                                    builder.append("%h");
                                    i += 2;
                                    break;
                            }
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'S':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'S':
                            builder.append("%S");
                            i += 2;
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                default:
                    builder.append(patternArray[i++]);
                    break;
            }
        }
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
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
    protected String toString(FunctionStatement.ToDateStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "STR_TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }
}
