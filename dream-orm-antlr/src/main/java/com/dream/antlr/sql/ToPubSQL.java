package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;

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
                orderStatement.setOrder(new BraceStatement(new SymbolStatement.LetterStatement("(select 0)")));
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

            QueryStatement queryStatement = new QueryStatement();
            SelectStatement selectStatement = new SelectStatement();
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            listColumnStatement.add(new SymbolStatement.LetterStatement("t_tmp.*"));
            selectStatement.setSelectList(listColumnStatement);
            queryStatement.setSelectStatement(selectStatement);
            AliasStatement tableAliasStatement = new AliasStatement();
            tableAliasStatement.setColumn(new BraceStatement(statement));
            tableAliasStatement.setAlias(new SymbolStatement.LetterStatement("t_tmp"));
            FromStatement fromStatement = new FromStatement();
            fromStatement.setMainTable(tableAliasStatement);
            queryStatement.setFromStatement(fromStatement);
            ConditionStatement conditionStatement;
            if (second == null) {
                conditionStatement = new ConditionStatement();
                conditionStatement.setLeft(new SymbolStatement.LetterStatement("rn"));
                conditionStatement.setOper(new OperStatement.LEQStatement());
                conditionStatement.setRight(first);
            } else {
                ConditionStatement leftConditionStatement = new ConditionStatement();
                leftConditionStatement.setLeft(new SymbolStatement.LetterStatement("rn"));
                leftConditionStatement.setOper(new OperStatement.GTStatement());
                leftConditionStatement.setRight(first);

                ConditionStatement rightConditionStatement = new ConditionStatement();
                rightConditionStatement.setLeft(new SymbolStatement.LetterStatement("rn"));
                rightConditionStatement.setOper(new OperStatement.LEQStatement());
                ConditionStatement plusConditionStatement = new ConditionStatement();
                plusConditionStatement.setLeft(first);
                plusConditionStatement.setOper(new OperStatement.ADDStatement());
                plusConditionStatement.setRight(second);
                rightConditionStatement.setRight(plusConditionStatement);

                conditionStatement = new ConditionStatement();
                conditionStatement.setLeft(leftConditionStatement);
                conditionStatement.setOper(new OperStatement.ANDStatement());
                conditionStatement.setRight(rightConditionStatement);
            }
            WhereStatement whereStatement = new WhereStatement();
            whereStatement.setCondition(conditionStatement);
            queryStatement.setWhereStatement(whereStatement);
            statement = queryStatement;
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
        ListColumnStatement columnDefineList = statement.getColumnDefineList();
        for (Statement ddlDefineStatement : columnDefineList.getColumnList()) {
            if (ddlDefineStatement instanceof DDLDefineStatement.DDLColumnDefineStatement) {
                DDLDefineStatement.DDLColumnDefineStatement ddlColumnDefineStatement = (DDLDefineStatement.DDLColumnDefineStatement) ddlDefineStatement;
                Statement columnComment = ddlColumnDefineStatement.getComment();
                if (columnComment != null) {
                    builder.append("COMMENT ON COLUMN " + table + "." + toStr(ddlColumnDefineStatement.getColumn(), assist, invokerList) + " is " + toStr(columnComment, assist, invokerList) + ";");
                }
            }
        }
        return "CREATE TABLE " + (statement.isExistCreate() ? "" : "IF NOT EXISTS ") + toStr(statement.getStatement(), assist, invokerList) + "(" +
                toStr(columnDefineList, assist, invokerList)
                + ");" + builder;
    }
}