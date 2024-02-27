package com.dream.antlr.util;

import com.dream.antlr.smt.*;

import java.util.Map;

public class AntlrUtil {

    public static String invokerSQL(String function, String namespace, String... args) {
        StringBuilder paramBuilder = new StringBuilder();
        if (args != null && args.length > 0) {
            String cut = ",";
            for (String param : args) {
                paramBuilder.append(param).append(cut);
            }
            paramBuilder.delete(paramBuilder.length() - cut.length(), paramBuilder.length());
        }
        if (namespace != null && namespace.trim().length() != 0) {
            return "@" + function + ":" + namespace + "(" + paramBuilder + ")";
        } else {
            return "@" + function + "(" + paramBuilder + ")";
        }
    }

    public static String replace(String value, Map<String, String> replaceMap) {
        StringBuilder builder = new StringBuilder();
        int length = value.length();
        int i = 0;
        while (i < length) {
            int point = i;
            String replaceNewStr = null;
            for (int j = i + 1; j <= length; j++) {
                String subStr = value.substring(i, j);
                String newStr = replaceMap.get(subStr);
                if (newStr != null) {
                    point = j;
                    replaceNewStr = newStr;
                }
            }
            if (replaceNewStr != null) {
                i = point;
                builder.append(replaceNewStr);
            } else {
                builder.append(value.charAt(i++));
            }
        }
        return builder.toString();
    }

    public static InvokerStatement invokerStatement(String function, String namespace, Statement... statements) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (statements != null) {
            for (Statement statement : statements) {
                listColumnStatement.add(statement);
            }
        }
        InvokerStatement invokerStatement = new InvokerStatement();
        invokerStatement.setFunction(function);
        invokerStatement.setNamespace(namespace);
        invokerStatement.setParamStatement(listColumnStatement);
        return invokerStatement;
    }

    public static AliasStatement aliasStatement(String column, String alias) {
        return aliasStatement(new SymbolStatement.LetterStatement(column), new SymbolStatement.LetterStatement(alias));
    }

    public static AliasStatement aliasStatement(Statement column, Statement alias) {
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(column);
        aliasStatement.setAlias(alias);
        return aliasStatement;
    }

    public static ListColumnStatement listColumnStatement(String cut, String... strs) {
        if (strs != null && strs.length > 0) {
            Statement[] statements = new Statement[strs.length];
            for (int i = 0; i < strs.length; i++) {
                statements[i] = new SymbolStatement.LetterStatement(strs[i]);
            }
            return listColumnStatement(cut, statements);
        } else {
            return new ListColumnStatement(cut);
        }
    }

    public static ListColumnStatement listColumnStatement(String cut, Statement... statements) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(cut);
        if (statements != null && statements.length > 0) {
            for (Statement statement : statements) {
                listColumnStatement.add(statement);
            }
        }
        return listColumnStatement;
    }

    public static ConditionStatement conditionStatement(Statement left, OperStatement operStatement, Statement right) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(left);
        conditionStatement.setOper(operStatement);
        conditionStatement.setRight(right);
        return conditionStatement;
    }

    public static QueryStatement queryStatement(ListColumnStatement selectListColumnStatement, AliasStatement aliasStatement, ConditionStatement conditionStatement) {
        QueryStatement queryStatement = new QueryStatement();

        SelectStatement selectStatement = new SelectStatement();
        selectStatement.setSelectList(selectListColumnStatement);
        queryStatement.setSelectStatement(selectStatement);

        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(aliasStatement);
        queryStatement.setFromStatement(fromStatement);

        if (conditionStatement != null) {
            WhereStatement whereStatement = new WhereStatement();
            whereStatement.setStatement(conditionStatement);
            queryStatement.setWhereStatement(whereStatement);
        }
        return queryStatement;
    }
}
