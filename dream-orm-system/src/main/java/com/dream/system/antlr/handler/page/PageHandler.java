package com.dream.system.antlr.handler.page;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.QueryExpr;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.LimitStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToNativeSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.config.MethodInfo;
import com.dream.util.reflect.ReflectUtil;

import java.util.List;

public class PageHandler extends AbstractHandler {
    private final MethodInfo methodInfo;
    private final Invoker invoker;
    private final ToNativeSQL toNativeSQL = new ToNativeSQL();
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

    void handlerCount(QueryStatement statement) throws AntlrException {
        QueryStatement queryStatement = new QueryStatement();
        ReflectUtil.copy(queryStatement, statement);
        if (queryStatement.getOrderStatement() != null) {
            queryStatement.setOrderStatement(null);
        }
        String countSql = "select count(1) from (" + toNativeSQL.toStr(queryStatement, null, null) + ")t_tmp";
        PageAction pageAction = new PageAction(methodInfo, countSql);
        methodInfo.addInitAction(pageAction);
    }

    void handlerPage(QueryStatement queryStatement) throws AntlrException {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(offset);
        limitStatement.setFirst(first);
        limitStatement.setSecond(second);
        if (queryStatement.getUnionStatement() == null && queryStatement.getLimitStatement() == null) {
            queryStatement.setLimitStatement(limitStatement);
        } else {
            String sql = "select t_tmp.* from(" + toNativeSQL.toStr(queryStatement, null, null) + ")t_tmp " + toNativeSQL.toStr(limitStatement, null, null);
            QueryStatement pageQueryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            ReflectUtil.copy(queryStatement, pageQueryStatement);
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
