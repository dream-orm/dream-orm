package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.BraceExpr;
import com.dream.antlr.expr.QueryExpr;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 方言公共类
 */
public abstract class ToPubSQL extends ToNativeSQL {

    @Override
    protected String before(Statement statement) {
        return statement.getQuickValue();
    }

    @Override
    protected void after(Statement statement, String sql) {
        if (statement.isNeedCache()) {
            statement.setQuickValue(sql);
        }
    }

    @Override
    protected String toString(InvokerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Invoker invoker = assist.getInvoker(statement.getFunction(), statement.getNamespace());
        if (invokerList == null) {
            invokerList = new ArrayList<>();
        }
        return invoker.invoke(statement, assist, this, invokerList);
    }

    @Override
    protected String toString(MyFunctionStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return statement.toString(this, assist, invokerList);
    }

    protected String toStringForRowNumber(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        LimitStatement limitStatement = statement.getLimitStatement();
        if (limitStatement != null && !limitStatement.isOffset()) {
            Statement first = limitStatement.getFirst();
            Statement second = limitStatement.getSecond();
            statement.setLimitStatement(null);

            OrderStatement orderStatement = statement.getOrderStatement();
            if (orderStatement != null) {
                statement.setOrderStatement(null);
            } else {
                orderStatement = new OrderStatement();
                orderStatement.setOrder(new BraceExpr(new ExprReader("(select 0)")).expr());
            }
            RowNumberStatement rowNumberStatement = new RowNumberStatement();
            RowNumberStatement.OverStatement overStatement = new RowNumberStatement.OverStatement();
            overStatement.setOrderStatement(orderStatement);
            rowNumberStatement.setStatement(overStatement);
            ListColumnStatement selectList = statement.getSelectStatement().getSelectList();
            Statement[] columnList = selectList.getColumnList();
            Statement[] newColumnList = new Statement[columnList.length + 1];
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(rowNumberStatement);
            aliasStatement.setAlias(new SymbolStatement.LetterStatement("rn"));
            newColumnList[0] = aliasStatement;
            System.arraycopy(columnList, 0, newColumnList, 1, columnList.length);
            selectList.setColumnList(newColumnList);

            ToSQL toNativeSQL = new ToNativeSQL();
            String minValue;
            String maxValue;
            String querySql = toNativeSQL.toStr(statement, null, null);
            String sql;
            if (second == null) {
                maxValue = toNativeSQL.toStr(first, null, null);
                sql = "select t_tmp.* from (" + querySql + ")t_tmp where rn <=" + maxValue;
            } else {
                maxValue = toNativeSQL.toStr(second, null, null);
                minValue = toNativeSQL.toStr(first, null, null);
                sql = "select t_tmp.* from (" + querySql + ")t_tmp where rn > " + minValue + " and rn<=" + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            AntlrUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    protected String toStringForCreateTable(DDLCreateStatement.DDLCreateTableStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String table = toStr(statement.getStatement(), assist, invokerList);
        Statement comment = statement.getComment();
        StringBuilder builder = new StringBuilder();
        if (comment != null) {
            builder.append("COMMENT ON TABLE " + table + " is " + toStr(comment, assist, invokerList) + ";");
        }
        List<DDLDefineStatement> columnDefineList = statement.getColumnDefineList();
        for (DDLDefineStatement ddlDefineStatement : columnDefineList) {
            if (ddlDefineStatement instanceof DDLDefineStatement.DDLColumnDefineStatement) {
                DDLDefineStatement.DDLColumnDefineStatement ddlColumnDefineStatement = (DDLDefineStatement.DDLColumnDefineStatement) ddlDefineStatement;
                Statement columnComment = ddlColumnDefineStatement.getComment();
                if (columnComment != null) {
                    builder.append("COMMENT ON COLUMN " + table + "." + toStr(ddlColumnDefineStatement.getColumn(), assist, invokerList) + " is " + toStr(columnComment, assist, invokerList) + ";");
                }
            }
        }
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.setColumnList(columnDefineList.toArray(new Statement[columnDefineList.size()]));
        return "CREATE TABLE " + (statement.isExistCreate() ? "" : "IF NOT EXISTS ") + toStr(statement.getStatement(), assist, invokerList) + "(" +
                toStr(listColumnStatement, assist, invokerList)
                + ");" + builder;
    }
}
