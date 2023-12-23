package com.dream.tdengine.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToPubSQL;
import com.dream.tdengine.statement.TdInsertStatement;
import com.dream.tdengine.statement.TdQueryStatement;

import java.util.List;

public class ToTdEngine extends ToPubSQL {
    @Override
    protected String toString(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (statement instanceof TdQueryStatement) {
            String from = toStr(statement.getFromStatement(), assist, invokerList);
            String where = toStr(statement.getWhereStatement(), assist, invokerList);
            String partitionBy = toStr(((TdQueryStatement) statement).getPartitionBy(), assist, invokerList);
            String window = toStr(((TdQueryStatement) statement).getWindnow(), assist, invokerList);
            String groupBy = toStr(statement.getGroupStatement(), assist, invokerList);
            String having = toStr(statement.getHavingStatement(), assist, invokerList);
            String select = toStr(statement.getSelectStatement(), assist, invokerList);
            String orderBy = toStr(statement.getOrderStatement(), assist, invokerList);
            String sLimit = toStr(((TdQueryStatement) statement).getSlimit(), assist, invokerList);
            String limit = toStr(statement.getLimitStatement(), assist, invokerList);
            String union = toStr(statement.getUnionStatement(), assist, invokerList);
            String forUpdate = toStr(statement.getForUpdateStatement(), assist, invokerList);
            return select + from + where + partitionBy + window + groupBy + having + orderBy + sLimit + limit + union + forUpdate;
        } else {
            return super.toString(statement, assist, invokerList);
        }
    }

    @Override
    protected String toString(InsertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (statement instanceof TdInsertStatement) {
            TdInsertStatement tdInsertStatement = (TdInsertStatement) statement;
            Statement stdTable = tdInsertStatement.getStdTable();
            Statement tagColumn = tdInsertStatement.getTagColumn();
            ListColumnStatement tags = tdInsertStatement.getTags();
            Statement columns = tdInsertStatement.getColumns();
            StringBuilder builder = new StringBuilder();
            if (stdTable != null) {
                builder.append(" USING " + toStr(stdTable, assist, invokerList));
            }
            if(tagColumn!=null){
                builder.append(toStr(tagColumn,assist,invokerList));
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
