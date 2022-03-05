package com.moxa.dream.antlr.util;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.expr.SqlExpr;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;

import java.util.Set;
import java.util.Stack;

public class ExpandWrapper {
    private FromStatement cloneFromStatement(FromStatement fromStatement) {
        FromStatement newFromStatement = new FromStatement();
        newFromStatement.setMainTable(fromStatement.getMainTable());
        newFromStatement.setJoinList(fromStatement.getJoinList());
        return newFromStatement;
    }

    private SelectStatement cloneSelectStatement(SelectStatement selectStatement) {
        SelectStatement newSelectStatement = new SelectStatement();
        newSelectStatement.setPreSelect(selectStatement.getPreSelect());
        newSelectStatement.setSelectList(selectStatement.getSelectList());
        return newSelectStatement;
    }

    public QueryStatement expandQuery(QueryStatement queryStatement) {
        SelectStatement selectStatement = cloneSelectStatement(queryStatement.getSelectStatement());
        FromStatement fromStatement = cloneFromStatement(queryStatement.getFromStatement());
        WhereStatement whereStatement = queryStatement.getWhereStatement();
        GroupStatement groupStatement = queryStatement.getGroupStatement();
        HavingStatement havingStatement = queryStatement.getHavingStatement();
        LimitStatement limitStatement = queryStatement.getLimitStatement();
        QueryStatement newQueryStatement = new QueryStatement();
        newQueryStatement.setSelectStatement(selectStatement);
        newQueryStatement.setFromStatement(fromStatement);
        newQueryStatement.setWhereStatement(whereStatement);
        newQueryStatement.setGroupStatement(groupStatement);
        newQueryStatement.setHavingStatement(havingStatement);
        newQueryStatement.setLimitStatement(limitStatement);
        BraceStatement braceStatement = new BraceStatement();
        braceStatement.setStatement(newQueryStatement);
        Statement mainTable = queryStatement.getFromStatement().getMainTable();
        SymbolStatement.LetterStatement alias;
        if (mainTable instanceof SymbolStatement.LetterStatement)
            alias = ((SymbolStatement.LetterStatement) mainTable);
        else if (mainTable instanceof AliasStatement) {
            AliasStatement mainTableAliasStatement = (AliasStatement) mainTable;
            alias = mainTableAliasStatement.getAlias();
        } else
            throw new RuntimeException("FROM抽象树未发现主表表名");
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(braceStatement);
        aliasStatement.setAlias(alias);
        FromStatement newFromStatement = new FromStatement();
        newFromStatement.setMainTable(aliasStatement);
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        SymbolStatement.LetterStatement dotStatement = new SymbolStatement.LetterStatement(alias.getSymbol() + ".*");
        listColumnStatement.add(dotStatement);
        queryStatement.getSelectStatement().setSelectList(listColumnStatement);
        queryStatement.setFromStatement(newFromStatement);
        queryStatement.setWhereStatement(null);
        queryStatement.setGroupStatement(null);
        queryStatement.setHavingStatement(null);
        queryStatement.setOrderStatement(null);
        queryStatement.setLimitStatement(null);
        return queryStatement;
    }

    public String wrapper(SqlExpr sqlExpr, ExprReader exprReader) {
        Set<ExprType> acceptSet = sqlExpr.getAcceptSet();
        Stack<ExprInfo> exprInfoStack = exprReader.getExprInfoStack();
        ExprInfo curExprInfo = exprInfoStack.pop();
        StringBuilder builder = new StringBuilder();
        builder.append("Current parser '" + sqlExpr.getClass().getName() + "'\naccept type '" + acceptSet + "'\nnot support'" + curExprInfo + "'"
                + "\naccepted type:\n");
        int size = exprInfoStack.size();
        while (!exprInfoStack.isEmpty()) {
            builder.append((size--) + ":\t" + exprInfoStack.pop() + "\n");
        }
        return builder.toString();
    }
}
