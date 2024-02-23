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
