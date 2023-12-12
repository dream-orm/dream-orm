package com.dream.system.antlr.handler.page;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.annotation.PageQuery;
import com.dream.system.config.MethodInfo;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;

import java.util.List;

public class PageHandler extends AbstractHandler {
    private final MethodInfo methodInfo;
    private final Invoker invoker;
    private boolean offset;
    private Statement first;
    private Statement second;

    public PageHandler(Invoker invoker, MethodInfo methodInfo) {
        this.invoker = invoker;
        this.methodInfo = methodInfo;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        invoker.setAccessible(false);
        handlerCount((QueryStatement) statement);
        handlerPage((QueryStatement) statement);
        return statement;
    }

    void handlerCount(QueryStatement statement) {
        PackageStatement packageStatement = new PackageStatement();
        QueryStatement queryStatement = statement.clone();
        queryStatement.setOrderStatement(null);
        SelectStatement selectStatement = new SelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        FunctionStatement.CountStatement countStatement = new FunctionStatement.CountStatement();
        ListColumnStatement paramListColumnStatement = new ListColumnStatement(",");
        paramListColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        countStatement.setParamsStatement(paramListColumnStatement);
        listColumnStatement.add(countStatement);
        selectStatement.setSelectList(listColumnStatement);
        if (queryStatement.getUnionStatement() == null && !queryStatement.getSelectStatement().isDistinct()) {
            queryStatement.setSelectStatement(selectStatement);
            packageStatement.setStatement(queryStatement);
        } else {
            queryStatement.setSelectStatement(statement.getSelectStatement());
            QueryStatement newQueryStatement = new QueryStatement();
            newQueryStatement.setSelectStatement(selectStatement);
            FromStatement fromStatement = new FromStatement();
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(new BraceStatement(queryStatement));
            aliasStatement.setAlias(new SymbolStatement.LetterStatement("t_tmp"));
            fromStatement.setMainTable(aliasStatement);
            newQueryStatement.setFromStatement(fromStatement);
            packageStatement.setStatement(newQueryStatement);
        }
        PageQuery pageQuery = methodInfo.get(PageQuery.class);
        String value = pageQuery.value();
        String property = "total";
        if (!ObjectUtil.isNull(value)) {
            property = value + "." + property;
        }
        MethodInfo methodInfo = new MethodInfo()
                .setId(this.methodInfo.getId() + "#count")
                .setConfiguration(this.methodInfo.getConfiguration())
                .setRowType(NonCollection.class)
                .setColType(Long.class)
                .setCache(this.methodInfo.isCache())
                .setSql("SELECT COUNT(*)FROM(" + this.methodInfo.getSql() + ")t_mp")
                .setStatement(packageStatement);
        PageAction pageAction = new PageAction(methodInfo, property);
        this.methodInfo.addInitAction(pageAction);
    }

    void handlerPage(QueryStatement statement) throws AntlrException {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(offset);
        limitStatement.setFirst(first);
        limitStatement.setSecond(second);
        if (statement.getUnionStatement() == null && statement.getLimitStatement() == null) {
            statement.setLimitStatement(limitStatement);
        } else {
            QueryStatement queryStatement = new QueryStatement();
            SelectStatement selectStatement = new SelectStatement();
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            listColumnStatement.add(new SymbolStatement.LetterStatement("t_tmp.*"));
            selectStatement.setSelectList(listColumnStatement);
            queryStatement.setSelectStatement(selectStatement);
            FromStatement fromStatement = new FromStatement();
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(new BraceStatement(statement));
            aliasStatement.setAlias(new SymbolStatement.LetterStatement("t_tmp"));
            fromStatement.setMainTable(aliasStatement);
            queryStatement.setFromStatement(fromStatement);
            queryStatement.setLimitStatement(limitStatement);
            statement.replaceWith(queryStatement);
        }
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    public void setParamList(Statement first, Statement second, boolean offset) {
        this.first = first;
        this.second = second;
        this.offset = offset;
    }
}
