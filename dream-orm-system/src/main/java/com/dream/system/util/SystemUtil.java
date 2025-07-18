package com.dream.system.util;


import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.annotation.Ignore;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.cache.CacheKey;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class SystemUtil {
    private static final TableUtil tableUtil = new TableUtil();

    public static String getTableName(Class<?> type) {
        return tableUtil.getTableName(type);
    }

    public static boolean ignoreField(Field field) {
        int modifier = field.getModifiers();
        if (Modifier.isStatic(modifier)
                || Modifier.isFinal(modifier) || field.isAnnotationPresent(Ignore.class)) {
            return true;
        }
        return false;
    }

    public static String underlineToCamel(String column) {
        if (column == null) {
            return null;
        }
        int len = column.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(column.charAt(i));
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(column.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String camelToUnderline(String field) {
        if (field == null) {
            return null;
        }
        int len = field.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = field.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                if (sb.length() > 0) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static CacheKey cacheKey(String sql, int split) {
        char[] charList = sql.toCharArray();
        int index;
        index = 0;
        for (int i = 0; i < charList.length; i++) {
            char c;
            if (!Character.isWhitespace(c = charList[i])) {
                charList[index++] = Character.toLowerCase(c);
            }
        }
        if (split > index) {
            split = index;
        }
        Object[] updateList = new Object[split + 2];
        updateList[0] = new String(charList, 0, index);
        updateList[1] = index;
        int len = (int) Math.ceil(index / (double) split);
        for (int i = 0; i < split; i++) {
            int sPoint = i * len;
            int size = Math.min((i + 1) * len, index) - sPoint;
            if (size > 0) {
                char[] tempChars = new char[size];
                System.arraycopy(charList, sPoint, tempChars, 0, size);
                updateList[i + 2] = new String(tempChars);
            }
        }
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(updateList);
        return cacheKey;
    }

    public static String key(String column) {
        return "`" + column + "`";
    }

    public static String key(String column, ToSQL toSQL) {
        try {
            return toSQL.toStr(new SymbolStatement.SingleMarkStatement(column), null, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e.getMessage());
        }
    }

    public static InsertStatement insertStatement(String table, List<String> columns, List<List<String>> columnRefsList) {
        InsertStatement insertStatement = new InsertStatement();
        insertStatement.setTable(new SymbolStatement.SingleMarkStatement(table));
        ListColumnStatement columnStatements = new ListColumnStatement();
        ListColumnStatement valueStatements = new ListColumnStatement();
        columnStatements.add(columns.stream().map(SymbolStatement.SingleMarkStatement::new).toArray(Statement[]::new));
        Statement[] statementList = new Statement[columnRefsList.size()];
        for (int index = 0; index < statementList.length; index++) {
            List<String> columnRefs = columnRefsList.get(index);
            ListColumnStatement paramStatements = new ListColumnStatement();
            Statement[] statements = new Statement[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                InvokerStatement markInvokerStatement = new InvokerStatement();
                markInvokerStatement.setFunction(MarkInvoker.FUNCTION);
                markInvokerStatement.setNamespace(MarkInvoker.DEFAULT_NAMESPACE);
                markInvokerStatement.setParamStatement(new SymbolStatement.LetterStatement(columnRefs.get(i)));
                statements[i] = markInvokerStatement;
            }
            paramStatements.add(statements);
            statementList[index] = new BraceStatement(paramStatements);
        }
        valueStatements.add(statementList);
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(valueStatements);
        insertStatement.setColumns(new BraceStatement(columnStatements));
        insertStatement.setValues(valuesStatement);
        return insertStatement;
    }
}
