package com.dream.system.antlr.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.FunctionExpr;
import com.dream.antlr.expr.QueryExpr;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToNativeSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.annotation.PageQuery;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.SqlAction;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;
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
        SelectStatement selectStatement = queryStatement.getSelectStatement();
        String countSql = null;
        if (!(selectStatement.isDistinct())) {
            UnionStatement unionStatement = queryStatement.getUnionStatement();
            if (unionStatement == null) {
                SelectStatement countSelectStatement = new SelectStatement();
                ListColumnStatement listColumnStatement = new ListColumnStatement();
                listColumnStatement.add(new FunctionExpr(new ExprReader("count(1)")).expr());
                countSelectStatement.setSelectList(listColumnStatement);
                queryStatement.setSelectStatement(countSelectStatement);
                countSql = toNativeSQL.toStr(queryStatement, null, null);
            }
        }
        if (ObjectUtil.isNull(countSql)) {
            countSql = "select count(1) from (" + toNativeSQL.toStr(queryStatement, null, null) + ")t_tmp";
        }
        SqlAction sqlAction = new SqlAction(methodInfo, countSql, NonCollection.class, Long.class, (arg, result) -> {
            ObjectWrapper wrapper = ObjectWrapper.wrapper(arg);
            PageQuery pageQuery = methodInfo.get(PageQuery.class);
            String value = pageQuery.value();
            String property = "total";
            if (!ObjectUtil.isNull(value)) {
                property = value + "." + property;
            }
            wrapper.set(property, result);
        });
        methodInfo.addInitAction(sqlAction);
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
