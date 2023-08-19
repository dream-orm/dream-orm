package com.dream.tdengine.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToPubSQL;
import com.dream.tdengine.statement.TdInsertStatement;

import java.util.List;

public class ToTdEngine extends ToPubSQL {
    @Override
    protected String toString(InsertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (statement instanceof TdInsertStatement) {
            TdInsertStatement tdInsertStatement = (TdInsertStatement) statement;
            Statement stdTable = tdInsertStatement.getStdTable();
            ListColumnStatement tags = tdInsertStatement.getTags();
            Statement columns = tdInsertStatement.getColumns();
            StringBuilder builder = new StringBuilder();
            if (stdTable != null) {
                builder.append(" USING " + toStr(stdTable, assist, invokerList));
            }
            if (tags != null) {
                builder.append(" TAGS(" + toStr(tags, assist, invokerList) + ")");
            }
            return "INSERT INTO " + toStr(statement.getTable(), assist, invokerList) + builder + (columns != null ? toStr(columns, assist, invokerList) : " ") + toStr(statement.getValues(), assist, invokerList);
        } else {
            return super.toString(statement, assist, invokerList);
        }
    }
}
